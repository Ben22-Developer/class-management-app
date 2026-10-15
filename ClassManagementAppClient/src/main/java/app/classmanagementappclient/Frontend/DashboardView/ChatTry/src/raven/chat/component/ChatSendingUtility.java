package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.component;

import app.classmanagementappclient.Backend.Objects.Enum.ChatType;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Prompt;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector.BackendToMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.model.ModelMessage;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import com.google.gson.Gson;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author BEN
 */
public class ChatSendingUtility {

    private final ChatArea chatArea;
    private final JButton cmdSend;
    private final Prompt prompt;
    
    public ChatSendingUtility(ChatArea chatArea, JButton cmdSend) {
        this.chatArea = chatArea;
        this.cmdSend = cmdSend;
        this.prompt = new Prompt(chatArea.getTextMessage().getText(), null);
    }
    
    public void chat () {
        
        new SwingWorker<Object, Object> () {
            
            private Timestamp timestamp;
            private final ChatBox.BoxType userBoxType = ChatBox.BoxType.RIGHT;
            private final ChatBox.BoxType botBoxType = ChatBox.BoxType.LEFT;
            private final Icon botIcon = chatArea.getBotIcon();
            private final Icon userIcon = chatArea.getUserIcon();            
            private final String content = chatArea.getText();
            private String botResponse;
            
            @Override
            protected Object doInBackground() throws Exception {
                
                cmdSend.setEnabled(false);
                chatArea.clearTextAndGrabFocus();
                
                checkNonBlankUserMessage();
                userMsg();
                setBotResponse();
                botMsg();
                
                return null;
            }
            
            private void checkNonBlankUserMessage () {
                
                if (content.isBlank()) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Can't send an empty message\nType something please", "Error Message", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Custom exception for terminating user sent message because it's blank.");
                }
            }
            
            private void setBotResponse () {
                botResponse = getModelResponse();
            }
            
            private void userMsg () {
                timestamp = Timestamp.valueOf(LocalDateTime.now());
                addDbRecord(content, userBoxType, timestamp);
                addChatMsg(userIcon, content, ChatBox.BoxType.RIGHT, timestamp);                
            }
            
            private void botMsg () {
                timestamp = Timestamp.valueOf(LocalDateTime.now());
                addDbRecord(botResponse, botBoxType, timestamp);
                addChatMsg(botIcon, botResponse, ChatBox.BoxType.LEFT, timestamp);                
            }            

            @Override
            protected void done() {
                cmdSend.setEnabled(true);
            }
            
            
        }.execute();        
    }
    
    
    private String getModelResponse () {
        
        String json = new Gson().toJson(prompt);
        
        var request = HttpRequest
                .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.aiChat))
                .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        
        HttpResponse<?> response = CommonWebServiceFeatures.getHttpResponse(request, chatArea.getConnectionLabel());
        
        if (response.statusCode() > 299) {
            FrontEndUtility.showMsgJOptionPane(response.body().toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        }
        
        return response.body().toString();
    }
    
    private void addDbRecord (String message, ChatBox.BoxType boxType, Timestamp timestamp) {
        
        String chatName = chatArea.getLabelTitle().getText();
        int chatID = BackendToMenuUtility.getChadIDByChatName(chatName);
        ChatType chatType = (boxType == boxType.LEFT) ? ChatType.BOT : ChatType.USER;
        
        if (!BackendToMenuUtility.insertNewChat(chatID, message, chatType, timestamp))
            throw new RuntimeException("Exception when adding a chat record in teacher & bot chats at " + getClass().getName()+".addDbRecord");
    }
    
    private void addChatMsg (Icon icon, String message, ChatBox.BoxType boxType, Timestamp timestamp) {
        chatArea.addChatBox(new ModelMessage(icon, message, timestamp), boxType);          
    }       
}
