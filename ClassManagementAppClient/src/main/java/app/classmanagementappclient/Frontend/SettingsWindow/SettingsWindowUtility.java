package app.classmanagementappclient.Frontend.SettingsWindow;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.AppModeUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.AudioFileFolder.AudioFileUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.SentEmailFolder.Load.LoadSentEmail;
import app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.StudentImageFolder.StudentImageUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SettingsWindowUtility 
{
    public static void settingsWindowInit (MainWindow mainWindow) 
    {
        
        new SwingWorker<Object, JDialog> () 
        {
            private SettingsWindow sw;
            private MyJDialog dialog = new MyJDialog();
            
            @Override
            public Object doInBackground () 
            {
                sw = new SettingsWindow();
                sw.setMainWindow(mainWindow);
                setPanelDisplay();
                AppModeUtility.loadAppModeComboBox(sw);
                StudentImageUtility.initStudentImage(sw);
                AudioFileUtility.initAudioSettings(sw);
                LoadSentEmail.load(sw);
                setDialog();
                publish(dialog);
                return null;
            }
            
            private void setPanelDisplay () {
                String cardConstraint = getDisplayingPanel(sw);
                loadPanel(sw, cardConstraint);
            }
            
            private void setDialog () {
                JPanel viewPanel = sw.getSettingsView();
                JPanel btnPanel = sw.getSettingsBtns();
                dialog.add(btnPanel, BorderLayout.WEST);
                dialog.add(viewPanel, BorderLayout.CENTER);    
                dialog.setTitle (sw.getTitle());
                dialog.setLocation(sw.getLocation());
                dialog.pack();
            }

            @Override
            protected void process(List<JDialog> chunks) {
                chunks.getFirst().setVisible(true);
            }
        }.execute();
    }
    
    private static String getDisplayingPanel (SettingsWindow sw) 
    {
        JPanel settingsView = sw.getSettingsView();
        CardLayout cardLayout = (CardLayout) settingsView.getLayout();
        String cardConstraint = getPanelNameConstraint();
        cardLayout.show(settingsView, cardConstraint);
        return cardConstraint;
    }
    
    private static String getPanelNameConstraint ()
    {
        int panelIndex = FrontEndUtility.getWindowPanelToDisplay("SettingsWindowCurrentPanel");
        
        switch (panelIndex) 
        {
            case 0 -> {
                return "card2";
            }    
            case 1 -> {
                return "card4";
            }
            case 2 -> {
                return "card3";
            }
            case 3 -> {
                return "card5";
            }            
        }
//        System.err.println("At SettingsWindowUtility.getPanelNameConstraint\nNo panel constraint set");
        return "";
    }
    
    private static void loadPanel (SettingsWindow sw, String panelConstraint) 
    {
        // these below panels loads because they're dealing with database
        switch (panelConstraint) {
            case "card3" -> {
                sw.getLogInOutSetting().setAccountLogUIComponents(sw);
            }
            case "card4" -> {
                sw.getUserAccountSetting().loadPanel(sw);
            }            
        }
    }
    
    public static void storeCurrentPanel (String panelConstraint) 
    {
        switch (panelConstraint) {
            case "card2" -> FrontEndUtility.storeWindowPanelToDisplay("SettingsWindowCurrentPanel", 0); 
            case "card4" -> FrontEndUtility.storeWindowPanelToDisplay("SettingsWindowCurrentPanel", 1);
            case "card3" -> FrontEndUtility.storeWindowPanelToDisplay("SettingsWindowCurrentPanel", 2);
            case "card5" -> FrontEndUtility.storeWindowPanelToDisplay("SettingsWindowCurrentPanel", 3);
        }
    }
    
    
}
