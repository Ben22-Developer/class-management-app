/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat.Insert;

import app.classmanagementappclient.Medium.Chat.ChatMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord.InsertChatCredentials;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord.InsertTeacher_BotChat;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.ChatCredentials;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertChatMedium {
    
    public boolean insertNewChatCredentials (ChatMedium chatMedium, ChatCredentials chatCredentials) {
        
        InsertChatCredentials insertNewChat = null;
        try (Connection connection = MediumUtility.getConnection()) 
        {
            insertNewChat = new InsertChatCredentials (connection, chatCredentials);
            return insertNewChat.insert();
            
        } catch (SQLException ex) {
            System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            chatMedium.setMsg (insertNewChat.getErrorMsg());
        }
        return false;
    }
    
    public boolean insertNewChat (ChatMedium chatMedium, Chat chat) {
        
        InsertTeacher_BotChat insertChat = null;
        try (Connection connection = MediumUtility.getConnection()) 
        {
            insertChat = new InsertTeacher_BotChat (connection, chat);
            return insertChat.insert();
            
        } catch (SQLException ex) {
            System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            chatMedium.setMsg (insertChat.getErrorMsg());
        }
        
        return false;
    }    
}
