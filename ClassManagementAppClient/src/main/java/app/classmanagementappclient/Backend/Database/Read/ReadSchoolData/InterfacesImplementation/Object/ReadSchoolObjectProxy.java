package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Object;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadSchoolObjectProxy implements ReadSchoolObject 
{
    private final List<School> schools = new ArrayList<>();

    @Override
    public List<School> getData() {
        return schools;
    }

    @Override
    public String getQuery() {
    return """
            SELECT schoolData.schoolNum, schoolData.SchoolName
            FROM schoolData
            JOIN teacherPersonalData ON schoolData.teacherNum = teacherPersonalData.teacherNum
            WHERE teacherPersonalData.teacherNum = ? ORDER BY schoolData.SchoolName    
           """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getSchoolsRowRes(statement, schools);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
}
