/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacherMessage;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertTeacherMessage extends ObjectsInsert  {

    private final TeacherMessage teacherMessage;
    
    public InsertTeacherMessage(Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.teacherMessage = (TeacherMessage) object;
    }

    @Override
    public boolean insert() throws SQLException {
        
        String sql = "INSERT INTO TeacherMessageView (RecordedAt, TeacherNum, Message) VALUES(?,?,?)";
        
        PreparedStatement statement = getConnection().prepareStatement(sql);
        setStatement(statement);
        
        return InsertDatabaseManipulation.executePrepareStatement(statement, teacherMessage.toString());
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        
        UtilityClass uc = getUc();
        
        int index = uc.setTimestampStatement(statement, teacherMessage.getTimestamp(), 1);
        index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
        uc.setStringStatement(statement, teacherMessage.getMessage(), index);
    }
    
}
