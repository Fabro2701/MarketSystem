package market_system.backtest.broker;

import java.util.List;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Position {
	double volume;
	POSITION_TYPE type;
	double balance,equity;
	int dealsCursor;
	
	public Position(double balance) {
		this.balance = balance;
		this.dealsCursor = 0;
	}
	public enum POSITION_TYPE{
		LONG,SHORT;
	}

	public void update(List<Trade> trades, List<Deal>deals,double close) {
		
		//update equity value
		equity = 0;
		for(Trade trade:trades) {
			double openPrice = trade.getOpenPrice();
			double volume = trade.getVolume();
			ORDER_TYPE type = trade.getType();
			
			if(type==ORDER_TYPE.BUY)equity += (openPrice+(close-openPrice))*volume;
			else equity += (openPrice+(openPrice-close))*volume;
		}
		equity += this.balance;
		
		
		/*for(int i=dealsCursor;i<deals.size();i++) {
			balance += deals.get(i).getProfit();
		}dealsCursor=deals.size();*/
	}
	public double getBalance() {
		return this.balance;
	}
	public double getEquity() {
		return equity;
	}
	public void substractFromBalance(double d) {
		this.balance -= d;
	}
	public void addToBalance(double d) {
		this.balance += d;
	}
	
	@Override
	public String toString() {
		return String.format("Balance: %f Equity:%f", this.balance,this.equity);
	}
}
