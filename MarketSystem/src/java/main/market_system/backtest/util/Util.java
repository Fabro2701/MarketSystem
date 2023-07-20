package market_system.backtest.util;

public class Util {
	/**
	 * map query given the range min-max
	 * @param query
	 * @param min
	 * @param max
	 * @return value between 0 and 1
	 */
	public static double map(double query, double min, double max) {
		return (query-min)/(max-min);//1-0
	}
}
