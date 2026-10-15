/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Update.Chat;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ChatNameUpdate extends UpdateData
{
    private final String newChatName;
    private final int chatID;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public ChatNameUpdate(String newChatName, int chatID) throws SQLException {
        this.newChatName = newChatName;
        this.chatID = chatID;
    }
    
    public boolean updateDetails() {
        return super.updateData("chatUUIDView");
    }

    @Override
    public boolean duplicateChecker() throws SQLException 
    {
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        
        String query =
                """
                    SELECT id FROM chatUUIDView WHERE chatName=?
                """;        
        int res;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, newChatName);
            res = uc.getStatementIntResult(statement, 1);
        }
        return res != Integer.MIN_VALUE;        
    }

    @Override
    public void duplicateCloser() {
        String string = "Chat: "+newChatName + " already exists!";    
        super.setErrorMsg(string);
        super.rememberToCloseConnection();         
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("chatName");
    }

    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        query = query + " WHERE id = ?";
        PreparedStatement statement = getPreparedStatement(query);
        int index = setStatement(statement, newChatName);
        setStatement(statement, chatID, index);
        return getUc().executeDMLStament(statement);
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
    
    private int setStatement (PreparedStatement statement, String chatName) throws SQLException {
        UtilityClass uc = super.getUc();
        return uc.setStringStatement(statement, chatName, 1);
    }

    private void setStatement (PreparedStatement statement, int chatID, int index) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntStatement(statement, chatID, index);
    }    
    
}
