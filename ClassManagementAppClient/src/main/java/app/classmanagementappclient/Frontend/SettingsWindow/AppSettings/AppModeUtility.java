package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class AppModeUtility {
    
    public static void loadAppModeComboBox (SettingsWindow sw) {
        boolean isLightMode = Boolean.valueOf( String.valueOf(FrontEndUtility.getAppModeProperties().get("LightMode")) );
        sw.getAppModeComboBox().setSelectedIndex(isLightMode ? 0 : 1);
    }
    
    public static void loadUISettings (JPanel settingsView) {
        CardLayout cl = (CardLayout) settingsView.getLayout();
        cl.show(settingsView, "card2");        
    }
}
