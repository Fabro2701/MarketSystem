package market_system.backtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Client;
import market_system.backtest.data.DataProxy;
import market_system.backtest.data.MarketData;
import market_system.backtest.stats.BackTestStats;
import market_system.backtest.stats.PositionStats;
import market_system.backtest.stats.TradesStats;
import market_system.backtest.strategy.GrammarBasedStrategy;
import market_system.backtest.strategy.Strategy;

public class BackTest {
	private int cursor;
	private Broker broker;
	private MarketData data;
	private Map<String,Double>indicatorsMap;
	private List<BackTestStats>statsObservers;
        private boolean done;
        DataProxy dataProxy;
	
	public BackTest(Broker broker,List<BackTestStats>obs) {
		this.cursor = 0;
		this.broker = broker;
		this.indicatorsMap = new HashMap<>();
		statsObservers = obs;
		dataProxy = new DataProxy();
	}
	public BackTest(Broker broker) {
		this(broker,List.of(new PositionStats(), new TradesStats()));
	}
	
	public void init() {
            done=false;
		broker.onInit();
	}
	public void step(Strategy strategy, int n) {
		Objects.requireNonNull(data, "null data series for backtest step");
		
		for(int i=0;i<n && i+cursor<data.size();i++) {
			/*try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//indicatorsMap.clear();
			//data.fillIndicators(indicatorsMap, cursor+i);
			this.dataProxy.setCursor(i+cursor);
			
			broker.onTick(i+cursor, data.getDate(cursor+i), data.getCandle(cursor+i));
			strategy.onTick(i+cursor, data.getDate(cursor+i), data.getCandle(cursor+i), dataProxy, broker);
			
			//observers
			for(BackTestStats s:this.statsObservers) {
				s.onTick(cursor+i, data.getDate(cursor+i), broker);
			}
		}
		cursor+=n;
		if(cursor>=data.size())done=true;
	}
	public void run(Strategy strategy) {
		this.step(strategy, data.size());
	}
	public void end() {
		broker.onEnd();
		//observers
		for(BackTestStats s:this.statsObservers) {
			s.onTick(data.size(),data.getDate(data.size()-1), broker);
			s.onEnd(data.getDate(data.size()-1), broker);
		}
	}
	
	public void setData(MarketData data, int c) {
		this.data = data;
		this.cursor = c;
		this.dataProxy.setData(data);
	}
	public void setData(MarketData data) {
		this.setData(data, 0);
	}
        public MarketData getData(){
            return data;
        }
        public boolean isDone(){
            return this.done;
        }
        public int getCursor(){
            return this.cursor;
        }
	
	public Broker getBroker() {
			return broker;
		}

	public List<BackTestStats> getStatsObservers() {
		return statsObservers;
	}

	public static void main(String args[]) {
		
		Broker broker = new Broker(new Client(10d));
		BackTest bt = new BackTest(broker);
		bt.setData(new MarketData("resources/data/EURUSD-PERIOD_M15_m.csv"));
		bt.init();
		
		//Strategy strat = new UserStrategy();
		GrammarBasedStrategy strat = new GrammarBasedStrategy(
				"if(Bulls_power_1>=3.2){res.bear=res.bear+9;}else{res.bull=res.bull+6;}if(Macd50_100_2<-4.9){res.bull=0;}else{res.bear=res.bear+6;}res.atr=9;"
				+ ""
				);

		bt.run(strat);
		/*bt.step(strat, 1);
		broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 24*60*5L, null);
		bt.step(strat, 2);
		broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 24*60*5L, null);
		//broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1000000L, null);
		//broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1000000L, null);
		bt.step(strat, 50);
		//broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 1L, null);
		//bt.step(strat, 2);*/
		
		bt.end();
		System.out.println(broker.getPosition().getMaximumDrawdown());
	}
}
