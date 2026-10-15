package app.classmanagementappclient.Backend.Database.Read.ReadStudentInfo.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAllStudentProxy implements ReadStringOfStringInterface
{
    private final List<List<String>> data = new ArrayList();
    
    @Override
    public String getQuery () {
        
        return  """
                    SELECT ROW_NUMBER() OVER(ORDER BY studentpersonaldata.studentName, studentpersonaldata.studentID) AS num,
                    studentpersonaldata.studentID, studentpersonaldata.studentName FROM studentpersonaldata
                    WHERE TeacherNum = ?
                """;
    }
    @Override
    public void readData (PreparedStatement  statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, data);
    }
    
    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }    
    
    @Override
    public List<List<String>> getData() {
        return data;
    }
}
