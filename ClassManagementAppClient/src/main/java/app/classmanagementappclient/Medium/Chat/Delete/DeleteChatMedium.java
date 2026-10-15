/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat.Delete;

import app.classmanagementappclient.Medium.Chat.ChatMedium;
import app.classmanagementappclient.Backend.Database.Delete.Chat.ChatDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteChatMedium {
    
    public boolean deleteChat (ChatMedium chatMedium, int chatID)
    {
        ChatDelete chatDelete = null;
        boolean res = false;
        
        try {
            chatDelete = new ChatDelete(chatID);
            res = chatDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            chatMedium.setMsg(chatDelete.getErrorMsg());

        return res;
    }
}