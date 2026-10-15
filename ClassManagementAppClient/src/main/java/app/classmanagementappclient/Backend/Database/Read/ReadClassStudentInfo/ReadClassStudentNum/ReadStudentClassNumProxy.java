package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassStudentNum;

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
public class ReadStudentClassNumProxy implements ReadIntsInterface {


    private final int classTermNum;
    private final String studentID;
    private final List<Integer> classStudentNum = new ArrayList<>();

    public ReadStudentClassNumProxy(int classTermNum, String studentID) {
        this.classTermNum = classTermNum;
        this.studentID = studentID;
    }
    
    @Override
    public List<Integer> getData() {
        return classStudentNum;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT studentClassData.studentClassNum FROM studentClassData
                            JOIN studentPersonalData ON studentPersonalData.studentNum = studentClassData.studentNum
                            WHERE studentPersonalData.teacherNum = ? AND studentClassData.classTermNum = ? AND studentPersonalData.studentID = ?
                       """;    
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classStudentNum, 1);
        
        if (classStudentNum.size() > 1)
            throw new RuntimeException("Yolla brozie u got serious error\n teacherNum & classTermNum & studentID are suppposed to retrieve 1 row of studentClassData, which isn't the case here\nHappened at ReadClassStudentNumProxy.readData");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntsStatement(statement, new Integer[] { LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), classTermNum }, 1);
        uc.setStringStatement(statement, studentID, index);
    }      
}
