package app.classmanagementappclient.Backend.Database.Delete.Students.General;

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
public class StudentDelete extends DeleteData
{
    private final ArrayList<String> clmNamesToDelete = new ArrayList<>();
    private final String studentID;
    
    public StudentDelete (String studentId) throws SQLException {
        this.studentID = studentId;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData ("studentpersonaldata");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmNamesToDelete.add("TeacherNum");
        clmNamesToDelete.add("studentid");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            setStatement(statement, uc);
            return uc.executeDMLStament(statement);
        }
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        uc.setStringStatement(statement, studentID, index);
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToDelete;
    }
}
