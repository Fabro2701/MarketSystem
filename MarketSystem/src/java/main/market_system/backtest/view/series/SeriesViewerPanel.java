/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package market_system.backtest.view.series;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import market_system.backtest.data.MarketData;
import market_system.backtest.view.series.render.CandlesRenderer;
import market_system.backtest.view.series.render.IndicatorRenderer;
import market_system.backtest.view.series.render.LineChartRenderer;
import market_system.backtest.view.series.render.PricesRenderer;
import market_system.backtest.view.series.render.RenderConstants;

/**
 *
 * @author Fabrizio Ortega
 */
public class SeriesViewerPanel extends javax.swing.JPanel {
	public static double height = 466,width=1219;
	Graphics2D g2;
	BufferedImage bufferImage;
	List<PricesRenderer>priceRenderers;
	List<IndicatorRenderer>indicatorsRenderers;

    /**
     * Creates new form SeriesViewerPanel
     */
    public SeriesViewerPanel(SeriesPlayerController ctrl) {
        initComponents();
        
        
        bufferImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_4BYTE_ABGR);
        g2=bufferImage.createGraphics();
        
        priceRenderers = List.of(new CandlesRenderer(), new LineChartRenderer());
        //priceRenderers = List.of(new CandlesRenderer());
        
		MarketData md = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSDr.csv");
		paintRange(md,0);
    }
    
   
	public void paintRange(MarketData data, int ini) {

		g2.setColor(RenderConstants.backgroundColor);
    	g2.fillRect(0, 0, (int)width, (int)height);
    	
    	for(PricesRenderer r:this.priceRenderers) {
    		r.update(g2, data, ini);
    	}

    }
    @Override
    public void paintComponent(Graphics g) {
    	g.drawImage(bufferImage, 0, 0, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(1219, 466));
        setMinimumSize(new java.awt.Dimension(1219, 466));
        setPreferredSize(new java.awt.Dimension(1219, 466));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1219, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}