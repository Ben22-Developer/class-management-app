/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadTeacherMessage.ReadAllTeacherMessage;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAllTeacherMessage implements ReadStringOfStringInterface{

    private final List<List<String>> data = new ArrayList<>();
    
    @Override
    public String getQuery() {
        return """
                SELECT recordedAt, message FROM TeacherMessageView WHERE teacherNum = ?
               """;
    }    
    
    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }   
}
