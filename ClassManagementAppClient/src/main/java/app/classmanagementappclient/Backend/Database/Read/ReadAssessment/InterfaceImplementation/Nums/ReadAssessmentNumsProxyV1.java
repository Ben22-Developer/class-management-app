package app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Nums;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAssessmentNumsProxyV1 implements ReadIntsInterface {
    
    private final String schoolName;
    private final String className;
    private final int year;
    private final int term;
    private final String lessonName;
    private final String assessmentName; 
    private final java.sql.Date date;
    private final List<Integer> assessementNum = new ArrayList<>();

    public ReadAssessmentNumsProxyV1(String schoolName, String className, int year, int term, String lessonName, String assessmentName, Date date) {
        this.schoolName = schoolName;
        this.className = className;
        this.year = year;
        this.term = term;
        this.lessonName = lessonName;
        this.assessmentName = assessmentName;
        this.date = date;
    }
    
    @Override
    public List<Integer> getData() {
        return assessementNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT assessmentData.AssessmentNum FROM assessmentdata
                JOIN classlessondata ON classlessondata.classLessonNum = assessmentData.LessonNum
                JOIN classtermdata ON classtermdata.classTermNum = classlessondata.classTermNum
                JOIN classyeardata ON classyeardata.classYearNum = classtermdata.classYearNum
                JOIN classdata ON classdata.classNum = classyeardata.classNum
                JOIN schooldata ON schooldata.schoolNum = classdata.schoolNum
                JOIN teacherpersonaldata ON teacherpersonaldata.teacherNum = schooldata.teacherNum
                WHERE teacherpersonaldata.teacherNum = ? AND schooldata.schoolName = ? AND classdata.className = ?
                AND classyeardata.year = ? AND classtermdata.term = ? AND classlessondata.lessonName = ? AND assessmentData.assessmentName = ? AND assessmentdata.assessmentDate = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, assessementNum, 1);
        
        if (assessementNum.size() > 1)
            throw new RuntimeException("techerNum & schoolName & className & year & term & lessonName & AssessmentName & asessmentDate determine 1 row of AssessmentData which isn't the case here\nBackend.Database.Read.ReadAssessment.InterfaceImplementation.Nums.ReadAssessmentNumProxy");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[]{ schoolName, className }, index);
        index = uc.setIntsStatement(statement, new Integer[]{ year, term }, index);
        index = uc.setStringsStatement(statement, new String[]{ lessonName, assessmentName }, index);
        uc.setDateStatement(statement, date, index);
    }    
}
