package market_system.backtest.stats;

import java.time.LocalDateTime;

import market_system.backtest.broker.Broker;

public abstract class BackTestStats {
	public abstract void onTick(LocalDateTime date, Broker broker);
}
