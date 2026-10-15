/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector;

import app.classmanagementappclient.Medium.Chat.ChatMedium;
import app.classmanagementappclient.Backend.Objects.Enum.ChatType;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.ChatCredentials;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class BackendToMenuUtility {

    public static boolean insertNewChatCredentials (String chatName) {
        ChatCredentials credentials  = new ChatCredentials(chatName);
        return new ChatMedium().insertNewChatCredentials(credentials);
    }
    
    public static boolean insertNewChat (int chatID, String content, ChatType chatType, Timestamp timestamp) {
        return new ChatMedium().insertNewChat(new Chat(chatID, content, chatType, timestamp));
    }    

    public static boolean updateChatName (String currChatName, String newChatName) {
         int chatID = getChadIDByChatName(currChatName);
        return new ChatMedium().updateChatName(newChatName, chatID);
    }

    public static boolean deleteChat (int chatID) {
        return new ChatMedium().deleteChat(chatID);
    }    
    
    public static int getChadIDByChatName (String chatName) {
        return new ChatMedium().readChatIDByChatName(chatName);
    }
    
    public static List<String> getAllChatNames () {
        return new ChatMedium().getAllChatNames();
    }
    
    public static List<List<Object>> teacher_BotChats (String chatName) {
        return new ChatMedium().readTeacher_BotChats(chatName);
    }
    
    public static List<Chat> formatTeacher_BotChats (List<List<Object>> chatList) 
    {
        List<Chat> chat = new ArrayList<>();
        
        chatList.forEach(e -> 
        {
            int chatID = (int) e.get(0);
            String content = String.valueOf( e.get(1) );
            ChatType chatType = ChatType.valueOf(String.valueOf( e.get(2) ));
            Timestamp timestamp = (Timestamp) e.get(3);
            
            chat.add(new Chat(chatID, content, chatType, timestamp));
        });
        
        return chat;
    }
}
