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
	Position position;
	List<Order>orders,pendingOrders;
	List<Trade>trades,openTrades;
	List<Deal>deals;
	IdGenerator oGens,tGens,dGens;
	Client client;
	
	public Broker(Client client) {
		this.client = client;
		this.orders = new ArrayList<>();
		this.pendingOrders = new ArrayList<>();
		this.trades = new ArrayList<>();
		this.openTrades = new ArrayList<>();
		this.deals = new ArrayList<>();
		this.oGens = new IdGenerator();
		this.tGens = new IdGenerator();
		this.dGens = new IdGenerator();
		this.position = new Position(client.getDeposit());
	}

	public void onInit() {
		
	}
	public void onTick(LocalDateTime date, CandleData candle, Map<String, Double> indicatorsMap) {

		//tp sl fixedtime ...
		for(int i=0;i<this.openTrades.size();i++) {
			if(this.openTrades.get(i).update(date, candle.open)) {
				Deal deal = new Deal(dGens.getNext(), date, candle.open, this.openTrades.get(i));
				this.deals.add(deal);
				this.openTrades.remove(i);
				i--;
			}
		}
		
		//open pending orders
		double equity = position.getEquity();
		double auxEq = 0d;
		for(int i=0;i<this.pendingOrders.size();i++) {
			Order o = this.pendingOrders.get(i);
			if(equity-auxEq<o.getVolume()*candle.open) {
				System.err.println("Order couldn't be open >>> "+o.toString());
				continue;
			}
			auxEq += o.getVolume()*candle.open;
			Trade t = new Trade(tGens.getNext(), date, candle.open, o.getVolume(), o);
			this.openTrades.add(t);
			this.orders.add(o);
		}this.pendingOrders.clear();
		
		
		//update position
		this.position.update(this.openTrades, this.deals, candle.close);
		
		currentDate = date;
		currentPrice = candle.close;
	}
	

	public void onEnd() {
		//close everything...
		
		this.orders.clear();
		this.trades.clear();
		this.deals.clear();
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
}
