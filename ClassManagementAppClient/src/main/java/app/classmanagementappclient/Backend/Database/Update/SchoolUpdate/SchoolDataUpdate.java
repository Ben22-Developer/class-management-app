package app.classmanagementappclient.Backend.Database.Update.SchoolUpdate;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.School.School;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class SchoolDataUpdate extends UpdateData
{
    private final School currSchoolDetails;
    private final School newSchoolDetails;
    private int schoolNum;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public SchoolDataUpdate(School currSchoolDetails, School newSchoolDetails) throws SQLException {
        this.currSchoolDetails = currSchoolDetails;
        this.newSchoolDetails = newSchoolDetails;
    }
    
    public boolean updateDetails () {
        return super.updateData("schoolData");
    }    

    @Override
    public boolean duplicateChecker () throws SQLException 
    {
        String query =
                """
                    SELECT schoolNum FROM schoolData WHERE BINARY schoolName = ? AND teacherNum = ? 
                """;
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement (uc, statement, newSchoolDetails.getSchoolName());
            int res = uc.getStatementIntResult(statement, 1);
            return res != Integer.MIN_VALUE;
        }
    }

    @Override
    public void duplicateCloser(){
        super.setErrorMsg("School: " + newSchoolDetails.getSchoolName() + " exists already!");
        super.rememberToCloseConnection();          
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("schoolName");
        try {
            setSchoolNum();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }

    private void setSchoolNum () throws SQLException 
    {
        Connection connection = super.getConnection();
        String query = 
                """
                    SELECT schoolNum FROM schoolData WHERE schoolName = ? AND teacherNum = ? 
                """;
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(uc, statement, currSchoolDetails.getSchoolName());
            schoolNum = uc.getStatementIntResult(statement, 1);
        }
    }
    
    @Override
    public boolean updateDetails(String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        
        String completeQuery = query + " WHERE schoolNum = ?";
        
        Connection connection = super.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(completeQuery)) {
            setStatement(statement);
            return uc.executeDMLStament(statement);
        }
    }

    private void setStatement (UtilityClass uc, PreparedStatement statement, String string) throws SQLException {
        int teacherNum = super.getLoggedInTeacher().getTeacherNum();
        int index = uc.setStringStatement(statement, string, 1);
        uc.setIntStatement(statement, teacherNum, index);
    }

    private void setStatement (PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, newSchoolDetails.getSchoolName(), 1);
        uc.setIntStatement(statement, schoolNum, index);
    }
    
    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}
