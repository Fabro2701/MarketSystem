package market_system.backtest.broker;

import java.util.List;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Position {
	double volume;
	POSITION_TYPE type;
	double balance, margin;//profit & margin
	int dealsCursor;
	
	public Position(double balance) {
		this.balance = balance;
		this.margin = 0d;
		this.dealsCursor = 0;
	}
	public enum POSITION_TYPE{
		LONG,SHORT;
	}

	public void update(List<Trade> trades, List<Deal>deals,double close) {
		
		margin = 0;
		for(Trade trade:trades) {
			double openPrice = trade.getOpenPrice();
			double volume = trade.getVolume();
			ORDER_TYPE type = trade.getType();
			
			if(type==ORDER_TYPE.BUY) {
				margin += (close-openPrice)*volume;
			}
			else if(type==ORDER_TYPE.SELL) {
				margin += (openPrice-close)*volume;
			}
		}
		
		for(int i=dealsCursor;i<deals.size();i++) {
			balance += deals.get(i).getProfit();
		}dealsCursor=deals.size();
	}
	public double getBalance() {
		return this.balance;
	}
	public double getEquity() {
		return this.balance-margin;
	}
}
