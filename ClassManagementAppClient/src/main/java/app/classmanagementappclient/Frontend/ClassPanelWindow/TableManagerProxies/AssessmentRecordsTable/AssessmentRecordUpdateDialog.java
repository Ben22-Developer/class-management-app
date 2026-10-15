package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentRecordUpdateDialog extends TableEditDialog {

    @Override
    public void drawJDialog() {
        super.setWindowTitle("Student Assessment Record Edit");
        super.drawJDialog();
    }
    
    @Override
    protected void addCenterPanel() {
        
        List<JPanel> smallPanels = new ArrayList<>();
        
        List<String> headers = super.getHeaders();
        List<String> values = super.getSelectedRowData();
        List<JTextField> textFields = super.getTextFields();
        JDialog dialog = super.getDialog();
        
        var clmIndexToCheck = getClmIndexToCheck();
        
        for (int i = 0; i < clmIndexToCheck.size(); i++) 
        {
            int index = clmIndexToCheck.get(i);
            String labelTxt = headers.get(index);
            String value = values.get(i);
            JTextField textField = new JTextField (value);
            textFields.add(textField);
            
            if (i == 0)
                textField.setEditable(false);
            
            JLabel label = new JLabel(labelTxt);
            setJComponent(label,  new Dimension(120,40));
            FrontEndUtility.setJTextField(textField);
            Dimension dimension = new Dimension(500,50);
            JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
            setJPanel(jPanel, new JComponent[] { label, textField}, dimension);
            smallPanels.add(jPanel);
        }
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(smallPanels.size(), 1, 0, 5));
        smallPanels.forEach((JPanel panel) -> {
            bigPanel.add(panel);
        });
        
        dialog.add(bigPanel, BorderLayout.CENTER);
    }
}
