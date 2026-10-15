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
public class ReadClassYearTermsProxy implements ReadIntsInterface
{
    private final List<Integer> classTerms = new ArrayList();
    private final String schoolName;
    private final String className;
    private final int year;

    public ReadClassYearTermsProxy(String schoolName, String className, int year) {
        this.schoolName = schoolName;
        this.className = className;
        this.year = year;
    }
    
    @Override
    public void readData (PreparedStatement  statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement,classTerms, 1);
    }
    
    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntsStatement(statement, new Integer[] { LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), year }, 1);
        uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
    }    
    
    @Override
    public List<Integer> getData() {
        return classTerms;
    }    

    @Override
    public String getQuery() {
        return """
                SELECT classTermData.term
                FROM teacherpersonaldata
                JOIN schoolData ON schoolData.teacherNum = teacherpersonaldata.teacherNum
                JOIN classData ON classData.schoolNum = schoolData.schoolNum
                JOIN classyeardata ON classData.ClassNum = classyeardata.ClassNum
                JOIN classtermdata ON classtermdata.ClassYearNum = classyeardata.ClassYearNum
                WHERE teacherpersonaldata.teacherNum = ? AND classyeardata.year = ? AND schoolData.SchoolName = ? AND classData.className = ?
                ORDER BY classTermData.term
               """;
    }
}
