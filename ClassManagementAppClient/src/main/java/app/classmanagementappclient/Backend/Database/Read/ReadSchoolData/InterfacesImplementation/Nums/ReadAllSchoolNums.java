package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Nums;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
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
public class ReadAllSchoolNums implements ReadIntsInterface
{
    private final List<Integer> schoolNums = new ArrayList();
    private final String query = """
                                    SELECT schoolData.SchoolNum
                                    FROM schoolData
                                    JOIN teacherPersonalData ON schoolData.teacherNum = teacherPersonalData.teacherNum
                                    WHERE teacherPersonalData.teacherNum = ? ORDER BY schoolData.SchoolName       
                                 """;
    @Override
    public void readData (PreparedStatement  statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement,schoolNums, 1);
    }
    
    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }    
    
    @Override
    public List<Integer> getData() {
        return schoolNums;
    }    

    @Override
    public String getQuery() {
        return query;
    }
}
