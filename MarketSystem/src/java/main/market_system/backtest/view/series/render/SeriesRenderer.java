package market_system.backtest.view.series.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import market_system.backtest.data.MarketData;

public abstract class SeriesRenderer {
	public SeriesRenderer() {
	}
	public abstract void update(Graphics2D g2, MarketData data, int cursor);
}
