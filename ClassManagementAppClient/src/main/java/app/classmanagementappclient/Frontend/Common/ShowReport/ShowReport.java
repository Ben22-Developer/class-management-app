package app.classmanagementappclient.Frontend.Common.ShowReport;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class ShowReport {

    public void showReport(String errMsg, String sucessMsg, boolean...noAddDefault) {
        
        if (errMsg.isBlank()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(sucessMsg, "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String txt = (noAddDefault.length == 0) ? "Here are some errors found:\n"+errMsg : errMsg;
        renderView(txt);
    }
    
    public void showReport (String txt) {
        renderView(txt);
    }
    
    private void renderView (String txt) 
    {
        MyJDialog dialog = new MyJDialog();
        JTextArea textArea = new JTextArea();
        textArea.setColumns(30);
        textArea.setRows(5);
        
        textArea.setText(txt);
        
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial",Font.PLAIN,18));
        dialog.add(new JScrollPane(textArea));
        dialog.setSize(new Dimension(600,500));
        dialog.setLocation(200, 90);
        dialog.setTitle("Report");
        dialog.setResizable(true);
        dialog.setVisible(true);          
    }
    
}
