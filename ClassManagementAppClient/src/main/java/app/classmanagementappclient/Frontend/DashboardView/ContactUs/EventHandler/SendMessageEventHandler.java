/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ContactUs.EventHandler;

import app.classmanagementappclient.Medium.TeacherMessage.TeacherMessageMedium;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.ContactUsUtility;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.Panel.SentMessagePanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import com.google.gson.Gson;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author BEN
 */
public class SendMessageEventHandler {
    
    private final JLabel connectionLabel;
    
    private final JTextArea textArea;
    
    private final JPanel sentMsgPanel;

    public SendMessageEventHandler(JLabel connectionLabel, JTextArea textArea, JPanel sentMsgPanel) {
        this.connectionLabel = connectionLabel;
        this.textArea = textArea;
        this.sentMsgPanel = sentMsgPanel;
    }
    
    public void send () {
        
        var teacherMessage = new TeacherMessage(textArea.getText(), Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Africa/Kigali")))); // u can implement dynamic zpne id config too
        serverSend(teacherMessage);
        dbInsertTeacherMessage(teacherMessage);
        addTeacherMessageToUI(teacherMessage);
    }
    
    private void serverSend (TeacherMessage teacherMessage) {
        
        Gson gson = new Gson();
        String body = gson.toJson(teacherMessage);
        
        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL()+CommonWebServiceFeatures.sendEmailToAdmin))
                .headers("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        
        CommonWebServiceFeatures.getHttpResponse(httpRequest, connectionLabel);
    }
    
    private void dbInsertTeacherMessage (TeacherMessage teacherMessage) {
        new TeacherMessageMedium().insertTeacherMessage(teacherMessage);
    }
    
    private void addTeacherMessageToUI (TeacherMessage teacherMessage) {
        
        SentMessagePanel sentMessagePanel = ContactUsUtility.getSentMessagePanel(teacherMessage);
        ContactUsUtility.addPanelsToSentMsgPanel(sentMsgPanel, new JComponent[] {sentMessagePanel});
        textArea.setText("");
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Message sent successfully", "Information Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
