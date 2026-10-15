package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num;

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
public class ReadOneClassLessonNumProxy implements ReadIntsInterface {
    
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final int classTerm;
    private final String lessonName;
    private final List<Integer> classLessonNum = new ArrayList<>();

    public ReadOneClassLessonNumProxy (String schoolName, String className, int classYear, int classTerm, String lessonName) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerm = classTerm;
        this.lessonName = lessonName;
    }

    @Override
    public List<Integer> getData() {
        return classLessonNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT classLessonData.classLessonNum FROM classLessonData
                JOIN classTermData ON classTermData.classTermNum = classLessonData.classTermNum
                JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ? AND classData.className = ? 
                AND classYearData.year = ? AND classTermData.term = ? AND classLessonData.lessonName = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classLessonNum, 1);
        
        if (classLessonNum.size() > 1)
            throw new RuntimeException("schoolName & className & year & term & lesson determine 1 row of ClassLessonData which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        index = uc.setIntsStatement(statement, new Integer[] {classYear, classTerm}, index);
        uc.setStringStatement(statement, lessonName, index);
    }    
}
