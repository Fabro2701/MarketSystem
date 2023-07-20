package market_system.backtest.view.series.render;

import java.awt.Color;
import java.awt.Graphics2D;

import market_system.backtest.data.CandleData;
import market_system.backtest.data.MarketData;
import market_system.backtest.util.Util;
import market_system.backtest.view.series.SeriesViewerPanel;

public class CandlesRenderer extends PricesRenderer{
	private static double candleWidth = 5d; 
	@Override
	public void update(Graphics2D g2, MarketData data, int cursor){
		double max=data.get(0).low,min=data.get(0).high;
    	for(int i=cursor;i-cursor<windowSize && i<data.size();i++) {
    		max=Math.max(max, data.get(i).high);
    		min=Math.min(min, data.get(i).low);
    	}
    	
    	for(int i=cursor;i-cursor<windowSize && i<data.size();i++) {
    		CandleData cd=data.get(i);
    		double open = Util.map(cd.open,max,min)*SeriesViewerPanel.height;
    		double close = Util.map(cd.close,max,min)*SeriesViewerPanel.height;
    		double high = Util.map(cd.high,max,min)*SeriesViewerPanel.height;
    		double low = Util.map(cd.low,max,min)*SeriesViewerPanel.height;
    		double candleBody = Math.abs(open-close);
    		
    		double shift = i*8d;
    		g2.setColor(Color.BLACK);
    		g2.drawLine((int)(shift+candleWidth/2), (int)low, (int)(shift+candleWidth/2), (int)high);
    		if(cd.close >= cd.open){//bull
    			g2.setColor(Color.GREEN);
    			g2.fillRect((int)shift, (int)(close), (int)candleWidth, (int)candleBody);
    			
    		}
    		else {//bear
    			g2.setColor(Color.RED);
    			g2.fillRect((int)shift, (int)(open), (int)candleWidth, (int)candleBody);

    		}
    	}
	}

}
