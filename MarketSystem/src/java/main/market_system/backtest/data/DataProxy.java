package market_system.backtest.data;

public class DataProxy {
	MarketData data;
	int cursor;
	public double getValue(String id) {
		int p = id.lastIndexOf("_");
		String indicator = id.substring(0, p);
		int w = Integer.valueOf(id.substring(p+1));
		
		return data.indicators.get(indicator).get(cursor>=w?cursor-w:cursor);
	}
	public void setData(MarketData data) {
		this.data = data;
	}
	public void setCursor(int cursor) {
		this.cursor = cursor;
	}
}
