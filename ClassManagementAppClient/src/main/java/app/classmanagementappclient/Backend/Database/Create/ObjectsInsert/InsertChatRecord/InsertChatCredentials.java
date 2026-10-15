/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.WebObject.Chat.ChatCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertChatCredentials extends ObjectsInsert{

    private final ChatCredentials chatCredentials;
    
    public InsertChatCredentials(Connection connection, Object object) throws SQLException {
        super(connection, object);
        chatCredentials = (ChatCredentials) object;
    }

    @Override
    public boolean insert() throws SQLException {
        
        Connection connection = getConnection();
        
        try {
            insert(connection);
        }catch (SQLException ex) {      
            if (ex instanceof SQLIntegrityConstraintViolationException integrityEx)
                setErrorMsg("Chat name " + chatCredentials.getChatName() +" already exists!");
            throw ex;
        }
        return true;
    }
    
    private boolean insert (Connection connection) throws SQLException {
        
        String query = "INSERT INTO chatUUIDView(uuid, chatName, teacherNum) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement(statement, chatCredentials.toString());
    }
   
    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = getUc();
        int index = uc.setStringsStatement(statement, new String[] { chatCredentials.getUuid(), chatCredentials.getChatName() }, 1);
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
    }
    
}
