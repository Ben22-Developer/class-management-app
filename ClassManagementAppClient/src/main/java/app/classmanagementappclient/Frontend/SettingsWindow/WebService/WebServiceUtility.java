package app.classmanagementappclient.Frontend.SettingsWindow.WebService;

import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.ClaimAPI.Inherits.ClaimFreeTierAPI;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.DeleteAPIKey;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.SaveAPIKey;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.TestApiKey;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load.WebServiceWindowLoad;

/**
 *
 * @author BEN
 */
public class WebServiceUtility {
    
    public static void load (SettingsWindow sw) {
        WebServiceWindowLoad.loadWindow(sw);
    }
    
    public static void claimAPI (SettingsWindow settingsWindow, MainWindow mainWindow) {
        new ClaimFreeTierAPI(mainWindow.getConnectionLabel()).getAPIKey();
    }
    
    public static void saveAPI (SettingsWindow settingsWindow, MainWindow mainWindow) {
        new SaveAPIKey(settingsWindow, mainWindow.getConnectionLabel()).saveAPIKey();
    } 
    
    public static void deleteAPIKey (SettingsWindow settingsWindow, MainWindow mainWindow) {
        new DeleteAPIKey(mainWindow.getConnectionLabel(), settingsWindow).delete();
    }
    
    public static void testAPIKey (SettingsWindow settingsWindow, MainWindow mainWindow) {
        new TestApiKey(mainWindow.getConnectionLabel()).test();
    }    
}
