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
public class ReadOneClassTermNumProxyV1 implements ReadIntsInterface
{
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final int classTerm;
    private final List<Integer> classTermNum = new ArrayList<>();

    public ReadOneClassTermNumProxyV1 (String schoolName, String className, int classYear, int classTerm) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerm = classTerm;
    }

    @Override
    public List<Integer> getData() {
        return classTermNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT classTermData.classTermNum FROM classTermData
                JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ? AND classData.className = ? 
                AND classYearData.year = ? AND classTermData.term = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classTermNum, 1);
        
        if (classTermNum.size() > 1)
            throw new RuntimeException("schoolName & className & year & term determine 1 row of ClassTermData which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        uc.setIntsStatement(statement, new Integer[] {classYear, classTerm}, index);
    }
}
