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
	double sharpeRatio,calmarRatio;
	double mdd;
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
		this.sharpeRatio=0d;
		this.calmarRatio=0d;
		this.mdd=0d;
		
		int nlongTrades=0;
		int nshortrades=0;
		if(nTrades>1) {
			double shortTradesWin=0,longTradesWin=0;
			double profit;
			double r = 0d;
			double er = 0d, or = 0d;
			double initial = broker.getPosition().getInitialBalance();
			double max = initial;
			double acc = 0d;
			for(Deal deal:deals) er += (deal.getTrade().getType()==ORDER_TYPE.SELL?-1:1)*(deal.getClosePrice()-deal.getOpenPrice())/deal.getOpenPrice();
			er /= nTrades;
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
				if(deal.getTrade().getType()==ORDER_TYPE.BUY) {
					longTradesWin += profit>0d?1:0;
					nlongTrades++;
				}
				else {
					shortTradesWin += profit>0d?1:0;
					nshortrades++;
				}
				
				r = (deal.getTrade().getType()==ORDER_TYPE.SELL?-1:1)*(deal.getClosePrice()-deal.getOpenPrice())/deal.getOpenPrice();
				or += Math.pow(r - er, 2);
				acc += r;
				max = Math.max(max, acc+initial);
				mdd = Math.max(mdd, (max-(acc+initial))/max);
			}
			or = Math.sqrt(or/(nTrades-1));
			
			this.sharpeRatio = er/or;
			this.calmarRatio = mdd!=0?er/mdd:0;
			
			this.longTradesWin = nlongTrades>0?longTradesWin/nlongTrades:0;
			this.shortTradesWin = nshortrades>0?shortTradesWin/nshortrades:0;
			
			if(longTradesWin+shortTradesWin>0) {
				avgProfit /= (longTradesWin+shortTradesWin);
				avgLoss /= nTrades-(longTradesWin+shortTradesWin);
			}
			
			
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
	public double getSharpeRatio() {
		return sharpeRatio;
	}
	public double getCalmarRatio() {
		return calmarRatio;
	}
	public double getMaximumDrawdown() {
		return mdd;
	}
}
