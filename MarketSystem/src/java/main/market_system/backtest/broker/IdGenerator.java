package market_system.backtest.broker;

public class IdGenerator {
	private int count;
	public IdGenerator() {
		
	}
	public int getNext() {
		return count++;
	}
}
