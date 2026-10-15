/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ContactUs.Load;

import app.classmanagementappclient.Medium.TeacherMessage.TeacherMessageMedium;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.ContactUsPanel;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.ContactUsUtility;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class LoadContactUsPanel {
    
    public static ContactUsPanel loadContactUsPanel (JLabel connectionLabel) {
        
        List<List<String>> teacherMessageRawList = getAllTeacherMessages();
        
        List<TeacherMessage> teacherMessages = teacherMessagesConversion(teacherMessageRawList);
        
        JPanel[] sentMessagePanels = getSentMessagePanels(teacherMessages);
        
        ContactUsPanel contactUsPanel = new ContactUsPanel(connectionLabel);
        
        ContactUsUtility.addPanelsToSentMsgPanel(contactUsPanel.getSentMsgPanel(), sentMessagePanels);
        
        return contactUsPanel;
    }
    
    private static  List<List<String>> getAllTeacherMessages () {
        return new TeacherMessageMedium().readTeacherMessages();
    }
    
    private static  List<TeacherMessage> teacherMessagesConversion (List<List<String>> teacherMessageRawList) 
    {
        List<TeacherMessage> teacherMessagesList = new ArrayList<>();
        
        teacherMessageRawList.forEach(e -> 
        {
            Timestamp timestamp = Timestamp.valueOf(e.get(0));
            String message = e.get(1);
            
            teacherMessagesList.add (new TeacherMessage(message, timestamp));
        });
        
        return teacherMessagesList;
    }
    
    private static JPanel[] getSentMessagePanels (List<TeacherMessage> teacherMessages) {
        
        JPanel[] sentMessagePanels = new JPanel[teacherMessages.size()];
        int i = 0;
        
        for (TeacherMessage teacherMessage : teacherMessages) {
            
            sentMessagePanels[i] = ContactUsUtility.getSentMessagePanel(teacherMessage);
            i ++;
        }
        return sentMessagePanels;
    }
}
