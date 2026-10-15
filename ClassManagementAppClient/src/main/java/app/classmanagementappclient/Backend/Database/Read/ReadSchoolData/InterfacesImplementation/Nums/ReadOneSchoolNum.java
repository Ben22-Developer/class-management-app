package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Nums;

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
public class ReadOneSchoolNum implements ReadIntsInterface 
{
    private final String schoolName;
    private final List<Integer> classNumsList = new ArrayList<>();

    public ReadOneSchoolNum(String schoolName) {
        this.schoolName = schoolName;
    }
    
    @Override
    public List<Integer> getData() {
        return classNumsList;
    }

    @Override
    public String getQuery() {
        return """
                SELECT schoolData.SchoolNum
                FROM schoolData
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.SchoolName = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classNumsList, 1);
        if (classNumsList.size() > 1)
            throw new RuntimeException("TeacherNum & SchoolName was supposed to determine one row in schoolsData \nbut it's not the case now!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        uc.setStringStatement(statement, schoolName, index);
    }
}
