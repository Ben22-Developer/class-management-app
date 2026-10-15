/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatUuidView;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadChatUUIDByChatID implements ReadStringInterface{

    private final int chatID;
    private List<String> uuid = new ArrayList<>();
    
    public ReadChatUUIDByChatID (int chatID) {
        this.chatID = chatID;
    }

    @Override
    public String getQuery() {
        return "SELECT uuid FROM chatUUIDView WHERE id=?";
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, chatID, 1);
    }    
    
    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        uc.getStringsStatementByClmRes(statement, 1, uuid);
        
        if (uuid.size() != 1)
            throw new RuntimeException("Supposed to have 1 element in uuid List but no we've " + uuid.size() + " at " + this.getClass().getName()+".readData");
    }
    
    @Override
    public List<String> getData() {
        return uuid;
    }    
    
}
