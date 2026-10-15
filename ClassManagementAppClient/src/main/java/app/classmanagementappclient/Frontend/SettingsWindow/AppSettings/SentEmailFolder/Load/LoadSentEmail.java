/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.SentEmailFolder.Load;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class LoadSentEmail {
    
    public static void load (SettingsWindow sw) 
    {
        boolean autoSaveSentEmails = Boolean.parseBoolean( String.valueOf( FrontEndUtility.getSentEmailsPropertiesValue("autoSaveSentEmails") ));
        setSaveSentEmailButton(sw.getAutoSaveSentEmailToggleButton(), autoSaveSentEmails);
    }
    
    public static void setSaveSentEmailButton (JToggleButton autoSaveSentEmaiToggleButton, boolean autoSaveSentEmails) {
        autoSaveSentEmaiToggleButton.setText(autoSaveSentEmails ? "ON" : "OFF");
        autoSaveSentEmaiToggleButton.setSelected(autoSaveSentEmails);
    }
}
