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
// is deprecated u can use ReadOneClassNumProxyV1 instead!
public class ReadOneClassNumProxy implements ReadIntsInterface {

    private final int schoolNum;
    private final String className;
    private final List<Integer> classNum = new ArrayList<>();
    
    public ReadOneClassNumProxy (int schoolNum, String className) {
        this.schoolNum = schoolNum;
        this.className = className;
    }
    
    @Override
    public String getQuery() {
        return """
                SELECT classNum FROM classData WHERE className = ? AND schoolNum = ?;
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
            throw new RuntimeException("schoolNum & className determine 1 row of ClassData/Class which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setStringStatement(statement, className,1);
        uc.setIntStatement(statement, schoolNum, index);
    }
}
