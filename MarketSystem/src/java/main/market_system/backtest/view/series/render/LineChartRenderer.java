package market_system.backtest.view.series.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import market_system.backtest.data.CandleData;
import market_system.backtest.data.MarketData;
import market_system.backtest.util.Util;
import market_system.backtest.view.series.SeriesViewerPanel;

public class LineChartRenderer extends PricesRenderer{
	Stroke stroke = new BasicStroke(2);
	@Override
	public void update(Graphics2D g2, MarketData data, int cursor) {

		double max=data.get(0).low,min=data.get(0).high;
    	for(int i=cursor;i-cursor<windowSize && i<data.size();i++) {
    		max=Math.max(max, data.get(i).high);
    		min=Math.min(min, data.get(i).low);
    	}
    	double candleWidth = 5d; 
		g2.setColor(Color.BLUE);
		g2.setStroke(stroke);
    	int lastclose = (int) (Util.map(data.get(cursor).close,max,min)*SeriesViewerPanel.height);
    	for(int i=cursor+1;i-cursor<windowSize && i<data.size();i++) {
    		CandleData cd=data.get(i);
    		double close = Util.map(cd.close,max,min)*SeriesViewerPanel.height;

    		double shift1 = (i-1)*8d;
    		double shift2 = (i)*8d;
    		g2.drawLine((int)(shift1+candleWidth/2), (int)lastclose, (int)(shift2+candleWidth/2), (int)close);
    		lastclose=(int) close;
    	}
	}

}