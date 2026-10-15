/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat.Read.ReadChatUUID;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatData;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatUuidView.ReadChatIDByUUID;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatUuidView.ReadChatUUIDByChatID;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadChatUUIDMedium {
    
    public int getChatID (String uuid) 
    {
        var proxy = new ReadChatIDByUUID(uuid);
        executeReadOperation(proxy);
        return proxy.getData().getFirst();
    }
    
    public String getChatUUID (int chatID) 
    {
        var proxy = new ReadChatUUIDByChatID(chatID);
        executeReadOperation(proxy);
        return proxy.getData().getFirst();
    }    
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            
            var readChatData = new ReadChatData(proxy);
            readChatData.setConnection(connection);
            readChatData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }       
}
