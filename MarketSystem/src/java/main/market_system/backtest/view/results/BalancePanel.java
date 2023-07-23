/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package market_system.backtest.view.results;

import java.awt.Color;
import java.time.LocalDateTime;
import market_system.backtest.BackTest;
import market_system.backtest.broker.Position;
import market_system.backtest.stats.BackTestStats;
import market_system.backtest.stats.PositionStats;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Fabrizio Ortega
 */
public class BalancePanel extends javax.swing.JPanel {
	DefaultCategoryDataset data;

    /**
     * Creates new form BalancePanel
     */
    public BalancePanel() {
        initComponents();
    }
    public BalancePanel(BackTest backtest) {
        initComponents();
        data = new DefaultCategoryDataset();
        for(BackTestStats s:backtest.getStatsObservers()) {
            if(s instanceof PositionStats) {
                    ((PositionStats)s).setPanelObserver(this);
            }
        }

       JFreeChart chart = ChartFactory.createLineChart(
            "",
            "","",
            data,
            PlotOrientation.VERTICAL,
            true,true,false);
       
        chart.getPlot().setBackgroundPaint(Color.white);
        

	ChartPanel panel = new ChartPanel(chart); 
	panel.setPreferredSize( new java.awt.Dimension(2174, 263));
        this.add(panel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(2174, 263));
        setMinimumSize(new java.awt.Dimension(2174, 263));
        setPreferredSize(new java.awt.Dimension(2174, 263));
    }// </editor-fold>//GEN-END:initComponents

	public void update(LocalDateTime date, Position position) {
		data.addValue(position.getEquity(), "equity", date);
		data.addValue(position.getBalance(), "balance", date);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
