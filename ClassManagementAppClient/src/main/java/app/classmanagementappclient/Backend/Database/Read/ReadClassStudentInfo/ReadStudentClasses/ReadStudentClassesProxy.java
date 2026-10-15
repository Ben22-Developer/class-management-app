package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentClasses;

import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.Students.ReadClassStudentsInterface;
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
public class ReadStudentClassesProxy implements ReadClassStudentsInterface {

    private final String studentID;
    private final List<List<String>> studentClasses = new ArrayList<>();

    public ReadStudentClassesProxy (String studentID) {
        this.studentID = studentID;
    }
    
    @Override
    public List<List<String>> getData() {
        return studentClasses;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT ROW_NUMBER() OVER (),
                            schooldata.schoolName, classdata.className, classyeardata.year, classtermdata.term
                            FROM studentClassData
                            JOIN studentPersonalData ON studentPersonalData.studentNum = studentClassData.studentNum
                            JOIN classtermdata ON classtermdata.classTermNum = studentClassData.classTermNum
                            JOIN classyeardata ON classtermdata.classYearNum = classyeardata.classYearNum
                            JOIN classdata ON classyeardata.classNum = classdata.classNum
                            JOIN schooldata ON classdata.schoolNum = schooldata.schoolNum
                            WHERE studentPersonalData.studentID = ? AND studentPersonalData.teacherNum = ?             
                       """;    
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, studentClasses);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setStringStatement(statement, studentID, 1);
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
    }    
}
