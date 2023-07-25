package market_system.evolution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import market_system.backtest.broker.Broker;
import model.Experiment;
import model.algorithm.AbstractPipeline;
import model.algorithm.AbstractSearchAlgorithm;
import model.algorithm.SimplePipeline;
import model.grammar.AbstractGrammar;
import model.module.CollectorModule;
import model.module.CrossoverModule;
import model.module.FitnessModule;
import model.module.InitializationModule;
import model.module.JoinModule;
import model.module.MutationModule;
import model.module.SelectionModule;
import model.module.operator.Operator;
import model.module.operator.collector.FitnessCollectorOperator;
import model.module.operator.collector.SimilarityCollectorOperator;
import model.module.operator.crossover.CrossoverOperator;
import model.module.operator.fitness.FitnessEvaluationOperator;
import model.module.operator.initialization.InitializationOperator;
import model.module.operator.join.JoinOperator;
import model.module.operator.mutation.MutationOperator;
import model.module.operator.selection.SelectionOperator;


public class Test extends Experiment{
	public Test(AbstractSearchAlgorithm algo) {
		super(algo);
	}
	public Test() {
		super();
	}
	@Override
	public void setup(Properties properties) {
		AbstractPipeline initPipeline = new SimplePipeline();
		
		AbstractGrammar grammar = this.loadGrammar(properties);
		System.out.println(grammar);
	
		InitializationModule initModule = new InitializationModule(generalPopulation, properties,rnd);
		InitializationOperator rinitOp = this.loadInitializer(properties, grammar);
		initModule.addOperator(rinitOp);
		
		FitnessModule initFitnessModule = new FitnessModule(generalPopulation, properties,rnd);
		FitnessEvaluationOperator fitnessInitOp = new BalanceFitnessOperator(properties,rnd);
		initFitnessModule.addOperator(fitnessInitOp);
		
		CollectorModule fitnesscollModule = new CollectorModule(generalPopulation, properties,rnd);
		Operator fitnesscollOp = new FitnessCollectorOperator(properties,rnd);
		fitnesscollModule.addOperator(fitnesscollOp);

		//loop
		AbstractPipeline loopPipeline = new SimplePipeline();
		
		SelectionModule selectionModule = new SelectionModule(generalPopulation, properties, rnd, selectedPopulation);
		SelectionOperator selectionOp = this.loadSelection(properties);
		selectionModule.addOperator(selectionOp);
		
		CrossoverModule crossoverModule = new CrossoverModule(selectedPopulation, properties, rnd);
		CrossoverOperator crossoverOp = this.loadCrossover(properties);
		crossoverModule.addOperator(crossoverOp);
		
		MutationModule mutationModule = new MutationModule(selectedPopulation, properties, rnd);
		MutationOperator mutationOp = this.loadMutation(properties);
		mutationModule.addOperator(mutationOp);
		
		FitnessModule fitnessModule = new FitnessModule(selectedPopulation, properties,rnd);
		FitnessEvaluationOperator fitnessOp = new BalanceFitnessOperator(properties,rnd);
		fitnessModule.addOperator(fitnessOp);
		
		JoinModule joinModule = new JoinModule(generalPopulation, properties, rnd, selectedPopulation);
		JoinOperator joinOp = this.loadJoin(properties);
		joinModule.addOperator(joinOp);
		
		CollectorModule simicollModule = new CollectorModule(generalPopulation, properties,rnd);
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
		loopPipeline.addModule(simicollModule);
		
		this.algorithm.setInitPipeline(initPipeline);
		this.algorithm.setLoopPipeline(loopPipeline);
	}
	public static void main(String args[]) {
		Broker.debug=false;
		Properties properties = new Properties();
		try { 
			properties.load(new FileInputStream(new File("resources/evolution/default.properties")));
		} catch (IOException e) {e.printStackTrace(); } 
		
		Test test = new Test();
		test.setup(properties);
		test.run(properties);
	}
	
}
