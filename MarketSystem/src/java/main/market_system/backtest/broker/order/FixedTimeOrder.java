package market_system.backtest.broker.order;

import java.time.LocalDateTime;

public class FixedTimeOrder extends Order {
	long duration;
	
	public FixedTimeOrder(int id, ORDER_TYPE type, double volume, LocalDateTime openDate, double openPrice, long duration,String comment) {
		super(id, type, volume, openDate, openPrice, comment);
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return String.format("FixedTimeOrder %d: %s %f at %s open: %f %dmin \"%s\"", id, type, volume, openDate.toString(), openPrice, duration, comment);
	}
}
