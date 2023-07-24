/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package market_system.backtest.view.results;

import java.util.List;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import market_system.backtest.broker.Deal;
import market_system.backtest.broker.Trade;

/**
 *
 * @author Fabrizio Ortega
 */
public class TradesTableModel extends DefaultTableModel{
    int cursor;
    public TradesTableModel(){
        super(new Vector(), new Vector<String>(List.of("Date","Id","Type","Price","Volume", "Close Date", "Close Price","Profit","Comment")));
        this.cursor=0;
    }

    boolean update(List<Deal> deals, List<Trade> otrades) {
        //this.dataVector.clear();
        /*for(Trade t:otrades){
            this.dataVector.add(new Vector(List.of(t.getOpenDate(),t.getId(), t.getType(),t.getOpenPrice(), t.getVolume(), "")));
        }*/
        boolean b = false;
        for(int i=cursor;i<deals.size();i++){
            Deal d=deals.get(i);
            this.dataVector.add(new Vector(List.of(d.getOpenTime(),d.getId(), d.getTrade().getType(),d.getOpenPrice(), d.getVolume(), d.getCloseTime(), d.getClosePrice(), d.getProfit(), "")));
            cursor++;
            b=true;
            //this.fireTableStructureChanged();
            this.fireTableDataChanged();
        }
        return b;
    }
   
}
