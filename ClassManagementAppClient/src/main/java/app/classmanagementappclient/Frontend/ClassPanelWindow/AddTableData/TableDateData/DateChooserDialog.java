package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

/**
 *
 * @author BEN
 */

public abstract class DateChooserDialog extends ADialog {
    
    private boolean submitted;
    private final JDateChooser dateChooser = new JDateChooser();

    public DateChooserDialog () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, 12, 31);
        dateChooser.setMinSelectableDate(calendar.getTime());
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 16));
    }
    
    public JDateChooser getDateChooser() {
        return dateChooser;
    }
    
    protected java.sql.Date getDate () {
        return FrontEndUtility.getDate(dateChooser);
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
    

    @Override
    protected void setJDialogProp() {
        
        var thisObj = this;
        
        super.getDialog().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!isSubmitted()) {
                    thisObj.setSubmitted(true);
                    notifySleepers();
                }
                cleanUp();
                System.out.println("cleaned up");
            }
        });
        super.setJDialogProp(); 
    }
    
    @Override
    public boolean handler() {
        
        java.sql.Date date = getDate();
        if (date == null) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Invalid date selected!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean res = submitHandler();
        return res;
    }
        
    
    public void cleanUp() {
        dateChooser.cleanup();
    }
    
    public abstract void notifySleepers();
    public abstract boolean submitHandler();
}
