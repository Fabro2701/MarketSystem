package market_system.evolution;

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

public class BalanceFitnessOperator extends FitnessEvaluationOperator {
	MarketData data;
	public BalanceFitnessOperator(Properties properties, Random rnd) {
		super(properties, rnd);
		data = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSD-PERIOD_H1.csv");
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public float evaluate(Individual ind) {
		Broker broker = new Broker(new Client(50d));
		BackTest backtest = new BackTest(broker,List.of());
		GrammarBasedStrategy strat = new GrammarBasedStrategy(ind.getPhenotype().getPlainSymbols());
		
		backtest.setData(data);
		backtest.init();
		backtest.run(strat);
		backtest.end();
		
		broker.clear();
		
		return (float) broker.getPosition().getBalance();
	}

}
