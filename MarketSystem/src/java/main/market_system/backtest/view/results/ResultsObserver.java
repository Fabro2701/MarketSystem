package market_system.backtest.view.results;

import java.time.LocalDateTime;
import java.util.List;

import market_system.backtest.broker.Deal;
import market_system.backtest.broker.Position;
import market_system.backtest.broker.Trade;

public interface ResultsObserver {
	public void update(LocalDateTime date, Position position, List<Deal> deals, List<Trade> otrades);
}
