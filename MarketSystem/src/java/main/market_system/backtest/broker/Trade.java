package market_system.backtest.broker;

import java.time.LocalDateTime;

import market_system.backtest.broker.order.FixedTimeOrder;
import market_system.backtest.broker.order.Order;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.broker.order.TPSLOrder;
import market_system.backtest.data.CandleData;

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
	public boolean update(int idx, LocalDateTime date, CandleData cd) {
		if(order instanceof FixedTimeOrder) {
			if(idx-this.idx>=((FixedTimeOrder)order).getDuration()) {
				return true;
			}
		
			/*long duration = Duration.between(order.getOpenDate(), date).toMinutes();
			if(duration>((FixedTimeOrder)order).getDuration()) {
				return true;
			}*/
		}
		if(order instanceof TPSLOrder) {
			TPSLOrder order = (TPSLOrder)this.order;
			if(order.getType()==ORDER_TYPE.BUY) {
				if(order.getTp()<=cd.high)return true;
				if(order.getSl()>=cd.low)return true;
			}
			else {
				if(order.getTp()>=cd.low)return true;
				if(order.getSl()<=cd.high)return true;
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
