package market_system.backtest.view.series.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Deal;
import market_system.backtest.broker.Trade;
import market_system.backtest.broker.order.Order.ORDER_TYPE;
import market_system.backtest.util.Util;
import market_system.backtest.view.series.SeriesViewerPanel;

public class OperationsRenderer {
	double arrowWidth=5d,arrowHeight=10d;
	public void render(Graphics2D g2, Broker broker, int cursor, double max, double min) {
		List<Deal> deals = broker.getDeals();
		g2.setColor(Color.black);
		for(Deal deal:deals) {
			double open = Util.map(deal.getOpenPrice(),max,min)*SeriesViewerPanel.height;
			double close = Util.map(deal.getClosePrice(),max,min)*SeriesViewerPanel.height;
			int ini = deal.getIniIdx();
			int end = deal.getEndIdx();
			int c=0;
			boolean longPos = deal.getTrade().getType()==ORDER_TYPE.BUY?true:false;
			double x1=(ini-cursor)*RenderConstants.tickShift, x2=(end-cursor)*RenderConstants.tickShift;
			double y1=open, y2=close;
			if(ini>=cursor&&ini<cursor+RenderConstants.windowSize){
				//g2.fillOval((int)((ini-cursor)*RenderConstants.tickShift), (int)open, 5, 5);
				if(longPos)drawLongPosition(g2,x1,y1);
				else drawShortPosition(g2,x1,y1);
				c=2;
			}
			if(end>=cursor&&end<cursor+RenderConstants.windowSize){
				//g2.fillOval((int)((end-cursor)*RenderConstants.tickShift), (int)close, 5, 5);
				if(!longPos)drawLongPosition(g2,x2,y2);
				else drawShortPosition(g2,x2,y2);
				c++;
			}
			if(longPos)g2.setColor(RenderConstants.bullCandleColor);
			else g2.setColor(RenderConstants.bearCandleColor);
			
			if(c==3) {
				g2.drawLine((int)x1, (int)y1, 
							(int)x2, (int)y2);
			}
			else if(c==1) {
				double m = (y2-y1)/(x2-x1);
				double b = y1-m*x1;
				double y = 0*m + b;
				g2.drawLine((int)0, (int)y, 
							(int)x2, (int)y2);
			}
			else if(c==2) {
				double m = (y2-y1)/(x2-x1);
				double b = y1-m*x1;
				double y = (SeriesViewerPanel.width-1)*m + b;
				g2.drawLine((int)x1,(int)y1,(int)(SeriesViewerPanel.width-1), (int)y);
			}
		}
		/*for(Trade trade:broker.getOpenTrades()) {
			double open = Util.map(trade.getOpenPrice(),max,min)*SeriesViewerPanel.height;
			int ini = trade.getIdx();
			double x1=(ini-cursor)*RenderConstants.tickShift, x2;
			double y1=open, y2;
			
		}*/
	}
	private void drawLongPosition(Graphics2D g2, double x, double y) {
		g2.setColor(RenderConstants.bullCandleColor);
		double baseWidth=3d,baseHeight=4d;
		double arrowWidth=3d,arrowHeight=7d;
		g2.fillPolygon(new int[]{(int)(x-baseWidth),(int)(x-baseWidth),(int)(x-(baseWidth+arrowWidth)),
							     (int)x,
							     (int)(x+(baseWidth+arrowWidth)),(int)(x+baseWidth),(int)(x+baseWidth)}, 
					   new int[]{(int)(y+(baseHeight+arrowHeight)),(int)(y+arrowHeight),(int)(y+arrowHeight),
							     (int)(y),
							     (int)(y+arrowHeight),(int)(y+arrowHeight),(int)(y+(baseHeight+arrowHeight))}, 
					   7);
	}
	private void drawShortPosition(Graphics2D g2, double x, double y) {
		g2.setColor(RenderConstants.bearCandleColor);
		double baseWidth=3d,baseHeight=4d;
		double arrowWidth=3d,arrowHeight=7d;
		g2.fillPolygon(new int[]{(int)(x-baseWidth),(int)(x-baseWidth),(int)(x-(baseWidth+arrowWidth)),
							     (int)x,
							     (int)(x+(baseWidth+arrowWidth)),(int)(x+baseWidth),(int)(x+baseWidth)}, 
					   new int[]{(int)(y-(baseHeight+arrowHeight)),(int)(y-arrowHeight),(int)(y-arrowHeight),
							     (int)(y),
							     (int)(y-arrowHeight),(int)(y-arrowHeight),(int)(y-(baseHeight+arrowHeight))}, 
					   7);
	}
}
