/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.AppSettings.StudentImageFolder;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class StudentImageUtility {
    
    public static void initStudentImage (SettingsWindow sw) {
        
        var buttonGroup = new ButtonGroup();
        
        var displayWarningMessageButton = sw.getDisplayWarningMessageButton();
        var dontDisplayWarningMessageButton = sw.getDontDisplayWarningMessageButton();
        
        FrontEndUtility.addToRadioBtnsToButtonGrp(buttonGroup, new JRadioButton[] {displayWarningMessageButton, dontDisplayWarningMessageButton});
        setSelectedRadioButton(displayWarningMessageButton, dontDisplayWarningMessageButton);
    }
    
    private static void setSelectedRadioButton (JRadioButton displayWarningMessageButton, JRadioButton dontDisplayWarningMessageButton) {
        
        if (FrontEndUtility.canDisplayWarningMsg("ExcelStudentFileFormatWarning"))
            displayWarningMessageButton.setSelected(true);
        else
            dontDisplayWarningMessageButton.setSelected(true);        
    }
}
