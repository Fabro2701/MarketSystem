package market_system.backtest.data;

public class CandleData {
	public double low,high,close,open;
	public double ask,bid;
	
	public CandleData(double open, double high, double low, double close, double ask, double bid) {
		this.low = low;
		this.high = high;
		this.close = close;
		this.open = open;
		this.ask = ask;
		this.bid = bid;
	}

	@Override
	public String toString() {
		return open+" "+high+" "+low+" "+close;
	}  
}
