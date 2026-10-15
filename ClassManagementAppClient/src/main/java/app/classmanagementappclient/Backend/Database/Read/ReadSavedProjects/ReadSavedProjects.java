package app.classmanagementappclient.Backend.Database.Read.ReadSavedProjects;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadSavedProjects {
    
    private final Connection connection;
    
    public ReadSavedProjects (Connection connection) {
        this.connection = connection;
    }
    
    public List<List<String>> getData () {
        
        List<List<String>> data = new ArrayList<>();
        UtilityClass uc = new UtilityClass();
        
        try (PreparedStatement statement = connection.prepareStatement(getQuery())) {
            setStament(statement, uc);
            new UtilityClass().getStringsStatementByRowRes(statement, data);
        } 
        catch (SQLException ex) {
            System.getLogger(ReadSavedProjects.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return data;
    }
    
    private String getQuery () {
            return 
            """
                SELECT ROW_NUMBER() OVER(ORDER BY schoolData.schoolName, classData.ClassName, classyearData.year, classTermData.term, classLessonData.lessonName),
                schoolData.SchoolName, classData.ClassName, classYearData.year, classTermData.term, classLessonData.lessonName FROM schoolData 
                LEFT JOIN teacherPersonalData ON teacherPersonalData.TeacherNum = schoolData.teacherNum 
                LEFT JOIN classData ON classData.schoolNum = schoolData.schoolNum
                LEFT JOIN classyearData ON classData.ClassNum = classyearData.ClassNum
                LEFT JOIN classtermData ON classtermData.ClassYearNum = classyearData.ClassYearNum
                LEFT JOIN classLessonData ON classTermData.classTermNum = classLessonData.classTermNum
                WHERE teacherPersonalData.teacherNum = ?
            """;        
    }
    
    private void setStament (PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
}
