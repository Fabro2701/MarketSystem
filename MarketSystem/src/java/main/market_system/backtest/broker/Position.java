package market_system.backtest.broker;

import java.util.List;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Position {
	double volume;
	POSITION_TYPE type;
	double balance, margin,equity;
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
		equity = 0;
		for(Trade trade:trades) {
			double openPrice = trade.getOpenPrice();
			double volume = trade.getVolume();
			ORDER_TYPE type = trade.getType();
			
			margin += Math.abs((close-openPrice)*volume);
			if(type==ORDER_TYPE.BUY)equity += (close-openPrice)*volume;
			else equity -= (close-openPrice)*volume;
		}
		
		for(int i=dealsCursor;i<deals.size();i++) {
			balance += deals.get(i).getProfit();
		}dealsCursor=deals.size();
	}
	public double getBalance() {
		return this.balance;
	}
	public double getMargin() {
		return this.balance-margin;
	}
	public double getEquity() {
		return this.balance+equity;
	}
	public void substract(double d) {
		this.balance -= d;
	}
	
	@Override
	public String toString() {
		return String.format("Balance: %f Equity:%f Margin:%f", this.balance,this.equity,this.margin);
	}
}
