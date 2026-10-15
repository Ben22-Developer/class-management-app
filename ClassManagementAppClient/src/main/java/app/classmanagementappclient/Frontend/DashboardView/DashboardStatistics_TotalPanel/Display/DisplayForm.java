package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.Display;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class DisplayForm {
    
    public static void display(JPanel wrapperPanel, JPanel toShowPanel) {
        
        wrapperPanel.removeAll();
        wrapperPanel.add(toShowPanel);
        wrapperPanel.repaint();
        wrapperPanel.revalidate();
    }
}
