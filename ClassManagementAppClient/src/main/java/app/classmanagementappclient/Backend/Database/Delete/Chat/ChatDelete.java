/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Delete.Chat;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ChatDelete extends DeleteData {

    private final int chatID;
    private ArrayList<String> clmNamesToCheck = new ArrayList<>();
    
    public ChatDelete(int chatID) throws SQLException{
        super();
        this.chatID = chatID;
    }    
    
    @Override
    public boolean deleteData() {
        return super.deleteData("chatUUIDView");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNamesToCheck.add("id");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        
        PreparedStatement statement = getPreparedStatement(query);
        UtilityClass uc = getUc();
        uc.setIntStatement(statement, chatID, 1);
        return uc.executeDMLStament(statement);
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToCheck;
    }
    
}
