package market_system.backtest.broker.order;

import java.time.LocalDateTime;


public class TPSLOrder extends Order {
	double tp, sl;
	public TPSLOrder(int id, ORDER_TYPE type, double volume, LocalDateTime openDate, double openPrice, double tp, double sl, String comment) {
		super(id, type, volume, openDate, openPrice, comment);
		this.tp=tp;
		this.sl=sl;
	}
	public double getTp() {
		return tp;
	}
	public double getSl() {
		return sl;
	}
	
	@Override
	public String toString() {
		return String.format("TPSLOrder %d: %s %f at %s open: %f tp:%f sl:%f \"%s\"", id, type, volume, openDate.toString(), openPrice, tp, sl, comment);
	}
}
