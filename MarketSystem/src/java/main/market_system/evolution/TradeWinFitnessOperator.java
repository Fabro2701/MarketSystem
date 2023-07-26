package market_system.evolution;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import market_system.backtest.BackTest;
import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Client;
import market_system.backtest.data.MarketData;
import market_system.backtest.stats.BackTestStats;
import market_system.backtest.stats.PositionStats;
import market_system.backtest.stats.TradesStats;
import market_system.backtest.strategy.GrammarBasedStrategy;
import model.individual.Individual;
import model.module.operator.fitness.FitnessEvaluationOperator;

public class TradeWinFitnessOperator extends FitnessEvaluationOperator {
	MarketData data;
	public TradeWinFitnessOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		data = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSD-PERIOD_H1.csv");
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public float evaluate(Individual ind) {
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
		double r = 0d;
		r = Math.sqrt(ss.getLongTradesWin()*ss.getShortTradesWin());
		//r = (ss.getLongTradesWin()+ss.getShortTradesWin())/2d;
		//r *= broker.getPosition().getBalance()*ss.getAvgProfit();
		//r *= ss.getnTrades();
		r *= broker.getPosition().getBalance();
		return (float) r;
		//return (float) broker.getPosition().getBalance();

	}

}
