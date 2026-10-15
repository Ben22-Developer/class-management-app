/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.SentEmailFolder.EventListener;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.SentEmailFolder.Load.LoadSentEmail;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class SentEmailsEventHandler {
    
    public static void handler (JToggleButton button) {
        
        boolean autoSave = button.isSelected();
         
        FrontEndUtility.setSentEmailsPropertiesValue("autoSaveSentEmails", String.valueOf(autoSave));
        FrontEndUtility.storeSentEmailsProperties();
        LoadSentEmail.setSaveSentEmailButton(button,autoSave);
    }
}
