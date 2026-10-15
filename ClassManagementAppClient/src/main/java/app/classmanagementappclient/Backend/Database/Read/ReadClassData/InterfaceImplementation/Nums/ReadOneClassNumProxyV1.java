package app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Nums;

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
public class ReadOneClassNumProxyV1 implements ReadIntsInterface
{
    private final String schoolName;
    private final String className;
    private final List<Integer> classNum = new ArrayList<>();

    public ReadOneClassNumProxyV1(String schoolName, String className) {
        this.schoolName = schoolName;
        this.className = className;
    }   

    @Override
    public String getQuery() {
        return """
                SELECT classData.classNum FROM classData 
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ? AND classData.className = ?
               """;
    }    
    
    @Override
    public List<Integer> getData() {
        return classNum;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classNum, 1);
        
        if (classNum.size() > 1)
            throw new RuntimeException("schoolName & className determine 1 row of ClassData which isn't the case here!");        
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        uc.setStringsStatement(statement, new String[] {schoolName, className}, index);
    }   
}
