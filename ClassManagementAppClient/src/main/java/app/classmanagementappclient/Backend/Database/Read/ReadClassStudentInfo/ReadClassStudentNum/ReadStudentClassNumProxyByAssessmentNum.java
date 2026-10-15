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
public class ReadStudentClassNumProxyByAssessmentNum implements ReadIntsInterface {
    
    private final int assessmentNum;
    private final String studentID;
    private final List<Integer> classStudentNum = new ArrayList<>();

    public ReadStudentClassNumProxyByAssessmentNum (int assessmentNum, String studentID) {
        this.assessmentNum = assessmentNum;
        this.studentID = studentID;
    }
    
    @Override
    public List<Integer> getData() {
        return classStudentNum;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT studentClassData.studentClassNum, studentPersonalData.* FROM studentClassData 
                            JOIN studentPersonalData ON studentPersonalData.studentNum = studentClassData.studentNum
                            JOIN classTermData ON classTermData.classTermNum = studentClassData.classTermNum
                            JOIN classLessonData ON classLessonData.classTermNum = classTermData.classTermNum
                            WHERE studentPersonalData.studentID = ? AND studentPersonalData.TeacherNum = ? AND classLessonData.classLessonNum = 
                            (
                            	SELECT lessonNum FROM assessmentdata WHERE assessmentNum = ?
                            )
                       """;    
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classStudentNum, 1);
        
        if (classStudentNum.size() > 1)
            throw new RuntimeException("Yolla brozie u got serious error\n teacherNum & assessmentNum & studentID are suppposed to retrieve 1 row of studentClassData, which isn't the case here\nHappened at ReadStudentClassNumProxyByAssessmentNum.readData");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        int index = uc.setStringStatement(statement, studentID, 1);
        uc.setIntsStatement(statement, new Integer[] { LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), assessmentNum }, index);
        
    }      
}