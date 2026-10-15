/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Delete.TeacherMessage;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class DeleteTeacherMessage extends DeleteData {

    private final ArrayList<String> clmsToCheck = new ArrayList<>();
    private final TeacherMessage teacherMessage;
    
    public DeleteTeacherMessage (TeacherMessage teacherMessage) throws SQLException {
        super();
        this.teacherMessage = teacherMessage;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("TeacherMessageView");
    }

    @Override
    public void setClmNamesToCheck() {
        clmsToCheck.add("TeacherNum");
        clmsToCheck.add("recordedAt");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
    
        PreparedStatement statement = getPreparedStatement(query);
        
        UtilityClass uc = getUc();
        
        setStatement(statement, uc);
        
        return uc.executeDMLStament(statement);
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        int index = uc.setIntStatement(statement, super.getLoggedInTeacher().getTeacherNum(), 1);
        uc.setTimestampStatement(statement, teacherMessage.getTimestamp(), index);
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmsToCheck;
    }
}
