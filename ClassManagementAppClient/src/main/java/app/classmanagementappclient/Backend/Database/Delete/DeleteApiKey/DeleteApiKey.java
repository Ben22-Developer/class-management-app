/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Delete.DeleteApiKey;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class DeleteApiKey extends DeleteData 
{   
    private final ArrayList<String> clmsToCheck = new ArrayList<>();
    
    public DeleteApiKey () throws SQLException {
        super();
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("teacher_apiKeyView");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmsToCheck.add("TeacherNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
            return uc.executeDMLStament(statement);
        }          
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmsToCheck;
    }
}
