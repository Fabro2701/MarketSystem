package market_system.backtest.broker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import market_system.backtest.broker.order.BasicOrder;
import market_system.backtest.broker.order.FixedTimeOrder;
import market_system.backtest.broker.order.Order;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.data.CandleData;

public class Broker {
	LocalDateTime currentDate;
	double currentPrice;
	int currentIdx;
	Position position;
	List<Order>closedOrders,pendingOrders;
	List<Trade>closedTrades,openTrades;
	List<Deal>deals;
	IdGenerator oGens,tGens,dGens;
	Client client;
	
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
		//System.out.println("Time: "+date+"  |  "+position);
		
		//pending.. max and min of the candle for update
		//tp sl fixedtime ...
		for(int i=0;i<this.openTrades.size();i++) {
			if(this.openTrades.get(i).update(idx, date, candle.open)) {
				
				this.closeTrade(idx, this.openTrades.get(i), date, candle.open);
				
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
				System.err.println("Order couldn't be open >>> "+o.toString()+" Available balance: "+balance);
				continue;
			}
			//trade opens at o.getOpenPrice()
			Trade t = new Trade(tGens.getNext(), idx, date, o.getOpenPrice(), o.getVolume(), o);
			position.substractFromBalance(o.getVolume()*o.getOpenPrice());
			this.openTrades.add(t);
			this.closedOrders.add(o);
			System.out.println("Order opened >>> "+o.toString()+" Available balance: "+balance);
		}this.pendingOrders.clear();
		
		
		//update position
		this.position.update(this.openTrades, this.deals, candle.close);
		
		currentDate = date;
		currentPrice = candle.close;
		currentIdx = idx;
	}
	

	public void onEnd() {
		//close everything...
		for(int i=0;i<this.openTrades.size();i++) {
			closeTrade(currentIdx, this.openTrades.get(i),this.currentDate,this.currentPrice);
			this.openTrades.remove(i);
			i--;
		}
		
		this.position.update(this.openTrades, this.deals, this.currentPrice);

		this.closedTrades.clear();
		this.closedOrders.clear();
		this.deals.clear();
		
		
		System.out.println("Final balance: "+position.getEquity());
	}
	private void closeTrade(int idx, Trade trade, LocalDateTime date, double price) {
		Deal deal = new Deal(dGens.getNext(), idx, date, price, trade);
		System.out.printf("Trade closed at %f %s profit: %f\n", price, date.toString(),deal.profit);
		
		this.deals.add(deal);
		position.addToBalance(deal.getClosePrice()*deal.getVolume());
                
                this.closedTrades.add(trade);
	}

	public boolean sendOrder(ORDER_TYPE type, double volume, String comment) {
		if(volume<=0d) {
			System.err.println("incorrect volume size "+volume);
			return false;
		}
		Order o = new BasicOrder(oGens.getNext(), type, volume, currentDate, currentPrice, comment);
		pendingOrders.add(o);
		return true;
	}
	public boolean sendFixedTimeOrder(ORDER_TYPE type, double volume, long duration, String comment) {
		if(volume<=0d) {
			System.err.println("incorrect volume size "+volume);
			return false;
		}
		Order o = new FixedTimeOrder(oGens.getNext(), type, volume, currentDate, currentPrice, duration, comment);
		pendingOrders.add(o);
		return true;
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
