/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Chat.Read.ReadChatName;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatData;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatNameView.ReadAllChatName;
import app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatNameView.ReadChatIDByChatName;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadChatNameMedium {
    
    public int getChatID (String chatName) 
    {
        var proxy = new ReadChatIDByChatName(chatName);
        executeReadOperation(proxy);
        return proxy.getData().getFirst();
    }

    public List<String> getAllChatNames () 
    {
        var proxy = new ReadAllChatName();
        executeReadOperation(proxy);
        return proxy.getData();
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
