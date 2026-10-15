/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Update.Teacher;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Security.SecurityUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class TeacherPinUpdate extends UpdateData
{
    private final String pin;
    private final String confirmedPin;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();    
    private final int teacherNum;
    
    public TeacherPinUpdate (String pin, String confirmedPin, int teacherNum) throws SQLException 
    {
        super();
        this.pin = pin;
        this.confirmedPin = confirmedPin;
        this.teacherNum = teacherNum;
    }
    
    public boolean updateDetails () {
        return super.updateData("teacherPersonalData");
    }      
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        return !pin.equals(confirmedPin);
    }

    @Override
    public void duplicateCloser() {
        setErrorMsg("Pin confirmed is different from pin entered!");
        super.rememberToCloseConnection();        
    }

    @Override
    public void setDetailsToUpdate() 
    {
        toUpdateClms.add("teacherpin");
    }

    @Override
    public boolean updateDetails(String query) throws SQLException 
    {
        query = query + "WHERE teacherNum = ?";
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        boolean res;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            res = uc.executeDMLStament(statement);
        }
        return res;
    }
    
    private void setStatement (PreparedStatement statement) throws SQLException
    {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, SecurityUtility.encrypt(pin, SecurityUtility.getPasswordHashingCostFactor()), 1);
        uc.setIntStatement(statement, teacherNum, index);
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
    
}
