/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadTeacherData;

import app.classmanagementappclient.Backend.Database.Read.Readables;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacherByUsername implements Readables
{
    private final String username;
    private Teacher teacher;
    private Connection connection;
    
    public ReadTeacherByUsername (String username) throws SQLException {
        super();
        this.username = username;
    }
    
    public void setTeacher (int teacherNum, String name, String encryptedPin, String phoneNumber, String email)
    {
        teacher = new Teacher(teacherNum, name, encryptedPin, phoneNumber, email);
    }
    
    public Teacher getData ()
    {
        return teacher;
    }
    
    @Override
    public void setConnection(Connection connection) 
    {
        this.connection = connection;
    }

    @Override
    public void executeRead() throws SQLException 
    {
        String query = "SELECT teacherNum, teacherName, teacherPin, teacherPhoneNumber, teacherEmail FROM teacherPersonalData WHERE teacherEmail = ?"; 
        List<List<Object>> lists = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query) ) 
        {
            uc.setStringStatement(statement, username, 1);
            uc.getObjectsStatementByRowRes(statement, lists);
            
            if (lists.isEmpty())
                return;
            
            List<String> data = lists.getFirst().stream().map(e -> String.valueOf(e)).toList();
            
            setTeacher(Integer.parseInt(data.getFirst()), data.get(1), data.get(2), data.get(3), data.get(4));
        }        
    }
}
