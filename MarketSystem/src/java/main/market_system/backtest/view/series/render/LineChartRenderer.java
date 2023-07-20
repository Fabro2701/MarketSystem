package market_system.backtest.view.series.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import market_system.backtest.data.CandleData;
import market_system.backtest.data.MarketData;
import market_system.backtest.util.Util;
import market_system.backtest.view.series.SeriesViewerPanel;


import static market_system.backtest.view.series.render.RenderConstants.tickShift;

public class LineChartRenderer extends PricesRenderer{
	@Override
	public void update(Graphics2D g2, MarketData data, int cursor) {

		double max=data.get(0).low,min=data.get(0).high;
    	for(int i=cursor;i-cursor<RenderConstants.windowSize && i<data.size();i++) {
    		max=Math.max(max, data.get(i).high);
    		min=Math.min(min, data.get(i).low);
    	}
		g2.setColor(RenderConstants.linechartColor);
		g2.setStroke(RenderConstants.linechartStroke);
    	int lastclose = (int) (Util.map(data.get(cursor).close,max,min)*SeriesViewerPanel.height);
    	for(int i=cursor+1;i-cursor<RenderConstants.windowSize && i<data.size();i++) {
    		CandleData cd=data.get(i);
    		double close = Util.map(cd.close,max,min)*SeriesViewerPanel.height;

    		double shift1 = (i-1)*tickShift;
    		double shift2 = (i)*tickShift;
    		g2.drawLine((int)(shift1), (int)lastclose, (int)(shift2), (int)close);
    		lastclose=(int) close;
    	}
	}

}