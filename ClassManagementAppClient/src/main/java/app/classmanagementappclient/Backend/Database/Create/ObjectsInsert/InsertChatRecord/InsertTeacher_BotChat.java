/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertTeacher_BotChat extends ObjectsInsert {

    private final Chat chat;
    
    public InsertTeacher_BotChat(Connection connection, Object object) throws SQLException {
        super(connection, object);
        chat = (Chat) object;
    }

    @Override
    public boolean insert() throws SQLException {
        
        String query = "INSERT INTO Teacher_BotChatView(chatID, content, type, timestamp) VALUES (?,?,?,?)";
        Connection connection = getConnection();
        
        PreparedStatement statement = connection.prepareStatement(query);      
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement(statement, chat.toString()+"\n");
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        
        UtilityClass uc = getUc();
        
        int index  = uc.setIntStatement(statement, chat.getChatID(), 1);
        index = uc.setStringsStatement(statement, new String[] { chat.getContent(), chat.getChatType().toString() }, index);
        uc.setTimestampStatement(statement, chat.getTimestamp(),index);
    }
    
}
