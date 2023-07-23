package market_system.backtest.broker;

import java.time.Duration;
import java.time.LocalDateTime;

import market_system.backtest.broker.order.FixedTimeOrder;
import market_system.backtest.broker.order.Order;
import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Trade {
	int id,idx;
	Order order;
	LocalDateTime openDate;
	double openPrice,volume;
	public Trade(int id, int idx, LocalDateTime openDate, double currentPrice, double volume, Order order) {
		this.id = id;
		this.idx = idx;
		this.volume = volume;
		this.order = order;
		this.openDate = openDate;
		this.openPrice = currentPrice;
	}
	public boolean update(LocalDateTime date, double price) {
		if(order instanceof FixedTimeOrder) {
			long duration = Duration.between(order.getOpenDate(), date).toMinutes();
			if(duration>((FixedTimeOrder)order).getDuration()) {
				return true;
			}
		}
		return false;
	}
	public ORDER_TYPE getType() {
		return order.getType();
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
	public int getIdx() {
		return idx;
	}
	public int getId() {
		return id;
	}
}
