package app.classmanagementappclient.Backend.Database.Delete.Teacher;

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
public class TeacherDelete extends DeleteData
{
    private final ArrayList<String> clmNamesToDelete = new ArrayList<>();
    
    public TeacherDelete() throws SQLException {
        super();
    }
    
    @Override
    public boolean deleteData () {
        return super.deleteData("teacherPersonalData");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmNamesToDelete.add("teacherNum");
    }
    
    @Override
    public boolean executeQuery (String query) throws SQLException 
    {
        int teacherID = super.getLoggedInTeacher().getTeacherNum();
        UtilityClass uc = super.getUc();
        boolean isDeleted;
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            uc.setIntStatement(statement, teacherID, 1);
            isDeleted = uc.executeDMLStament (statement);
            eraseTeacherAccount(isDeleted);
        }
        return isDeleted;
    }
    
    //remove teacher account from the app if deleted
    private void eraseTeacherAccount (boolean isDeleted)
    {
        if (isDeleted) 
            eraseTeacherAccount();
        else 
            super.setErrMsg("<html><p>Unexpected error, no delete done!<br>If it continues to persist then contact the developer!</p></html>");
    }
    
    private void eraseTeacherAccount () {
        LoggedInTeacher.makeTeacherLoggedOut();
        LoggedInTeacher.setTeacher(null);
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToDelete;
    }
}
