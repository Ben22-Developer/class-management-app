package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel;

import app.classmanagementappclient.Frontend.SavedProjectsWindow.SavedProjectsWindowUtility;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedClassesUtility.SavedClassesUtility;

import java.awt.*;

/**
 *
 * @author Raven
 */
public class SavedClassesPanel extends javax.swing.JPanel {

    private int totalData;
    
    public SavedClassesPanel() {
        initComponents();
//        lb.putClientProperty(FlatClientProperties.STYLE, ""
//                + "font:$h1.font");
//        lb.setText(text);
            dataPopulate();
    }
    
    private void dataPopulate () {
        SavedProjectsWindowUtility utility = new SavedClassesUtility(null);
        utility.projectActivitiesMenuItenActionPerformed();
        var tableDisplay = utility.getTableDisplay();
//        add(tableDisplay.getNorthPanel(), BorderLayout.NORTH);
        add(tableDisplay.getScrollPanePanel(), BorderLayout.CENTER);
        add(tableDisplay.getSouthPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
        setTotalData(utility.getTotalData());
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
}
