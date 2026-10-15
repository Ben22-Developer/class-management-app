package app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Nums;

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
public class ReadAllTermsProxy implements ReadIntsInterface {

    private final List<Integer> terms = new ArrayList<>();
    
    @Override
    public String getQuery() {
        return """
               SELECT DISTINCT classTermData.term
               FROM classTermData
               JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
               JOIN classData ON classData.classNum = classYearData.classNum
               JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
               JOIN teacherPersonalData ON schoolData.teacherNum = teacherPersonalData.teacherNum
               WHERE teacherPersonalData.teacherNum = ? ORDER BY classTermData.term
               """;
    }    

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, terms, 1);
    }    
    
    @Override
    public List<Integer> getData() {
        return terms;
    }    

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }    
}
