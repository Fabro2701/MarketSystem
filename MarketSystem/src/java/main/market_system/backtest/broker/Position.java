package market_system.backtest.broker;

import java.util.List;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Position {
	double balance,equity;
	double initial;
	
	double maxEquity;
	double mdd;
	
	public Position(double balance) {
		this.balance = balance;
		this.initial = balance;
		this.maxEquity = 0d;
		this.mdd = 0d;
	}


	public void update(List<Trade> trades, List<Deal>deals,double close) {
		
		//update equity value
		equity = 0;
		for(Trade trade:trades) {
			double openPrice = trade.getOpenPrice();
			double volume = trade.getVolume();
			ORDER_TYPE type = trade.getType();
			
			if(type==ORDER_TYPE.BUY)equity += ((close-openPrice))*volume;
			else equity += ((openPrice-close))*volume;
		}
		equity += this.balance;
		
		maxEquity = Math.max(maxEquity, equity);
		if(equity<maxEquity) mdd = Math.max(mdd, (maxEquity-equity)/maxEquity);
	}
	public double getBalance() {
		return this.balance;
	}
	public double getEquity() {
		return equity;
	}
	public double getInitialBalance() {
		return initial;
	}
	public void substractFromBalance(double d) {
		this.balance -= d;
	}
	public void addToBalance(double d) {
		this.balance += d;
	}
	public double getMaximumDrawdown() {
		return mdd;
	}
	
	@Override
	public String toString() {
		return String.format("Balance: %f Equity:%f", this.balance,this.equity);
	}
}
