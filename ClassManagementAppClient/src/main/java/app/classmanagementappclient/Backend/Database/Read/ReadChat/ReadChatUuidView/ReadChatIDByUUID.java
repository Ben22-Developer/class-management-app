/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadChatUuidView;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
@Deprecated
public class ReadChatIDByUUID implements ReadIntsInterface{

    private final String uuid;
    private List<Integer> id = new ArrayList<>();
    
    public ReadChatIDByUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getQuery() {
        return "SELECT id FROM chatUUIDView WHERE uuid=?";
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setStringStatement(statement, uuid, 1);
    }    
    
    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        uc.getOneClmIntsStatementResult(statement, id, 1);
        
        if (id.size() != 1)
            throw new RuntimeException("Supposed to have 1 element in id List but no we've " + id.size() + " at " + this.getClass().getName()+".readData");
    }
    
    @Override
    public List<Integer> getData() {
        return id;
    }    
    
}
