package app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Nums;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
// deprecated
public class ReadOneClassYearNumProxy implements ReadIntsInterface
{
    private final int year;
    private final int classNum;
    private final List<Integer> classYearNum = new ArrayList<>();

    public ReadOneClassYearNumProxy(int year, int classNum) {
        this.year = year;
        this.classNum = classNum;
    }
    
    @Override
    public List<Integer> getData() {
        return classYearNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT classYearNum FROM classYearData WHERE year = ? AND classNum = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classYearNum, 1);
        if (classYearNum.size() > 1)
            throw new RuntimeException("classNum & year determine 1 row of ClassYearData/classYear which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, new Integer[] { year, classNum }, 1);
    }
}
