package market_system.backtest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Client;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.data.MarketData;
import market_system.backtest.strategy.Strategy;
import market_system.backtest.strategy.UserStrategy;

public class BackTest {
	private int cursor;
	private Broker broker;
	private MarketData data;
	private Map<String,Double>indicatorsMap;
	
	public BackTest(Broker broker) {
		this.cursor = 0;
		this.broker = broker;
		this.indicatorsMap = new HashMap<>();
	}
	
	public void init() {
		broker.onInit();
	}
	public void step(Strategy strategy, int n) {
		Objects.requireNonNull(data, "null data");
		
		for(int i=0;i<n && i+cursor<data.size();i++) {
			indicatorsMap.clear();
			data.fillIndicators(indicatorsMap, cursor+i);
			
			broker.onTick(data.getDate(cursor+i), data.get(cursor+i), indicatorsMap);
			strategy.onTick(data.getDate(cursor+i), data.get(cursor+i), indicatorsMap, broker);
			cursor++;
		}
		//observers
	}
	public void run(Strategy strategy) {
		this.step(strategy, data.size());
	}
	public void end() {
		broker.onEnd();
	}
	
	public void setData(MarketData data, int c) {
		this.data = data;
		this.cursor = c;
	}
	public void setData(MarketData data) {
		this.setData(data, 0);
	}
	
	public static void main(String args[]) {
		
		Broker broker = new Broker(new Client(2d));
		BackTest bt = new BackTest(broker);
		bt.setData(new MarketData("resources/data/EURUSDr.csv"));
		
		Strategy strat = new UserStrategy();
		bt.step(strat, 1);
		broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1L, null);
		broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1L, null);
		broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1L, null);
		bt.step(strat, 10);
	}
}
