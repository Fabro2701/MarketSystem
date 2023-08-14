package market_system.backtest.broker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import market_system.backtest.broker.order.BasicOrder;
import market_system.backtest.broker.order.FixedTimeOrder;
import market_system.backtest.broker.order.Order;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.broker.order.TPSLOrder;
import market_system.backtest.data.CandleData;

public class Broker {
	LocalDateTime currentDate;
	double currentPrice,currentAsk,currentBid;
	int currentIdx;
	Position position;
	List<Order>closedOrders,pendingOrders;
	List<Trade>closedTrades,openTrades;
	List<Deal>deals;
	IdGenerator oGens,tGens,dGens;
	Client client;
	
	public static boolean debug = true;
	
	public Broker(Client client) {
		this.client = client;
		this.closedOrders = new ArrayList<>();
		this.pendingOrders = new ArrayList<>();
		this.closedTrades = new ArrayList<>();
		this.openTrades = new ArrayList<>();
		this.deals = new ArrayList<>();
		this.oGens = new IdGenerator();
		this.tGens = new IdGenerator();
		this.dGens = new IdGenerator();
		this.position = new Position(client.getDeposit());
	}

	public void onInit() {
		
	}
	public void onTick(int idx, LocalDateTime date, CandleData candle, Map<String, Double> indicatorsMap) {
		//if(idx%50==0)System.out.println("Time: "+date+"  |  "+position);
		//System.out.println("Time: "+date+"  |  "+position);
		
		//pending.. max and min of the candle for update
		//tp sl fixedtime ...
		for(int i=0;i<this.openTrades.size();i++) {
			if(this.openTrades.get(i).update(idx, date, candle)) {
				
				this.closeTrade(idx, this.openTrades.get(i), date, candle.ask, candle.bid);//tp sl orders actually should close at one of those points
				
				this.openTrades.remove(i);
				i--;
			}
		}
		
		//open pending orders
		double equity = position.getEquity();
		double balance = position.getBalance();
		for(int i=0;i<this.pendingOrders.size();i++) {
			Order o = this.pendingOrders.get(i);
			if(balance<o.getVolume()*o.getOpenPrice()) {
				if(debug)System.err.println("Order couldn't be open >>> "+o.toString()+" Available balance: "+balance);
				continue;
			}
			//trade opens at o.getOpenPrice()
			Trade t = new Trade(tGens.getNext(), idx, date, o.getOpenPrice(), o.getVolume(), o);
			//position.substractFromBalance(o.getVolume()*o.getOpenPrice());
			this.openTrades.add(t);
			this.closedOrders.add(o);
			if(debug)System.out.println("Order opened >>> "+o.toString()+" Available balance: "+balance);
		}this.pendingOrders.clear();
		
		
		//update position
		this.position.update(this.openTrades, this.deals, candle.close);
                //System.out.println(this.position);
		
		currentDate = date;
		currentPrice = candle.close;
		currentIdx = idx;
		currentAsk = candle.ask;
		currentBid = candle.bid;
	}
	

	public void onEnd() {
		//close everything...
		for(int i=0;i<this.openTrades.size();i++) {
			closeTrade(currentIdx, this.openTrades.get(i),this.currentDate, this.currentAsk, this.currentBid);
			this.openTrades.remove(i);
			i--;
		}
		
		this.position.update(this.openTrades, this.deals, this.currentPrice);

		this.closedTrades.clear();
		this.closedOrders.clear();
		//this.deals.clear();
		
		
		if(debug)System.out.println("Final balance: "+position.getEquity());
	}
	private void closeTrade(int idx, Trade trade, LocalDateTime date, double ask, double bid) {
		Deal deal = new Deal(dGens.getNext(), idx, date, ask, bid, trade);
		if(debug)System.out.printf("Trade closed at %f %s profit: %f\n", deal.closePrice, date.toString(),deal.profit);
		
		this.deals.add(deal);
		//position.addToBalance(deal.getOpenPrice()*deal.getVolume()+deal.profit);
		position.addToBalance(deal.profit);

		this.closedTrades.add(trade);
	}

	
	public void closeTrades(ORDER_TYPE type) {//actually it needs to create a new order
		for(int i=0;i<this.openTrades.size();i++) {
			Trade t = this.openTrades.get(i);
			if(t.getType()==type) {
				this.closeTrade(this.currentIdx, t, this.currentDate, this.currentAsk, this.currentBid);
				this.openTrades.remove(i);
				i--;
			}
		}
	}
	public boolean sendOrder(ORDER_TYPE type, double volume, String comment) {
		if(volume<=0d) {
			System.err.println("incorrect volume size "+volume);
			return false;
		}
		Order o = new BasicOrder(oGens.getNext(), type, volume, currentDate, type==ORDER_TYPE.BUY?currentAsk:currentBid, comment);
		pendingOrders.add(o);
		return true;
	}
	public boolean sendFixedTimeOrder(ORDER_TYPE type, double volume, long duration, String comment) {
		if(volume<=0d) {
			System.err.println("incorrect volume size "+volume);
			return false;
		}
		Order o = new FixedTimeOrder(oGens.getNext(), type, volume, currentDate, type==ORDER_TYPE.BUY?currentAsk:currentBid, duration, comment);
		pendingOrders.add(o);
		return true;
	}
	public boolean sendTPSLOrder(ORDER_TYPE type, double volume, double tp, double sl, String comment) {
		if(volume<=0d) {
			System.err.println("incorrect volume size "+volume);
			return false;
		}
		if(type == ORDER_TYPE.BUY && tp<sl) {
			System.err.printf("incorrect %s tp %f sl %f\n"+type.toString(),tp,sl);
			return false;
		}
		if(type == ORDER_TYPE.SELL && tp>sl) {
			System.err.printf("incorrect %s tp %f sl %f\n"+type.toString(),tp,sl);
			return false;
		}
		Order o = new TPSLOrder(oGens.getNext(), type, volume, currentDate, type==ORDER_TYPE.BUY?currentAsk:currentBid, tp, sl,comment);
		pendingOrders.add(o);
		return true;
	}

	public void clear() {
		this.deals.clear();
		this.deals=null;
		this.closedTrades.clear();
		this.closedTrades=null;
	}
	
	public Position getPosition() {
		return position;
	}

	public List<Deal> getDeals() {
		return deals;
	}

	public List<Trade> getOpenTrades() {
		return openTrades;
	}
	public List<Trade> getClosedTrades() {
		return closedTrades;
	}

	public int getCurrentIdx() {
		return currentIdx;
	}


}
