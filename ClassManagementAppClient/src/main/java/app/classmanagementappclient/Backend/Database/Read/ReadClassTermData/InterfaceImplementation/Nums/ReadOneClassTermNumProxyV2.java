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
public class ReadOneClassTermNumProxyV2 implements ReadIntsInterface
{
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classTerms;
    private final List<Integer> data = new ArrayList<>();

    public ReadOneClassTermNumProxyV2 (String schoolName, String className, int classYear, List<Integer> classTerms) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerms = classTerms;
    }

    @Override
    public List<Integer> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        String query = """
                SELECT classTermData.classTermNum FROM classTermData
                JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
                JOIN classData ON classData.classNum = classYearData.classNum
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                WHERE teacherPersonalData.teacherNum = ? AND schoolData.schoolName = ? AND classData.className = ? 
                AND classYearData.year = ? AND classTermData.term IN(
               """;
        return new UtilityClass().getWildCardsFormattedQuery(query, classTerms.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, data, 1);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        index = uc.setIntStatement(statement, classYear, index);
        uc.setIntsStatement(statement, classTerms.toArray(), index);
    }
}
