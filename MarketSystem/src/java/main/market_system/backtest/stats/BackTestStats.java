package market_system.backtest.stats;

import java.time.LocalDateTime;

import market_system.backtest.broker.Broker;
import market_system.backtest.view.results.BalancePanel;

public abstract class BackTestStats {
	public abstract void onTick(LocalDateTime date, Broker broker);
	public abstract void onEnd(Broker broker);
}
