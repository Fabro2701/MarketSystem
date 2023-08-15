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
	public void update(Graphics2D g2, MarketData data, int cursor, double max, double min) {

    	int lastclose = (int) (Util.map(data.getCandle(cursor).close,max,min)*SeriesViewerPanel.height);
		double lastask = Util.map(data.getCandle(cursor).ask,max,min)*SeriesViewerPanel.height;
		double lastbid = Util.map(data.getCandle(cursor).bid,max,min)*SeriesViewerPanel.height;
    	for(int i=cursor+1;i-cursor<RenderConstants.windowSize && i<data.size();i++) {
    		CandleData cd=data.getCandle(i);
    		double close = Util.map(cd.close,max,min)*SeriesViewerPanel.height;
    		double ask = Util.map(cd.ask,max,min)*SeriesViewerPanel.height;
    		double bid = Util.map(cd.bid,max,min)*SeriesViewerPanel.height;

    		double shift1 = ((i-cursor)-1)*tickShift;
    		double shift2 = ((i-cursor))*tickShift;

    		g2.setColor(RenderConstants.linechartColor);
    		g2.setStroke(RenderConstants.linechartStroke);
    		g2.drawLine((int)(shift1), (int)lastclose, (int)(shift2), (int)close);
    		
    		g2.setColor(Color.black);
    		g2.setStroke(RenderConstants.thinlinechartStroke);
    		g2.drawLine((int)(shift1), (int)lastask, (int)(shift2), (int)ask);
    		g2.drawLine((int)(shift1), (int)lastbid, (int)(shift2), (int)bid);
    		lastclose=(int) close;
    		lastask=(int) ask;
    		lastbid=(int) bid;
    	}
	}

}