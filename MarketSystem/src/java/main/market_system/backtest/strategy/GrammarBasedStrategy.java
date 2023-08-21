package market_system.backtest.strategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.data.CandleData;
import market_system.backtest.data.DataProxy;
import market_system.evaluation.ActionEvaluator;
import market_system.evaluation.EvaluationException;
import market_system.evaluation.OOPParser;

public class GrammarBasedStrategy extends Strategy {
	JSONObject code;
	ActionEvaluator eval;
	Map<String, Object>map;
	Result res;
	volatile static Map<String, JSONObject>cache = new HashMap<>();
	
	public GrammarBasedStrategy(String code) {
		if(cache.containsKey(code)) this.code = cache.get(code);
		else {
			if(cache.size()>5000)cache.clear();
			this.code = new OOPParser().parse(code);
			cache.put(code, this.code);
		}
		
		this.map = new HashMap<>();
		eval = new ActionEvaluator(this.code.getJSONArray("code"));
		res = new Result();
	}
	public class Result{
		public double bull,bear;
		public double atr;
		public Result() {
			bull=0d;
			bear=0d;
			atr=0d;
		}
		public void reset() {
			bull=0d;
			bear=0d;
			atr=0d;
		}
	}
	@Override
	public void onTick(int idx, LocalDateTime date, CandleData candleData, DataProxy dataProxy, Broker broker) {
		map.clear();
		//map.putAll(indicators);
		//map.put("cd", candleData);
		map.put("proxy", dataProxy);
		map.put("res", res);
		double atr = dataProxy.getValue("atr_0");
		//this.valueStrat(broker);
		this.resultStrat(broker,atr,idx, date, candleData);
		//this.oneWayResultStrat(broker,atr,idx, date, candleData);
		
	}
	private void valueStrat(Broker broker, double atr, CandleData candleData) {
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
	private void resultStrat(Broker broker, double atr, int idx, LocalDateTime date, CandleData cd) {
		//res.reset();
		res.bear *= 0.9;
		res.bull *= 0.9;
		try {
			eval.evaluate(map);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			e.printStackTrace();
		}
		double threshold=10d;
		int r=0;
		if(res.bull>threshold)r++;
		if(res.bear>threshold)r--;
		long duration=20L;
		double multAtr = 4d;
		//double multAtr = res.atr;
		if(r>0) {
			broker.closeTrades(ORDER_TYPE.SELL);
			broker.sendTPSLOrder(ORDER_TYPE.BUY, Math.min(res.bull/100d,0.5), cd.ask+atr*multAtr, cd.ask-atr*multAtr, null);
		}
		if(r<0) {
			broker.closeTrades(ORDER_TYPE.BUY);
			broker.sendTPSLOrder(ORDER_TYPE.SELL, Math.min(res.bear/100d,0.5), cd.bid-atr*multAtr, cd.bid+atr*multAtr, null);
		}
		/*if(r>0) {
			broker.closeTrades(ORDER_TYPE.SELL);
			broker.sendTPSLOrder(ORDER_TYPE.BUY, Math.min(res.bull/100d,0.5), cd.ask+atr*multAtr*2d, cd.ask-atr*multAtr/2d, null);
		}
		if(r<0) {
			broker.closeTrades(ORDER_TYPE.BUY);
			broker.sendTPSLOrder(ORDER_TYPE.SELL, Math.min(res.bear/100d,0.5), cd.bid-atr*multAtr*2d, cd.bid+atr*multAtr/2d, null);
		}*/
	}
	private void oneWayResultStrat(Broker broker, double atr, int idx, LocalDateTime date, CandleData cd) {
		res.reset();
		res.bear *= 0.9;
		res.bull *= 0.9;
		try {
			eval.evaluate(map);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			e.printStackTrace();
		}
		double threshold=10d;
		double multAtr = 4d;
		//double multAtr = res.atr;
		if(res.bull>threshold) {
			//System.out.println(res.bull);
			broker.sendTPSLOrder(ORDER_TYPE.BUY, Math.min(res.bull/100d,0.5), cd.ask+atr*multAtr, cd.ask-atr*multAtr, null);
		}
	}

}
