package market_system.evolution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import market_system.backtest.broker.Broker;
import market_system.backtest.data.MarketData;
import model.Experiment;
import model.algorithm.AbstractPipeline;
import model.algorithm.AbstractSearchAlgorithm;
import model.algorithm.SimplePipeline;
import model.grammar.AbstractGrammar;
import model.module.CollectorModule;
import model.module.CrossoverModule;
import model.module.FitnessModule;
import model.module.GrammarModule;
import model.module.InitializationModule;
import model.module.InterleavedFitnessModule;
import model.module.JoinModule;
import model.module.MutationModule;
import model.module.SelectionModule;
import model.module.operator.Operator;
import model.module.operator.collector.FitnessCollectorOperator;
import model.module.operator.collector.SimilarityCollectorOperator;
import model.module.operator.crossover.CrossoverOperator;
import model.module.operator.fitness.FitnessEvaluationOperator;
import model.module.operator.grammar.GrammarOperator;
import model.module.operator.grammar.ModuleGrammarOperator;
import model.module.operator.initialization.InitializationOperator;
import model.module.operator.join.JoinOperator;
import model.module.operator.mutation.MutationOperator;
import model.module.operator.selection.SelectionOperator;
import view.GrammaticalEvolutionMainFrame;


public class MarketSystemExperiment extends Experiment{
	public MarketSystemExperiment(AbstractSearchAlgorithm algo) {
		super(algo);
	}
	public MarketSystemExperiment() {
		super();
	}
	
