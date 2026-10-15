/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.AudioFileFolder;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class AudioFileUtility {

    public static void initAudioSettings (SettingsWindow sw) {
        
        var buttonGroup = new ButtonGroup();
        
        var playAudioButton= sw.getPlayAudioButton();
        var dontPlayAudioButton = sw.getDontPlayAudioButton();
        
        FrontEndUtility.addToRadioBtnsToButtonGrp(buttonGroup, new JRadioButton[] {playAudioButton, dontPlayAudioButton});
        setSelectedRadioButton(playAudioButton, dontPlayAudioButton);
    }
    
    private static void setSelectedRadioButton (JRadioButton playAudioButton, JRadioButton dontPlayAudioButton) {
        
        if (FrontEndUtility.canPlayAudioFile("AttendanceAudioPlay"))
            playAudioButton.setSelected(true);
        else
            dontPlayAudioButton.setSelected(true);        
    }    
}
