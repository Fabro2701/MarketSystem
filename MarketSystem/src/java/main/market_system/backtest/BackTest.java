package market_system.backtest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import market_system.backtest.broker.Broker;
import market_system.backtest.data.MarketData;
import market_system.backtest.strategy.Strategy;

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
}
