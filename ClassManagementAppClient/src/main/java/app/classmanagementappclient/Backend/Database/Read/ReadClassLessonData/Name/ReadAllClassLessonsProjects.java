package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Name;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadObjectOfObjectInterface;
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
public class ReadAllClassLessonsProjects implements ReadObjectOfObjectInterface
{
    private final List<List<Object>> lessonsDetails = new ArrayList<>();    
    
    @Override
    public String getQuery() {
        return """
                SELECT DISTINCT schoolData.SchoolName, classData.ClassName, 
                classYearData.year, classTermData.term
                FROM teacherPersonalData
                JOIN schoolData ON schoolData.teacherNum = teacherPersonalData.teacherNum
                JOIN classData ON classData.schoolNum = schoolData.schoolNum
                JOIN classYearData ON classYearData.classNum = classData.classNum
                JOIN classTermData ON classTermData.classYearNum = classYearData.classYearNum
                JOIN classLessonData ON classLessonData.classTermNum = classTermData.classTermNum
                WHERE teacherPersonalData.teacherNum = ? ORDER BY schoolData.SchoolName, classData.ClassName, classYearData.year, classTermData.term
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getObjectsStatementByRowRes(statement, lessonsDetails);
    }

    @Override
    public List<List<Object>> getData() {
        return lessonsDetails;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
    
}
