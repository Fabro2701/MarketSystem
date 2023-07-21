/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package market_system.backtest.view.series;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import market_system.backtest.data.MarketData;

/**
 *
 * @author Fabrizio Ortega
 */
public class SeriesPlayerController {
    int cursor;
    SeriesPlayerPanel playerPanel;
    MarketData md;
    public SeriesPlayerController(SeriesPlayerPanel playerPanel){
        cursor=0;
        this.playerPanel = playerPanel;
        md = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSDr.csv");
    }
    public void shiftCursor(int change){
        setCursor(cursor + change);
    }
    public void setCursor(double cursor){
        setCursor((int)Math.floor(cursor));
    }
    public void setCursor(int cursor){
        if(!(cursor>=0&&cursor<getDataSize())){
            JOptionPane.showMessageDialog(new JFrame(), "Invalid cursor value: "+cursor+ "  values must be in ["+0+","+getDataSize()+">", "Invalid cursor value", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.cursor = cursor;
        playerPanel.updateCursor(cursor);
    }
    public int getCursor(){
        return cursor;
    }
    public MarketData getData(){
        return md;
    }
    public int getDataSize(){
        return md.size();
    }
}
