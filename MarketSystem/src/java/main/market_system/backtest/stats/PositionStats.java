package market_system.backtest.stats;

import java.awt.Color;
import java.awt.EventQueue;
import java.time.LocalDateTime;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Position;
import market_system.backtest.view.results.BalancePanel;

public class PositionStats extends BackTestStats{
	DefaultCategoryDataset dataset1,dataset2;
	JFrame frame;
	int updateRate=10;
	BalancePanel balancePanel;
	public PositionStats() {
		/*frame = new JFrame();
		dataset1 = new DefaultCategoryDataset();
		dataset2 = new DefaultCategoryDataset();
		EventQueue.invokeLater(() -> {
			CategoryPlot plot = new CategoryPlot(); 
			
			CategoryItemRenderer renderer = new BarRenderer();
			plot.setDataset(2,dataset2);
			plot.setRenderer(2,renderer);  

			//add the second dataset, render as lines
			CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
			plot.setDataset(0, dataset1);
			plot.setRenderer(0, renderer2);
			plot.setDataset(1, dataset1);
			plot.setRenderer(1, renderer2);

			//set axis 
			plot.setDomainAxis(new CategoryAxis("Time"));
			plot.setRangeAxis(new NumberAxis("Value"));
			final JFreeChart result = new JFreeChart(
		            plot
		        );
			
			
			ChartPanel panel = new ChartPanel(result);  
			frame.add(panel);
			frame.pack();
			frame.setVisible(true);
			
        });*/
	}
	public void setPanelObserver(BalancePanel balancePanel) {
		this.balancePanel = balancePanel;
	}
	@Override
	public void onTick(LocalDateTime date, Broker broker) {
		Position position = broker.getPosition();
		//System.out.println(date+" "+position);
		if(this.balancePanel!=null)balancePanel.update(date, position);

		/*((DefaultCategoryDataset)dataset1).addValue(position.getEquity(), "equity", date);
		((DefaultCategoryDataset)dataset1).addValue(position.getBalance(), "balance", date);
		frame.repaint();*/
	}
	@Override
	public void onEnd(Broker broker) {
		// TODO Auto-generated method stub
		
	}
}
