package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentsInClass;

import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.Students.ReadClassStudentsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassStudentsProxy implements ReadClassStudentsInterface {

    private final int classTermNum;
    private final List<List<String>> classStudents = new ArrayList<>();

    public ReadClassStudentsProxy (int classTermNum) {
        this.classTermNum = classTermNum;
    }
    
    @Override
    public List<List<String>> getData() {
        return classStudents;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT studentPersonalData.StudentID, studentPersonalData.studentName
                            FROM studentPersonalData
                            JOIN studentClassData ON studentClassData.studentNum = studentPersonalData.studentNum
                            WHERE studentClassData.classTermNum = ?  ORDER BY studentPersonalData.studentName                      
                       """;    
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, classStudents);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, classTermNum, 1);
    }    
}
