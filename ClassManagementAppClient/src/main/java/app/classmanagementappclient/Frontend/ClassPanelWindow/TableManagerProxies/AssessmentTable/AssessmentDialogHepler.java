package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

/**
 *
 * @author BEN
 */
public class AssessmentDialogHepler {
    
    public JPanel getPanel (ADialog dialog, String labelStr, String textFieldVal) {
        
        JPanel panel = new JPanel (new FlowLayout(FlowLayout.LEADING, 10, 10));
        JLabel label = new JLabel(labelStr);
        JTextField textField = new JTextField(textFieldVal, 15);
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { label,textField });
        setLabelProp(label);
        dialog.getTextFields().add(textField);
        return panel;
    }
    
    public JPanel getPanel (String labelStr, JDateChooser dateChooser, Date...date) {
        
        JPanel panel = new JPanel (new FlowLayout(FlowLayout.LEADING, 10, 10));
        JLabel label = new JLabel(labelStr);
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { label,dateChooser });
        setLabelProp(label);
        dateChooser.setPreferredSize(new Dimension(210,40));
        if (date.length != 0)
            dateChooser.setDate(date[0]);
        return panel;
    }   
    
    private void setLabelProp (JComponent label) {
        FrontEndUtility.setJComponentDimension(label, new Dimension(120, 40));
        FrontEndUtility.setJComponentCustomFont(label, new Font("Arial", Font.BOLD, 16));
    }
}
