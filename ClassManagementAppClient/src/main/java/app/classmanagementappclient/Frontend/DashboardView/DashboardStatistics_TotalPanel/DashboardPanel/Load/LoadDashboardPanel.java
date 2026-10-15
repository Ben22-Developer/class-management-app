/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Load;

import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.DashboardPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.SavedClassesPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.SavedStudentsPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.StatisticsPanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class LoadDashboardPanel {
    
    public static JPanel loadDashboard () {
        
        var dashboardPanel = new DashboardPanel();
        
        var sw = new SwingWorker<Object, Integer> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                int index = getPanelIndexToDisplay();
                publish(index);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                displayPanel(dashboardPanel, chunks.getFirst());
            }
        };
        
        sw.execute();
        
        try {
            sw.get();
        } catch (Exception ex) {
            System.getLogger(LoadDashboardPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return dashboardPanel;
    }
    
    private static void displayPanel (DashboardPanel dashboardPanel, int index) {

        switch (index) {
            case 0 -> dashboardPanel.add( dashboardPanel.getSavedClassesPanel(), BorderLayout.CENTER);
            case 1 -> dashboardPanel.add( dashboardPanel.getSavedStudentsPanel(), BorderLayout.CENTER);
            case 2 -> dashboardPanel.add( dashboardPanel.getStatisticsPanel(), BorderLayout.CENTER);
            default -> throw new AssertionError("Unknown panel index at LoadDashboardPanel.displayPanel");
        }
    }
    
    private static int getPanelIndexToDisplay () {
        return FrontEndUtility.getWindowPanelToDisplay("DashboardPanel");
    }
    
    public static void storePanelIndex (JPanel panel) {
        
        if (panel instanceof SavedClassesPanel)
            FrontEndUtility.storeWindowPanelToDisplay("DashboardPanel", 0);
        
        else if (panel instanceof SavedStudentsPanel)
            FrontEndUtility.storeWindowPanelToDisplay("DashboardPanel", 1);
        
        else if (panel instanceof StatisticsPanel)
            FrontEndUtility.storeWindowPanelToDisplay("DashboardPanel", 2);        
        else
            throw new AssertionError("Unknown panel type at LoadDashboardPanel.storePanelIndex");
    }
    
    
}
