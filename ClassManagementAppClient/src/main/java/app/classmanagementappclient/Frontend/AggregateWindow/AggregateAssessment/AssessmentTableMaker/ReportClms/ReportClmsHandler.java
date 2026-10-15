package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.ReportClms;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ReportStructureTable;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class ReportClmsHandler {
    
    private ReportStructureTable table;
    private final AggregateWindow aggregateWindow;
    
    public ReportClmsHandler(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    public void run () {
    
        var sw = new SwingWorker<Object, Object> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                JSplitPane pane = aggregateWindow.getSplitPane();
                
                if (pane.getTopComponent() == null)
                    setSplitPaneTopComponent();
                
                JPanel topPanel = (JPanel) pane.getTopComponent();
                addPanel(topPanel);
                
                FrontEndUtility.componentValidate(pane);
                return null;
            }
        };
        
        sw.execute();
        try {
            sw.get();
        } catch (Exception e) {}
    }
    
    private void setSplitPaneTopComponent () {
        JPanel topPanel = new JPanel(new BorderLayout());
        aggregateWindow.setJSplitPaneTopComponent(topPanel);
        FrontEndUtility.componentValidate(aggregateWindow.getSplitPane());
    }
    
    private void addPanel (JPanel topPanel) {
        JPanel panel = getJPanel();
        topPanel.add(panel);
    }
    
    private JPanel getJPanel () {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getDetailsButton(), BorderLayout.NORTH);
        panel.setOpaque(false);
        return panel;
    }
    
    private JPanel getDetailsButton () {
        
        JButton button = new JButton("Set Columns Details");
        FrontEndUtility.setJComponentDefaultFont(button);
        
        button.addActionListener(l -> 
        {
            SwingUtilities.invokeLater(() ->
            {
                new ClmsHeaderSetterDialog(this).run();
            });
        });
        
        return addBtnToPanel(button);
    }
    
    private JPanel addBtnToPanel (JButton button) {
        JPanel panel = new JPanel();
        panel.add(button);
        return panel;
    }

    public void setTable(ReportStructureTable table) {
        this.table = table;
    }

    public ReportStructureTable getTable() {
        return table;
    }

    public AggregateWindow getAggregateWindow() {
        return aggregateWindow;
    }
}
