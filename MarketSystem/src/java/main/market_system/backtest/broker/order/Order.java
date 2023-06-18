package market_system.backtest.broker.order;

import java.time.LocalDateTime;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public abstract class Order {
	int id;
	ORDER_TYPE type;
	double volume;
	LocalDateTime openDate;
	double openPrice;
	String comment;
	public enum ORDER_TYPE{
		BUY,SELL;
	}
	public Order(int id, ORDER_TYPE type, double volume, LocalDateTime openDate, double openPrice, String comment) {
		this.id=id;
		this.type=type;
		this.volume=volume;
		this.openDate=openDate;
		this.openPrice=openPrice;
		this.comment=comment;
	}
	public int getId() {
		return id;
	}
	public ORDER_TYPE getType() {
		return type;
	}
	public double getVolume() {
		return volume;
	}
	public LocalDateTime getOpenDate() {
		return openDate;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public String getComment() {
		return comment;
	}
}
