/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ContactUs;

import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.Panel.SentMessagePanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ContactUsUtility {
   
    public static void addPanelsToSentMsgPanel (JPanel contactUsSentMsgPanel, JComponent[] panels) {
        FrontEndUtility.addJComponentToPanel(contactUsSentMsgPanel, panels);
        FrontEndUtility.refresh(contactUsSentMsgPanel);
    }
    
    public static SentMessagePanel getSentMessagePanel (TeacherMessage teacherMessage) {
        
        String sentAt = FrontEndUtility.getRecordedAtTimeInAppFormat(teacherMessage.getTimestamp().toLocalDateTime());
        String msg = teacherMessage.getMessage();
        
        var sentMessagePanel = new SentMessagePanel();
        
        sentMessagePanel.getSentAtLabel().setText(sentAt);
        sentMessagePanel.getjTextArea().setText(msg);
        
        return sentMessagePanel;
    }
}
