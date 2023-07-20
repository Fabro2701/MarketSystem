package market_system.backtest.view.series.render;

import java.awt.image.BufferedImage;

import market_system.backtest.data.MarketData;

public abstract class SeriesRenderer {
	BufferedImage buff;
	public abstract void update(MarketData data);
}
