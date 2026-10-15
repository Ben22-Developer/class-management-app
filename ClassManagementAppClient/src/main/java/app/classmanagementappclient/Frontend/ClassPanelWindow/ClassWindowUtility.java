package app.classmanagementappclient.Frontend.ClassPanelWindow;

import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.ClassPanelWindowLoadController;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ClassWindowUtility 
{
    
    public static void controllerButtonActionPerformed (ClassPanel classPanel, int panelNbr) {
        new PanelDisplayHandler(classPanel).setWindowToDisplay(panelNbr);
    }
    
    public static void settingsToggleBtnActionPerformed (ClassPanel classPanel) {
        new PanelDisplayHandler(classPanel).toggleClassWindowSettingsPanel();
    }

    public static void showSettingsPanel (ClassPanel classPanel, JPanel settingsPanel) {
        new PanelDisplayHandler(classPanel).showSettingsPanel(settingsPanel);
    }
    
    public static void hideSettingsPanel (ClassPanel classPanel, JPanel settingsPanel) {
        new PanelDisplayHandler(classPanel).hideSettingsPanel(settingsPanel);
    }
    
    public static void hideTitlePanel (JPanel studentsPanel, JPanel titlePanel) {
        new PanelDisplayHandler().hideTitlePanel(studentsPanel, titlePanel);
    }
    
    public static void showTitlePanel (JPanel activePanel, JPanel titlePanel) {
        new PanelDisplayHandler().showTitlePanel(activePanel, titlePanel);
    }    
    
    public static void showClass (MainWindow mainWindow) {

        SwingUtilities.invokeLater(() -> {
            new ClassPanelWindowLoadController(mainWindow).load();
        });
    }
    
    public static void closeTabBtnActionPerformed (ClassPanel panel) {
        
        JTabbedPane pane = (JTabbedPane) panel.getParent();
        pane.remove(panel);
        panel = null;
    }
}
