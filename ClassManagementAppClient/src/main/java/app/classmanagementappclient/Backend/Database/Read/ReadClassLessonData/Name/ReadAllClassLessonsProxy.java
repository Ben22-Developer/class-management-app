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
public class ReadAllClassLessonsProxy implements ReadStringInterface {

    private final List<String> lessons = new ArrayList<>();
    
    @Override
    public String getQuery() {
        return """
                SELECT DISTINCT classLessonData.lessonName FROM classLessonData
                JOIN classTermData ON classTermData.classTermNum = classLessonData.classTermNum
                JOIN classYearData ON classTermData.ClassYearNum = classYearData.ClassYearNum
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? ORDER BY classLessonData.lessonName
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
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);        
    }    
}
