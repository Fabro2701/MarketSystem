package market_system.backtest.broker.order;

import java.time.LocalDateTime;

public class BasicOrder extends Order {

	public BasicOrder(int id, ORDER_TYPE type, double volume, LocalDateTime openDate, double openPrice, String comment) {
		super(id, type, volume, openDate, openPrice, comment);
	}



}
