/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadTeacherData;

import app.classmanagementappclient.Backend.Database.Read.Readables;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacherPin implements Readables
{
    private List<String> data = new ArrayList<>();
    private Connection connection;
    private final UtilityClass uc = new UtilityClass();
    
    public ReadTeacherPin () throws SQLException {
        super();
    }    

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }    
    
    @Override
    public void executeRead () throws SQLException {
        String query = "SELECT teacherPin FROM teacherPersonalData WHERE teacherNum = ?"; 
        
        try (PreparedStatement statement = connection.prepareStatement(query) ) {
            setStatement(statement);
            uc.getStringsStatementByClmRes(statement, 1, data);
        }
    }
    
    public void setStatement (PreparedStatement statement) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
    
    public String getPin() {
        return data.isEmpty() ? null : data.getFirst();
    }    
}
