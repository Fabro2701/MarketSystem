/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package market_system.backtest.view.series;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;
import market_system.backtest.BackTest;
import market_system.backtest.strategy.Strategy;
import market_system.backtest.strategy.UserStrategy;

/**
 *
 * @author Fabrizio Ortega
 */
public class SeriesPlayerPanel extends javax.swing.JPanel {
    BackTest backtest;
    SeriesPlayerController ctrl;
    boolean simStop;
    Strategy strategy = new UserStrategy();
    /**
     * Creates new form SeriesVisualizer
     */
    public SeriesPlayerPanel() {
        //ctrl = new SeriesPlayerController(this);
    
        
        initComponents();
        
        this.candleButton.setSelected(true);
    }
    public SeriesPlayerPanel(BackTest backtest) {
        this.backtest = backtest;
        ctrl = new SeriesPlayerController(this);
        
        initComponents();
        progressBar.setUI(new BasicProgressBarUI());
        
        ctrl.setData(backtest.getData());
        ctrl.setBroker(backtest.getBroker());
        
        this.candleButton.setSelected(true);
        this.pauseButton.setEnabled(false);
        simStop = true;
        

           
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        navigatorSlider = new javax.swing.JSlider();
        leftButton = new javax.swing.JButton();
        rightButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        speedField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        progressBar = new javax.swing.JProgressBar();
        jButton8 = new javax.swing.JButton();
        pauseButton = new javax.swing.JToggleButton();
        playButton = new javax.swing.JToggleButton();
        playplayButton = new javax.swing.JButton();
        candleButton = new javax.swing.JToggleButton();
        linechartButton = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        cursorField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        seriesViewerPanel =  new market_system.backtest.view.series.SeriesViewerPanel(ctrl);

        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        setPreferredSize(new java.awt.Dimension(1500, 568));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        navigatorSlider.setValue(0);
        navigatorSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                navigatorSliderStateChanged(evt);
            }
        });

        leftButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\left-arrow.png")); // NOI18N
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftButtonActionPerformed(evt);
            }
        });

        rightButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\right-arrow.png")); // NOI18N
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navigatorSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(navigatorSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(leftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Speed");

        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        speedField.setText("50");
        speedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Date");

        dateField.setText("0");
        dateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateFieldActionPerformed(evt);
            }
        });

        progressBar.setBackground(new java.awt.Color(255, 255, 255));
        progressBar.setForeground(new java.awt.Color(0, 204, 0));
        progressBar.setValue(10);
        progressBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton8.setText("jButton3");

        pauseButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\pause.png")); // NOI18N
        pauseButton.setPreferredSize(new java.awt.Dimension(35, 35));
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        playButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\play.png")); // NOI18N
        playButton.setPreferredSize(new java.awt.Dimension(35, 35));
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        playplayButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\play-play.png")); // NOI18N
        playplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playplayButtonActionPerformed(evt);
            }
        });

        candleButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\bars.png")); // NOI18N
        candleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        candleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                candleButtonActionPerformed(evt);
            }
        });

        linechartButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\line-chart.png")); // NOI18N
        linechartButton.setPreferredSize(new java.awt.Dimension(35, 35));
        linechartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linechartButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Cursor");

        cursorField.setColumns(5);
        cursorField.setText("0");
        cursorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cursorFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(playplayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(candleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linechartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(speedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cursorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cursorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playplayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(candleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linechartButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(speedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout seriesViewerPanelLayout = new javax.swing.GroupLayout(seriesViewerPanel);
        seriesViewerPanel.setLayout(seriesViewerPanelLayout);
        seriesViewerPanelLayout.setHorizontalGroup(
            seriesViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1219, Short.MAX_VALUE)
        );
        seriesViewerPanelLayout.setVerticalGroup(
            seriesViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(seriesViewerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(seriesViewerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftButtonActionPerformed
        ctrl.shiftCursor(-1);
    }//GEN-LAST:event_leftButtonActionPerformed

    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightButtonActionPerformed
        ctrl.shiftCursor(1);
    }//GEN-LAST:event_rightButtonActionPerformed

    private void speedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speedFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_speedFieldActionPerformed

    private void dateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFieldActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        this.simStop = true;
        this.playButton.setEnabled(true);
        this.playButton.setSelected(false);
        this.pauseButton.setEnabled(false);
        this.pauseButton.setSelected(true);
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        this.simStop = false;
        this.playButton.setEnabled(false);
        this.playButton.setSelected(false);
        this.pauseButton.setEnabled(true);
        this.pauseButton.setSelected(false);
        runEventPlay();
    }//GEN-LAST:event_playButtonActionPerformed
      
    public void runEventPlay() {
    	if (!simStop && !backtest.isDone()) {
            try {
                    int n = Integer.parseInt(this.speedField.getText());
                    this.backtest.step(strategy, n);
                    this.progressBar.setValue(this.backtest.getCursor()*100/this.backtest.getData().size());
                    if(backtest.isDone()){
                        this.playButton.setEnabled(false);
                        this.pauseButton.setEnabled(false);
                        this.playplayButton.setEnabled(false);
                        return;
                    }
                    this.ctrl.shiftCursor(n);
            } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e);
                return;
            }
            SwingUtilities.invokeLater( new Runnable() {
                   @Override
                   public void run() {
                           runEventPlay();
                   }
            });
	} 

    }
    private void candleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_candleButtonActionPerformed
        ctrl.setCandleVisu(this.candleButton.isSelected());
        seriesViewerPanel.repaint();
    }//GEN-LAST:event_candleButtonActionPerformed

    private void linechartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linechartButtonActionPerformed
    	ctrl.setLinechartVisu(this.linechartButton.isSelected());
        seriesViewerPanel.repaint();
    }//GEN-LAST:event_linechartButtonActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        
    }//GEN-LAST:event_speedSliderStateChanged

    private void navigatorSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_navigatorSliderStateChanged
        JSlider source = (JSlider)evt.getSource();
        if(!source.getValueIsAdjusting()){
            ctrl.setCursor(navigatorSlider.getValue()*(ctrl.getDataSize()-1)/100f);
        }
        
    }//GEN-LAST:event_navigatorSliderStateChanged

    private void cursorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cursorFieldActionPerformed
        ctrl.setCursor(Integer.parseInt(cursorField.getText()));
    }//GEN-LAST:event_cursorFieldActionPerformed

    private void playplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playplayButtonActionPerformed
        this.playButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.playplayButton.setEnabled(false);
        runEventPlayPlay();
    }//GEN-LAST:event_playplayButtonActionPerformed
    public void runEventPlayPlay() {
        try {
                this.backtest.run(strategy);
                this.progressBar.setValue(100);
                this.ctrl.shiftCursor(1);
        } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e);
        }
    }
    public void updateCursor(int c){
        
        cursorField.setText(String.valueOf(c)); 
        
        var ls = navigatorSlider.getChangeListeners();
        navigatorSlider.removeChangeListener(ls[0]);
        navigatorSlider.setValue((int)Math.floor(c*100f/(ctrl.getDataSize()-1)));
        navigatorSlider.addChangeListener(ls[0]);
        
        seriesViewerPanel.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton candleButton;
    private javax.swing.JTextField cursorField;
    private javax.swing.JTextField dateField;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton leftButton;
    private javax.swing.JToggleButton linechartButton;
    private javax.swing.JSlider navigatorSlider;
    private javax.swing.JToggleButton pauseButton;
    private javax.swing.JToggleButton playButton;
    private javax.swing.JButton playplayButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton rightButton;
    private market_system.backtest.view.series.SeriesViewerPanel seriesViewerPanel;
    private javax.swing.JTextField speedField;
    private javax.swing.JSlider speedSlider;
    // End of variables declaration//GEN-END:variables
}
