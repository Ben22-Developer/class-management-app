package app.classmanagementappclient.Backend.Database.Update.Teacher;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Backend.Security.SecurityUtility;
import app.classmanagementappclient.FileHandlers.TeacherFileManipulator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class TeacherDetailsUpdate extends UpdateData
{
    private Teacher currTeacherData, newTeacherData;
    private final ArrayList <String> dataToUpdate = new ArrayList<>();
    private final ArrayList<String> toUpdateClms = new ArrayList<>();
    
    public TeacherDetailsUpdate () throws SQLException {
        super();
    }
    
    public boolean updateDetails () {
        return super.updateData("teacherPersonalData");
    }    
    
    @Override
    public boolean duplicateChecker () throws SQLException {
        String query =
                """
                    SELECT COUNT(teacherNum) FROM teacherPersonalData 
                    WHERE BINARY teacherEmail = ?
                """;
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();

        try (PreparedStatement statement = connection.prepareStatement(query)) 
        {
            setStatement (statement, newTeacherData.getEmail());
            int res = uc.getStatementIntResult(statement, 1);
            return res > 1;
        }
    }
    
    @Override
    public void duplicateCloser () {
        setErrorMsg("There is nothing to update");
        super.rememberToCloseConnection();
    }
    
    @Override
    public void setDetailsToUpdate ()
    {
        String[] names = { currTeacherData.getName(), newTeacherData.getName() };
        String[] pins = { currTeacherData.getEncryptedPin(), newTeacherData.getPin() };
        String[] phoneNbrs = { currTeacherData.getPhoneNumber(), newTeacherData.getPhoneNumber() };
        String[] email = { currTeacherData.getEmail(), newTeacherData.getEmail()};
        String[][] teacherData = { names, pins, phoneNbrs, email };
        String[] clmNames = { "teacherName", "teacherPin", "teacherPhoneNumber", "teacherEmail" };
        
        for (int i = 0; i < clmNames.length; i++) 
        {
            boolean isEqual = false;
            
            if (i == 1) {
                isEqual = checkPinClmDataEquality(teacherData[i]);
                setNewTeacherEncryptedPin (isEqual);
                setToUpdateClms_DataToUpdate(isEqual, clmNames[i], newTeacherData.getEncryptedPin());
                continue;
            }
            
            isEqual = checkClmsDataEquality(teacherData[i]);
            setToUpdateClms_DataToUpdate(isEqual, clmNames[i], teacherData[i][1]);
        }
    }

    private boolean checkClmsDataEquality (String[] data) 
    {
        String currTeacherDataStr = data[0];
        String newTeacherDataStr = data[1];
        return currTeacherDataStr.equals(newTeacherDataStr);
    }

    private boolean checkPinClmDataEquality (String[] data) 
    {
        String currTeacherEncyptedPin = data[0];
        String newTeacherNonEncyptedPin = data[1];
        return SecurityUtility.encrypted_NonEncryptedCheckUp(currTeacherEncyptedPin, newTeacherNonEncyptedPin);
    }
    
    private void setNewTeacherEncryptedPin (boolean isEqual)
    {
        if (isEqual)
            return;
        
        newTeacherData.setEncrptedPin();
    }
    
    private void setToUpdateClms_DataToUpdate (boolean bool, String clmName, String newTeacherDataStr)
    {
        if (bool)
            return;
        
        toUpdateClms.add(clmName);
        dataToUpdate.add (newTeacherDataStr);    
    }

    @Override
     public boolean updateDetails (String query) throws SQLException 
     {
        if (toUpdateClms.isEmpty()) {
            setErrorMsg("There's nothing to update!");
            return false;
        }
         
        query = query + "WHERE teacherNum = ?";
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        boolean res;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            res = uc.executeDMLStament(statement);
        }
        
        if (!res)
            return false;
        
        newTeacherData.setTeacherNum(currTeacherData.getTeacherNum());
        LoggedInTeacher.setTeacher(newTeacherData);
        TeacherFileManipulator.storeTeacherIfLoggedIn();
        
        return res;
     }    
     
     private void setStatement (PreparedStatement statement) throws SQLException
     {
        UtilityClass uc = super.getUc();
        int teacherID = super.getLoggedInTeacher().getTeacherNum();
        int index = uc.setStringsStatement(statement, dataToUpdate.toArray(), 1);
        uc.setIntStatement(statement, teacherID, index);
     }     

     private void setStatement (PreparedStatement statement, String teacherEmail) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setStringStatement(statement, teacherEmail, 1);
     } 
     
    public void setCurrTeacherData (Teacher currTeacherData) {
        this.currTeacherData = currTeacherData;
    }

    public void setNewTeacherData(Teacher newTeacherData) {
        this.newTeacherData = newTeacherData;
    }
    
    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}
