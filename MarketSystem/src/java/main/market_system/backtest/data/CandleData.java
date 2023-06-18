package market_system.backtest.data;

public class CandleData {
	public double low,high,close,open;
	
	public CandleData(double open, double high, double low, double close) {
		this.low = low;
		this.high = high;
		this.close = close;
		this.open = open;
	}

	@Override
	public String toString() {
		return open+" "+high+" "+low+" "+close;
	}  
}
