package app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Nums;

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
public class ReadOneClassYearNumProxyV1 implements ReadIntsInterface
{
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classYearNum = new ArrayList<>();

    public ReadOneClassYearNumProxyV1(String schoolName, String className, int classYear) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
    }

    @Override
    public List<Integer> getData() {
        return classYearNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT classYearData.classYearNum FROM classYearData
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ? AND classData.className = ? AND classYearData.year = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classYearNum, 1);
        
        if (classYearNum.size() > 1)
            throw new RuntimeException("schoolName & className & year determine 1 row of ClassYearData which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        uc.setIntStatement(statement, classYear, index);
    }
}
