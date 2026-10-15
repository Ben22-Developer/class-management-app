/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat;

import app.classmanagementappclient.Medium.Chat.Delete.DeleteChatMedium;
import app.classmanagementappclient.Medium.Chat.Insert.InsertChatMedium;
import app.classmanagementappclient.Medium.Chat.Read.ReadChatName.ReadChatNameMedium;
import app.classmanagementappclient.Medium.Chat.Read.ReadChatUUID.ReadChatUUIDMedium;
import app.classmanagementappclient.Medium.Chat.Read.ReadTeacher_BotChat.ReadTeacher_BotChatMedium;
import app.classmanagementappclient.Medium.Chat.Update.UpdateChatMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.ChatCredentials;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ChatMedium {
    
    private String msg;
    
    public boolean insertNewChatCredentials (ChatCredentials chatCredentials) {
        
        boolean bool = new InsertChatMedium().insertNewChatCredentials(this, chatCredentials);
        
        if (!bool)
            showErrorMsg();
        
        return bool;
    }
    
    
    public boolean insertNewChat (Chat chat) {
        
        boolean bool = new InsertChatMedium().insertNewChat(this, chat);
        
        if (!bool)
            showErrorMsg();
        
        return bool;
    }    
    
    public boolean updateChatName (String chatName,  int chatID) {
        
        if (!validate(chatName)) {
            showErrorMsg();
            return false;
        }        
        
        boolean bool = new UpdateChatMedium().updateChat(this, chatName, chatID);
        
        if (!bool)
            showErrorMsg();
        
        return bool;
    }

    public boolean deleteChat (int chatID) {
        
        boolean bool = new DeleteChatMedium().deleteChat(this, chatID);
        
        if (!bool)
            showErrorMsg();
        
        return bool;
    } 
    
    private boolean validate (String chatName) {  
        
        Validation validation = new Validation();
        
        boolean bool = validation.isNameValid(chatName);
        
        setMsg(bool ? "" : validation.getErrorMsg());
        
        return bool;
    }
    
    public int readChatIDByChatName (String chatName) {
        return new ReadChatNameMedium().getChatID(chatName);
    }
    
    public int readChatIDByChatUUID (String uuid) {
        return new ReadChatUUIDMedium().getChatID(uuid);
    }
    
    public String readChatUUIDByChatID (int chatID) {
        return new ReadChatUUIDMedium().getChatUUID(chatID);
    }    
    
    public List<String> getAllChatNames ()  {
        return new ReadChatNameMedium().getAllChatNames();
    }
    
    public List<List<Object>> readTeacher_BotChats (String chatName) {
        return new ReadTeacher_BotChatMedium().getChats(chatName);
    }   
    
    private void showErrorMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
