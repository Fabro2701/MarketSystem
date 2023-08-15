package market_system.backtest.strategy;

import java.time.LocalDateTime;
import java.util.Map;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.data.CandleData;
import market_system.backtest.data.DataProxy;

public class UserStrategy extends Strategy {

	@Override
	public void onTick(int idx, LocalDateTime date, CandleData cd, DataProxy dataProxy, Broker broker) {
		if(idx==4) {
			double atr = dataProxy.getValue("atr_0");
			broker.sendTPSLOrder(ORDER_TYPE.BUY, 0.1d, cd.ask+atr*3.0, cd.ask-atr*3.0, String.valueOf(atr));
		}
		/*if(idx==10) {
			//broker.sendFixedTimeOrder(ORDER_TYPE.SELL, 1d, 10L, null);
		}
		if(idx==100) {
			//broker.sendFixedTimeOrder(ORDER_TYPE.SELL, 1d, 100L, null);
		}*/
		/*double threshold=30d;
		if(indicators.get("Momentum10")>threshold &&
		   indicators.get("Momentum50")>threshold &&
		   indicators.get("Momentum200")>threshold) {
			broker.sendFixedTimeOrder(ORDER_TYPE.SELL, 2d, 20L, String.format("%f_%f_%f", indicators.get("Momentum10"),indicators.get("Momentum50"),indicators.get("Momentum200")));
		}*/
	}

}
