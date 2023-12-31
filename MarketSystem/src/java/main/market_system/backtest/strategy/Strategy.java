package market_system.backtest.strategy;

import java.time.LocalDateTime;
import java.util.Map;

import market_system.backtest.broker.Broker;
import market_system.backtest.data.CandleData;
import market_system.backtest.data.DataProxy;

public abstract class Strategy {

	public abstract void onTick(int idx, LocalDateTime date, CandleData candleData, DataProxy dataProxy, Broker broker);

}
