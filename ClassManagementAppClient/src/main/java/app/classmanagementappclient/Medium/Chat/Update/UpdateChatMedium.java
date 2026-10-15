/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat.Update;

import app.classmanagementappclient.Medium.Chat.ChatMedium;
import app.classmanagementappclient.Backend.Database.Update.Chat.ChatNameUpdate;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class UpdateChatMedium {
    
    public boolean updateChat (ChatMedium chatMedium, String newChatName, int chatID) 
    {
        ChatNameUpdate chatNameUpdate = null;
        boolean res = false;
        try {
            chatNameUpdate = new ChatNameUpdate(newChatName, chatID);
            res = chatNameUpdate.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);    
        }
        
        if (!res)
            chatMedium.setMsg(chatNameUpdate.getErrorMsg());
        
        return res;        
    }    
}
