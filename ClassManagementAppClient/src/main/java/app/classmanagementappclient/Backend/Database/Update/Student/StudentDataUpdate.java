package app.classmanagementappclient.Backend.Database.Update.Student;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class StudentDataUpdate extends UpdateData
{    
    private final Student currStudentData;
    private final Student newStudentData;
    private final ArrayList <String> dataToUpdate = new ArrayList<>();
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public StudentDataUpdate(Student currStudentData, Student newStudentData) throws SQLException {
        this.currStudentData = currStudentData;
        this.newStudentData = newStudentData;
    }
    
    public boolean updateData () {
        return super.updateData("studentPersonalData");
    }
    
    @Override
    public boolean duplicateChecker () throws SQLException {
        return currStudentData.getStudentID().equals(newStudentData.getStudentID()) && currStudentData.getStudentName().equals(newStudentData.getStudentName());
    }
    
    @Override
    public void duplicateCloser (){
        setErrorMsg("There's nothing to update!");
        super.rememberToCloseConnection();
    }
    
    // if cur and new ID are similar the teacher would be updating the student's name
    private boolean checkCurr_NewStudentIDSimilarity () {
        return currStudentData.getStudentID().equals(newStudentData.getStudentID());
    }
    
    @Override
    public void setDetailsToUpdate ()
    {
        String[] studentNames = { currStudentData.getStudentName(), newStudentData.getStudentName() };
        String[] studentIDs = { currStudentData.getStudentID(), newStudentData.getStudentID() };
        String[][] studentData = { studentNames, studentIDs };
        String[] clmNames = { "studentname", "studentid" };
        
        for (int i = 0; i < clmNames.length; i++) {
            addToUpdateClms(studentData[i], clmNames[i]);
        }
    }
    
    private void addToUpdateClms (String[] data, String clmName) 
    {
        String currStudentDataStr = data[0];
        String newStudentDataStr = data[1];
        boolean bool =  !(currStudentDataStr.equals(newStudentDataStr));
        
        if (bool) {
            toUpdateClms.add(clmName);
            dataToUpdate.add (newStudentDataStr);
        }
    }
    
    @Override
     public boolean updateDetails (String query) throws SQLException 
     {
        query = query + "WHERE studentid = ? AND teachernum = ?";
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();         
         
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            setStudentUpdateStatement(statement);
            return uc.executeDMLStament(statement);
        }
     }
    
     private void setStudentUpdateStatement (PreparedStatement statement) throws SQLException
     {
        UtilityClass uc = super.getUc();
        int teacherID = super.getLoggedInTeacher().getTeacherNum();
        int index = uc.setStringsStatement(statement, dataToUpdate.toArray(), 1);
        index = uc.setStringStatement(statement, currStudentData.getStudentID(), index);
        uc.setIntStatement(statement, teacherID, index);
     }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}
