package market_system.evolution;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import market_system.backtest.BackTest;
import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Client;
import market_system.backtest.data.MarketData;
import market_system.backtest.stats.BackTestStats;
import market_system.backtest.stats.TradesStats;
import market_system.backtest.strategy.GrammarBasedStrategy;
import model.individual.Individual;
import model.module.operator.fitness.FitnessEvaluationOperator;

public class TradeWinFitnessOperator extends FitnessEvaluationOperator {
	MarketData data;
	HashMap<String, Double>cache;
	boolean cacheable = true;
	public TradeWinFitnessOperator(Properties properties, Random rnd, String dataPath) {
		super(properties, rnd);
		data = new MarketData(dataPath);
		cache = new HashMap<>();
	}
	public TradeWinFitnessOperator(Properties properties, Random rnd, MarketData data) {
		super(properties, rnd);
		this.data = data;
		cache = new HashMap<>();
	}

	@Override
	public float evaluate(Individual ind) {
		String code = ind.getPhenotype().getPlainSymbols();
		if(cacheable)if(cache.containsKey(code))return cache.get(code).floatValue();
		
		Broker broker = new Broker(new Client(50d));
		BackTest backtest = new BackTest(broker,List.of(new TradesStats()));
		GrammarBasedStrategy strat = new GrammarBasedStrategy(ind.getPhenotype().getPlainSymbols());
		
		backtest.setData(data);
		backtest.init();
		backtest.run(strat);
		backtest.end();
		
		TradesStats ss=null;
		for(BackTestStats s:backtest.getStatsObservers()) {
            if(s instanceof TradesStats) {
            	ss = ((TradesStats)s);
            }
        }
		double r = 1d;
		//r = Math.sqrt(ss.getLongTradesWin()*ss.getShortTradesWin());
		//r = (ss.getLongTradesWin()+ss.getShortTradesWin())/2d;
		//r *= broker.getPosition().getBalance()*ss.getAvgProfit();
		//r *= ss.getnTrades();
		//r = 1d;
		//r *= broker.getPosition().getBalance();
		//r *= ss.getSharpeRatio();
		r *= ss.getCalmarRatio();

		broker.clear();
		//System.out.println(r);
		
		if(cacheable)cache.put(code, r);
		return (float) r;
		//return (float) broker.getPosition().getBalance();

	}
	
}
