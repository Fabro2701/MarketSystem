package market_system.backtest.broker;

import java.time.LocalDateTime;

import market_system.backtest.broker.order.Order.ORDER_TYPE;

public class Deal {
	int id,iniIdx,endIdx;
	double volume,openPrice,closePrice,profit;
	LocalDateTime openTime, closeTime;
	Trade trade;
	public Deal(int id, int idx, LocalDateTime date, double ask, double bid, Trade trade) {
		this.id = id;
		this.iniIdx = trade.getIdx();
		this.endIdx = idx;
		this.closeTime = date;
		this.volume = trade.getVolume();
		this.openTime = trade.getOpenDate();
		this.openPrice = trade.getOpenPrice();
		this.trade = trade;
		
		if(trade.getType()==ORDER_TYPE.BUY) {
			this.closePrice = bid;
			this.profit = (closePrice-openPrice)*volume;
		}
		else {
			this.closePrice = ask;
			this.profit = (openPrice-closePrice)*volume;
		}
		
		
	}
	public int getId() {
		return id;
	}
	public double getVolume() {
		return volume;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public double getProfit() {
		return profit;
	}
	public LocalDateTime getOpenTime() {
		return openTime;
	}
	public LocalDateTime getCloseTime() {
		return closeTime;
	}
	public int getIniIdx() {
		return iniIdx;
	}
	public int getEndIdx() {
		return endIdx;
	}
	public Trade getTrade() {
		return this.trade;
	}

}
