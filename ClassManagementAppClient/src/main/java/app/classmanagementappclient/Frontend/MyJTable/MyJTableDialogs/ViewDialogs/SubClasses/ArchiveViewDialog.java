package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewDialogs.SubClasses;

import app.classmanagementappclient.Frontend.DashboardView.Schedule.NewSchedule.ScheduleCenterPanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewDialogs.ViewDialogs;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ArchiveViewDialog extends ViewDialogs{

    @Override
    public void drawJDialog() {
        
        setWindowTitle("Schedule");
        setTitleLable("Archived Schedule");
        super.setLocationPoint(new Point(550, 100));
        super.drawJDialog();
    }

    @Override
    protected void addCenterPanel() {
        
        JDialog dialog  = getDialog();
        
        var scheduleCenterPanel = new ScheduleCenterPanel();
        
        JDateChooser date = scheduleCenterPanel.getTaskDate();
        JTextField title = scheduleCenterPanel.getTaskTitle();
        JTextArea description = scheduleCenterPanel.getTaskDescription();
        
        setComponentsText(date, title, description);
        
        FrontEndUtility.enableJComponents(new JComponent[] { scheduleCenterPanel.getTaskDate() }, false);
        FrontEndUtility.jTextEditable(new JTextComponent[] {scheduleCenterPanel.getTaskDescription(), scheduleCenterPanel.getTaskTitle()}, false);
        
        dialog.add(scheduleCenterPanel);
    }
    
    private void setComponentsText (JDateChooser date, JTextField title, JTextArea description) 
    {
        List<String> rowData = super.getSelectedRowData();
        
        title.setText(rowData.getFirst());
        description.setText(rowData.get(1));
        date.setDate(FrontEndUtility.getAppFormatDateToSQLFormatDate(rowData.getLast()));
    }
}
