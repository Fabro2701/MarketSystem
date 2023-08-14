package market_system.backtest.stats;

import java.time.LocalDateTime;

import javax.swing.JFrame;

import org.jfree.data.category.DefaultCategoryDataset;

import market_system.backtest.broker.Broker;
import market_system.backtest.broker.Position;
import market_system.backtest.view.results.ResultsObserver;

public class PositionStats extends BackTestStats{
	DefaultCategoryDataset dataset1,dataset2;
	JFrame frame;
	int updateRate=24;
	ResultsObserver balancePanel;
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
	public void setPanelObserver(ResultsObserver balancePanel) {
		this.balancePanel = balancePanel;
	}
	@Override
	public void onTick(int idx, LocalDateTime date, Broker broker) {
		Position position = broker.getPosition();
		//System.out.println(date+" "+position);
		if(this.balancePanel!=null && idx%this.updateRate==0)balancePanel.update(date, position, null, null);

		/*((DefaultCategoryDataset)dataset1).addValue(position.getEquity(), "equity", date);
		((DefaultCategoryDataset)dataset1).addValue(position.getBalance(), "balance", date);
		frame.repaint();*/
	}
	@Override
	public void onEnd(LocalDateTime date, Broker broker) {
		Position position = broker.getPosition();
		if(this.balancePanel!=null)balancePanel.update(date, position, null, null);

	}
}
