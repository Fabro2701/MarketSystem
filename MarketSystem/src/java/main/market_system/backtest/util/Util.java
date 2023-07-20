package market_system.backtest.util;

public class Util {
	public static double map(double query, double min, double max) {
		return (query-min)/(max-min);
	}
}
