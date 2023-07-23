package market_system.backtest.stats;

import java.time.LocalDateTime;
import java.util.List;


import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Deal;
import market_system.backtest.broker.Trade;
import market_system.backtest.view.results.TradesPanel;

public class TradesStats extends BackTestStats{

	TradesPanel tradesPanel;
	public TradesStats() {
		
	}
	public void setPanelObserver(TradesPanel tradesPanel) {
		this.tradesPanel = tradesPanel;
	}
	@Override
	public void onTick(LocalDateTime date, Broker broker) {
		List<Trade> otrades = broker.getOpenTrades();
		List<Deal> deals = broker.getDeals();
		if(this.tradesPanel!=null)tradesPanel.update(deals, otrades);

		/*((DefaultCategoryDataset)dataset1).addValue(position.getEquity(), "equity", date);
		((DefaultCategoryDataset)dataset1).addValue(position.getBalance(), "balance", date);
		frame.repaint();*/
	}
	@Override
	public void onEnd(Broker broker) {
		// TODO Auto-generated method stub
		
	}
}
