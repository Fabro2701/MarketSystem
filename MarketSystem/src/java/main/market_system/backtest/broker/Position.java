package market_system.backtest.broker;

import java.util.List;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Position {
	double volume;
	POSITION_TYPE type;
	double amount, tmpTrades;//profit & margin
	int dealsCursor;
	
	public Position() {
		this.amount = 0d;
		this.tmpTrades = 0d;
		this.dealsCursor = 0;
	}
	public enum POSITION_TYPE{
		LONG,SHORT;
	}

	public void update(List<Trade> trades, List<Deal>deals,double close) {
		amount -= tmpTrades;
		tmpTrades = 0;
		for(Trade trade:trades) {
			double openPrice = trade.getOpenPrice();
			double volume = trade.getVolume();
			ORDER_TYPE type = trade.getType();
			
			if(type==ORDER_TYPE.BUY) {
				tmpTrades += (close-openPrice)*volume;
			}
			else if(type==ORDER_TYPE.SELL) {
				tmpTrades += (openPrice-close)*volume;
			}
		}
		amount += tmpTrades;
		
		for(int i=dealsCursor;i<deals.size();i++) {
			amount += deals.get(i).getProfit();
		}dealsCursor=deals.size();
	}
}
