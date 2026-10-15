package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1Int;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class InsertOneByOneRecord extends ADialog{
    
    private final DataEntry1Int proxy;
    private int rowNbr;
    private final int num;

    public InsertOneByOneRecord(DataEntry1Int proxy, int rowNbr, int num) {
        this.proxy = proxy;
        this.rowNbr = rowNbr;
        this.num = num;
    }
    
    @Override
    protected void setJDialogProp() {
        super.getDialog().addWindowListener(new WindowAdapter () {
            @Override
            public void windowClosed(WindowEvent e) {
                proxy.setNewRecords(null);
                proxy.setNextRecord(false);
                notifySleepers();
            }  
        });
        super.setJDialogProp();
    }
    
    protected void UIUpdate () {
        List<JTextField> textFields = super.getTextFields();
        textFields.forEach(e -> e.setText(""));
    }    
    
    public void notifySleepers () {
        proxy.getLock().lock();
        try {
            proxy.getCondition().signalAll();
        } finally {
            proxy.getLock().unlock();
        }
    }

    public DataEntry1Int getProxy() {
        return proxy;
    }

    public int getRowNbr() {
        return rowNbr;
    }

    public int getNum() {
        return num;
    }

    public void setRowNbr(int rowNbr) {
        this.rowNbr = rowNbr;
    }
}
