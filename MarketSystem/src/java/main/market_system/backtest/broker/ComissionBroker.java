package market_system.backtest.broker;

import java.time.LocalDateTime;
import java.util.Map;

import market_system.backtest.data.CandleData;

public class ComissionBroker extends Broker {
	public ComissionBroker(Client client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	double comission;

	@Override
	public void onInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(int idx, LocalDateTime date, CandleData candleData, Map<String, Double> indicatorsMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub

	}

}
