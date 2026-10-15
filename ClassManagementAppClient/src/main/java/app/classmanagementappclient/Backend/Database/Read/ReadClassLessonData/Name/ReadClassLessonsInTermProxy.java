package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Name;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
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
public class ReadClassLessonsInTermProxy implements ReadStringInterface {

    private final String schoolName;
    private final String className;
    private final int classYear;
    private final int classTerm;
    private final List<String> lessons = new ArrayList<>();
    
    public ReadClassLessonsInTermProxy(String schoolName, String className, int classYear, int classTerm) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerm = classTerm;
    }
    
    @Override
    public String getQuery() {
        return """
                SELECT classLessonData.lessonName FROM classLessonData
                JOIN classTermData ON classTermData.classTermNum = classLessonData.classTermNum
                JOIN classYearData ON classTermData.ClassYearNum = classYearData.ClassYearNum
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ?
                AND classData.className = ? AND classYearData.year = ? AND classTermData.term = ? 
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByClmRes(statement, 1, lessons);
    }

    @Override
    public List<String> getData() {
        return lessons;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        uc.setIntsStatement(statement, new Integer[] { classYear, classTerm }, index);
    }
}
