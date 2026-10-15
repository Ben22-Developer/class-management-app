/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel;

import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Load.LoadDashboardPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.Display.DisplayForm;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class DashboardPanelUtility {
    
    public static void showTotalProjects (JPanel dashboardPanel, JPanel toShowPanel, JPanel titlePanel) {
        DisplayForm.display(dashboardPanel, toShowPanel);
        dashboardPanel.add(titlePanel, BorderLayout.NORTH);
        LoadDashboardPanel.storePanelIndex(toShowPanel);
    }
    
    public static JPanel loadDashboardPanel () {
        return LoadDashboardPanel.loadDashboard();
    }
}
