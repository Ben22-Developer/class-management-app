package app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load;

import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindowUtility;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class WebServiceWindowLoad {
    
    public static void loadWindow (SettingsWindow sw) {
        
        SwingUtilities.invokeLater(()  -> {
            JPanel settingsView = sw.getSettingsView();
            CardLayout cl = (CardLayout) settingsView.getLayout();
            String panelConstraint = "card5";
            cl.show(settingsView, panelConstraint);
            SettingsWindowUtility.storeCurrentPanel(panelConstraint);
            apiKeyCheckUp(sw);        
        });
    }
    
    public static void apiKeyCheckUp (SettingsWindow sw) {
        
        if (isTeacherNull()) {
            apiBtnsState( new JButton[] { sw.getClaimAPIBtn(), sw.getSaveApiKeyBtn(), sw.getDeleteApiKeyBtn(), sw.getTestBtn() }, new boolean[] {false, false, false, false});
            return;
        }
        
        JButton[] buttons = { sw.getClaimAPIBtn(), sw.getSaveApiKeyBtn(), sw.getDeleteApiKeyBtn() };
        
        String apiKey = CommonWebServiceFeatures.teacherApiKey();

        // teacher has no api key -> claim (enabled), save (enabled), delete (disabled)        
        // teacher has api key -> claim (disabled), save (disabled), delete (enabled)

        
        if (apiKey.isBlank())
            apiBtnsState(buttons, new boolean[] {true, true, false});
        else
            apiBtnsState(buttons, new boolean[] {false, false, true});
    }
    
    
    private static boolean isTeacherNull () {
        return LoggedInTeacher.getLoggedInTeacher() == null;
    }
    
    private static void apiBtnsState (JButton[] buttons, boolean[] states) {
        SwingUtilities.invokeLater(() -> 
        {
            for (int i = 0; i < buttons.length; i++)
                buttons[i].setEnabled(states[i]);        
        });
    }
}
