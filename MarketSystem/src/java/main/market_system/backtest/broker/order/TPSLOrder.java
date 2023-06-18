package market_system.backtest.broker.order;

import java.time.LocalDateTime;


public class TPSLOrder extends Order {
	public TPSLOrder(int id, ORDER_TYPE type, double volume, LocalDateTime openDate, double openPrice, String comment) {
		super(id, type, volume, openDate, openPrice, comment);
	}
}
