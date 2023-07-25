package market_system.backtest.stats;

import java.time.LocalDateTime;
import java.util.List;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Deal;
import market_system.backtest.broker.Trade;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.view.results.ResultsObserver;
import market_system.backtest.view.results.TradesPanel;

public class TradesStats extends BackTestStats{

	ResultsObserver tradesPanel,metricsPanel;
	
	int nTrades;
	double shortTradesWin,longTradesWin;
	double largestProfit,largestLoss;
	double avgProfit,avgLoss;
	public TradesStats() {
		
	}
	public void setTradesPanelObserver(ResultsObserver tradesPanel) {
		this.tradesPanel = tradesPanel;
	}
	public void setMetricsPanelObserver(ResultsObserver metricsPanel) {
		this.metricsPanel = metricsPanel;
	}
	public void onTick(LocalDateTime date, Broker broker) {
		List<Trade> otrades = broker.getOpenTrades();
		List<Deal> deals = broker.getDeals();
		if(this.tradesPanel!=null)tradesPanel.update(null,null,deals, otrades);

		/*((DefaultCategoryDataset)dataset1).addValue(position.getEquity(), "equity", date);
		((DefaultCategoryDataset)dataset1).addValue(position.getBalance(), "balance", date);
		frame.repaint();*/
	}
	@Override
	public void onEnd(LocalDateTime date, Broker broker) {
		List<Deal> deals = broker.getDeals();
		this.nTrades = deals.size();
		
		this.largestProfit=0d;
		this.largestLoss=0d;
		this.avgLoss=0d;
		this.avgProfit=0d;
		this.shortTradesWin=0d;
		this.longTradesWin=0d;
		if(nTrades!=0) {
			double shortTradesWin=0,longTradesWin=0;
			double profit;
			for(Deal deal:deals) {
				profit = deal.getProfit();
				if(profit>0d) {
					avgProfit+=profit;
					largestProfit = Math.max(largestProfit, profit);
				}
				else {
					avgLoss+=profit;
					largestLoss = Math.min(largestLoss, profit);
				}
				if(deal.getTrade().getType()==ORDER_TYPE.BUY)longTradesWin += profit>0d?1:0;
				else shortTradesWin += profit>0d?1:0;
			}
			avgProfit /= nTrades;
			avgLoss /= nTrades;
			
			this.longTradesWin = longTradesWin/nTrades;
			this.shortTradesWin = shortTradesWin/nTrades;
		}
		
		if(this.metricsPanel!=null)metricsPanel.update(null,null,deals, null);

	}
	
	//getters
	public int getnTrades() {
		return nTrades;
	}
	public double getShortTradesWin() {
		return shortTradesWin;
	}
	public double getLongTradesWin() {
		return longTradesWin;
	}
	public double getLargestProfit() {
		return largestProfit;
	}
	public double getLargestLoss() {
		return largestLoss;
	}
	public double getAvgProfit() {
		return avgProfit;
	}
	public double getAvgLoss() {
		return avgLoss;
	}
}
