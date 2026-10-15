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
public class ReadClassStudentsProxyV1 implements ReadClassStudentsInterface {

    private final List<Integer> classTermNums;
    private final List<List<String>> classStudents = new ArrayList<>();

    public ReadClassStudentsProxyV1 (List<Integer> classTermNums) {
        this.classTermNums = classTermNums;
    }
    
    @Override
    public List<List<String>> getData() {
        return classStudents;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT DISTINCT studentPersonalData.StudentID, studentPersonalData.studentName
                            FROM studentPersonalData
                            JOIN studentClassData ON studentClassData.studentNum = studentPersonalData.studentNum
                            WHERE studentClassData.classTermNum IN (                   
                       """;    
        return new UtilityClass().getWildCardsFormattedQuery(query, classTermNums.size(), "", " ORDER BY studentPersonalData.studentName");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, classStudents);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classTermNums.toArray(), 1);
    }    
}
