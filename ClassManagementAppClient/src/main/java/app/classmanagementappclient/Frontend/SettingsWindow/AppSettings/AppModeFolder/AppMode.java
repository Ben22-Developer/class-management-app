package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.AppModeFolder;

import app.classmanagementappclient.Frontend.FrontEndUtility;

import java.util.regex.Pattern;

/**
 *
 * @author BEN
 */
public class AppMode {
    
    public void newMode (String newMode) {
        var pattern = Pattern.compile("[light]", Pattern.CASE_INSENSITIVE);
        updateProperties(pattern.matcher(newMode).find());
    }
    
    private void updateProperties (Boolean isLightMode) {
        
        FrontEndUtility.updateAppModeProperties(isLightMode);
        FrontEndUtility.setLookAndFeel(isLightMode);
        FrontEndUtility.updateUI();
    }
}
