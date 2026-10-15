package app.classmanagementappclient.Frontend.ExcelManipulators.Warning;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author BEN
 */
public class ExcelWarning extends SwingWorker<Object, Object> {
    
    private final ExcelWarner warner;
    
    private final JComponent[] northComponents;
    private final JComponent[] centerComponents;
    private final JComponent[] southComponents;
    
    private final JPanel northPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    
    private final MyJDialog dialog = new MyJDialog();
    
    public ExcelWarning (ExcelWarner warner,JComponent[] northComponents, JComponent[] centerComponents, JComponent[] southComponents) {
        this.northComponents = northComponents;
        this.centerComponents = centerComponents;
        this.southComponents = southComponents;
        this.warner = warner;
    }
        
    @Override
    protected Object doInBackground() throws Exception 
    {
        setFont();
        setPanel(northComponents, northPanel);
        setPanel(centerComponents, centerPanel);
        setPanel(southComponents, southPanel);

        dialog.setTitle("Warning Message");
        dialog.add (northPanel, BorderLayout.NORTH);
        dialog.add (centerPanel, BorderLayout.CENTER);
        dialog.add (southPanel, BorderLayout.SOUTH);
        dialog.setLocation(new Point(400,100));
        dialog.setSize (new Dimension(650,420));
        return null;
    }
    @Override
    public void done () {
        dialog.setVisible(true);
    }

    private void setFont () {
        for (JComponent[] components : new JComponent[][] { northComponents,centerComponents, southComponents })
            FrontEndUtility.setJComponentsCustomFont(components, new Font("Arial", Font.PLAIN, 18));
    }

    private void setPanel (JComponent[] components, JPanel panel) {
        for (JComponent component : components)
            panel.add(component);
    }

    public void addConfirmBtnEvent (JButton okButton, JCheckBox noWarningCheckBox, String propertyKey) {
        
        okButton.addActionListener((l) -> {
            
            if (noWarningCheckBox.isSelected())
                FrontEndUtility.updateDisplayWarningMsgProperty(propertyKey, false);
            dialog.dispose();
        });        
    }
    
    public void addDisposeListener () {
        
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                warner.afterWarnRun();
            }
        });
    }
    
    public void addImageExampleListener (JButton viewImageBtn) {
        
        viewImageBtn.addActionListener(l -> {
            FrontEndUtility.displayExcelFormatStudentInsertImageExample();
        });

    }
    
}