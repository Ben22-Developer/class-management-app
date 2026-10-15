package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Names;

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
public class ReadAllSchoolNamesProxy implements ReadStringInterface
{
    private final List<String> schoolNames = new ArrayList();
    private final String query = """
                                    SELECT schoolData.SchoolName
                                    FROM schoolData
                                    JOIN teacherPersonalData ON schoolData.teacherNum = teacherPersonalData.teacherNum
                                    WHERE teacherPersonalData.teacherNum = ? ORDER BY schoolData.SchoolName      
                                 """;
    @Override
    public void readData (PreparedStatement  statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByClmRes(statement, 1, schoolNames);
    }
    
    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }    
    
    @Override
    public List<String> getData() {
        return schoolNames;
    }    

    @Override
    public String getQuery() {
        return query;
    }
}