	FitnessCollectorOperator fitnesscollOp;
	@Override
	public void setup(Properties properties) {
		MarketData _data = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSD-PERIOD_M15_m.csv");
		List<MarketData>tmp = _data.split(0.1,0.8,0.1);
		MarketData trainingData = tmp.get(1);
		
		AbstractPipeline initPipeline = new SimplePipeline();
		
		AbstractGrammar grammar = this.loadGrammar(properties);
		System.out.println(grammar);
	
		InitializationModule initModule = new InitializationModule(generalPopulation, properties,rnd);
		InitializationOperator rinitOp = this.loadInitializer(properties, grammar);
		initModule.addOperator(rinitOp);
		
		FitnessModule initFitnessModule = new FitnessModule(generalPopulation, properties,rnd);
		FitnessEvaluationOperator fitnessInitOp = new TradeWinFitnessOperator(properties,rnd,trainingData);
		initFitnessModule.addOperator(fitnessInitOp);
		
		CollectorModule fitnesscollModule = new CollectorModule(generalPopulation, properties,rnd);
		fitnesscollOp = new FitnessCollectorOperator(properties,rnd);
		fitnesscollOp.addValidationOps(Map.of("t1",new TradeWinFitnessOperator(properties,rnd,tmp.get(0))));
		fitnesscollOp.addValidationOps(Map.of("t2",new TradeWinFitnessOperator(properties,rnd,tmp.get(2))));
		fitnesscollModule.addOperator(fitnesscollOp);

		//loop
		AbstractPipeline loopPipeline = new SimplePipeline();
		
		SelectionModule selectionModule = new SelectionModule(generalPopulation, properties, rnd, selectedPopulation);
		SelectionOperator selectionOp = this.loadSelection(properties);
		/*CombinedSelectionOperator selectionOp = new CombinedSelectionOperator(properties, rnd);
		selectionOp.addOperator(new EliteSelectionOperator(properties, rnd))
				   .addOperator(new LinearRankSelectionOperator(properties, rnd))
				   .addOperator(new RouletteSelectionOperator(properties, rnd))
				   .addOperator(new StochasticUniversalSamplingSelectionOperator(properties, rnd))
				   .addOperator(new TournamentSelectionOperator(properties, rnd));*/
		selectionModule.addOperator(selectionOp);
		
		
		CrossoverModule crossoverModule = new CrossoverModule(selectedPopulation, properties, rnd);
		CrossoverOperator crossoverOp = this.loadCrossover(properties);
		//LHSCrossoverOperator crossoverOp = new LHSCrossoverOperator(properties, rnd, grammar);
		/*InheritedCrossoverOperator crossoverOp = new InheritedCrossoverOperator(properties, rnd);
		crossoverOp.addOperator(new HomologousCrossoverOperator(properties, rnd))
				   .addOperator(new LHSCrossoverOperator(properties, rnd, grammar))
				   .addOperator(new OnePointCrossoverOperator(properties, rnd))
				   .addOperator(new TwoPointCrossoverOperator(properties, rnd));*/
		crossoverModule.addOperator(crossoverOp);
		
		MutationModule mutationModule = new MutationModule(selectedPopulation, properties, rnd);
		MutationOperator mutationOp = this.loadMutation(properties);
		mutationModule.addOperator(mutationOp);
		
		FitnessModule fitnessModule = new FitnessModule(selectedPopulation, properties, rnd);//selectedPopulation beacuse generalpop are already evaluated
		fitnessModule.addOperator(fitnessInitOp);
		
		/*InterleavedFitnessModule fitnessModule = new InterleavedFitnessModule(generalPopulation, properties,rnd);
		List<MarketData> ds = trainingData.split(Integer.parseInt(properties.getProperty("interleaved_split", "10")));
		for(var d:ds)fitnessModule.addOperator(new TradeWinFitnessOperator(properties,rnd,d));*/
		
		/*AdaptiveFitnessModule fitnessModule = new AdaptiveFitnessModule(generalPopulation, properties,rnd);
		Pair<MarketData,MarketData> dp = data.split(Double.parseDouble(properties.getProperty("adaptive_init", "0.3")));
		fitnessModule.setInitOp(new TradeWinFitnessOperator(properties,rnd,dp.first));
		List<MarketData> ds = dp.second.split(Integer.parseInt(properties.getProperty("adaptive_split", "10")));
		for(var d:ds)fitnessModule.addOperator(new TradeWinFitnessOperator(properties,rnd,d));
		*/
		
		JoinModule joinModule = new JoinModule(generalPopulation, properties, rnd, selectedPopulation);
		JoinOperator joinOp = this.loadJoin(properties);
		joinModule.addOperator(joinOp);
		
		GrammarModule moduleGrammarModule = new GrammarModule(generalPopulation, properties, rnd, grammar);
		GrammarOperator moduleGrammarOp = new ModuleGrammarOperator(properties, rnd, fitnessInitOp, rinitOp);
		moduleGrammarModule.addOperator(moduleGrammarOp);
		
		CollectorModule simicollModule = new CollectorModule(generalPopulation, properties, rnd);
		Operator simicollOp = new SimilarityCollectorOperator(properties,rnd);
		simicollModule.addOperator(simicollOp);

		
		initPipeline.addModule(initModule);
		initPipeline.addModule(initFitnessModule);
		initPipeline.addModule(fitnesscollModule);

		loopPipeline.addModule(selectionModule);
		loopPipeline.addModule(crossoverModule);
		loopPipeline.addModule(mutationModule);
		loopPipeline.addModule(fitnessModule);
		loopPipeline.addModule(joinModule);
		loopPipeline.addModule(fitnesscollModule);
		//loopPipeline.addModule(simicollModule);
		//loopPipeline.addModule(moduleGrammarModule);
		
		this.algorithm.setInitPipeline(initPipeline);
		this.algorithm.setLoopPipeline(loopPipeline);
	}

	public static void main(String args[]) {
		runGUI();
		//run();
	}
	public static void run() {
		Broker.debug=false;
		
		Properties properties = new Properties();
		try { 
			properties.load(new FileInputStream(new File("resources/evolution/default.properties")));
		} catch (IOException e) {e.printStackTrace(); } 
		
		
		MarketSystemExperiment test = new MarketSystemExperiment();
		test.setup(properties);
		test.run(properties);
		
		
		
	}
	public static void runGUI() {
		Broker.debug=false;
		
		Properties properties = new Properties();
		try { 
			properties.load(new FileInputStream(new File("resources/evolution/default.properties")));
		} catch (IOException e) {e.printStackTrace(); } 

		java.awt.EventQueue.invokeLater(()->{
            	GrammaticalEvolutionMainFrame f = new GrammaticalEvolutionMainFrame(MarketSystemExperiment.class,properties);
            	((MarketSystemExperiment)f.getExperiment()).fitnesscollOp.setFrame(f);
                f.setVisible(true);
            
        });
		
		
	}
	
}
