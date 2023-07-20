package market_system.backtest.view.series.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class RenderConstants {

	public static int windowSize=94;
	public static Color backgroundColor = Color.WHITE;
	
	public static double tickShift = 13d;

	public static Color bullCandleColor = new Color(0,176,97);
	public static Color bearCandleColor = new Color(255,48,49);
	public static Color candleBorderColor = Color.BLACK;
	
	public static Stroke linechartStroke = new BasicStroke(2);
	public static Color linechartColor = Color.BLUE;
	protected static double candleWidth = 7d; 
}
