package app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Nums;

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
public class ReadClassNums implements ReadIntsInterface
{
    private final List<Integer> classNums = new ArrayList<>();
    private final int schoolNum;

    public ReadClassNums(int schoolNum) {
        this.schoolNum = schoolNum;
    }
    
    @Override
    public String getQuery() {
        return """
               SELECT classData.classNum
               FROM classData
               JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
               WHERE schoolData.schoolNum = ? ORDER BY classData.className
               """;
    }    

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classNums, 1);
    }    
    
    @Override
    public List<Integer> getData() {
        return classNums;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, schoolNum, 1);
    }
}
