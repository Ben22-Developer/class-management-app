package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassWithStudents;

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
public class ReadClassesWithStudentsProxy implements ReadIntsInterface {
    
    private final List<Integer> classWithStudents = new ArrayList<>();
    
    @Override
    public List<Integer> getData() {
        return classWithStudents;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT DISTINCT studentClassData.classTermNum
                            FROM studentClassData
                            JOIN studentPersonalData ON studentPersonalData.studentNum = studentClassData.studentNum
                            JOIN teacherPersonalData ON teacherPersonalData.TeacherNum = studentPersonalData.TeacherNum
                            WHERE teacherPersonalData.TeacherNum = ?
                       """;    
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classWithStudents, 1);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }        
}
