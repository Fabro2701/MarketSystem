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
	}
	@Override
	public void onTick(int idx, LocalDateTime date, CandleData candleData, Map<String, Double> indicators, Broker broker) {
		map.clear();
		map.putAll(indicators);
		map.put("cd", candleData);
		map.put("res", res);
		try {
			Object r = eval.evaluate(map);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res.bull>10)broker.sendFixedTimeOrder(ORDER_TYPE.BUY, 1d, 5L, null);
	}

}
