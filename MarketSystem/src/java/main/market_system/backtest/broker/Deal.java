package market_system.backtest.broker;

import java.time.LocalDateTime;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Deal {
	int id;
	double volume,openPrice,closePrice,profit;
	LocalDateTime openTime, closeTime;
	public Deal(int id, LocalDateTime date, double price, Trade trade) {
		this.id = id;
		this.closeTime = date;
		this.closePrice = price;
		this.volume = trade.getVolume();
		this.openTime = trade.getOpenDate();
		this.openPrice = trade.getOpenPrice();
		
		this.profit = (closePrice-openPrice)*volume;
		
	}
	public int getId() {
		return id;
	}
	public double getVolume() {
		return volume;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public double getProfit() {
		return profit;
	}
	public LocalDateTime getOpenTime() {
		return openTime;
	}
	public LocalDateTime getCloseTime() {
		return closeTime;
	}

}
