package market_system.backtest.strategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.data.CandleData;
import market_system.evaluation.ActionEvaluator;
import market_system.evaluation.EvaluationException;
import market_system.evaluation.OOPParser;

public class GrammarBasedStrategy extends Strategy {
	JSONObject code;
	ActionEvaluator eval;
	Map<String, Object>map;
	Result res;
	public GrammarBasedStrategy(String code) {
		this.code = new OOPParser().parse(code);
		this.map = new HashMap<>();
		eval = new ActionEvaluator(this.code.getJSONArray("code"));
		res = new Result();
	}
	public class Result{
		public double bull,bear;
		public Result() {
			bull=0d;
			bear=0d;
		}
		public void reset() {
			bull=0d;
			bear=0d;
		}
	}
	@Override
	public void onTick(int idx, LocalDateTime date, CandleData candleData, Map<String, Double> indicators, Broker broker) {
		map.clear();
		map.putAll(indicators);
		map.put("cd", candleData);
		map.put("res", res);
		//res.reset();
		this.resultStrat(broker);
		//this.valueStrat(broker);
		
	}
	private void valueStrat(Broker broker) {
		double n=0d;
		try {
			n = (Double)eval.evaluate(map);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			e.printStackTrace();
		}
		double threshold=5d;

		if(n>=threshold)broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 2d, 15L, null);
		else if(n<=-threshold)broker.sendFixedTimeOrder(ORDER_TYPE.SELL, 2d, 15L, null);
	}
	private void resultStrat(Broker broker) {
		try {
			eval.evaluate(map);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			e.printStackTrace();
		}
		double threshold=10d;
		int r=0;
		if(res.bull>threshold)r++;
		if(res.bear>threshold)r--;
		if(r>0)broker.sendFixedTimeOrder(ORDER_TYPE.BUY, Math.min(res.bull/10d,5), 15L, null);
		if(r<0)broker.sendFixedTimeOrder(ORDER_TYPE.SELL, Math.min(res.bear/10d,5), 15L, null);
	}

}
