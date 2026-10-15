package app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassObjectProxy implements ReadClassObject
{
    private final List<Integer> schoolNums;
    private final List<Classs> classes = new ArrayList<>();
    
    public ReadClassObjectProxy(List<Integer> schoolNums) {
        this.schoolNums = schoolNums;
    }  

    @Override
    public String getQuery() {
        String query = """
                        SELECT classData.classNum, classData.className, classData.schoolNum
                        FROM classData
                        JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                        WHERE schoolData.schoolNum in (
                       """;
        return new UtilityClass().getWildCardsFormattedQuery (query, schoolNums.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getClassesRowRes(statement, classes);
    }
    
    @Override
    public List<Classs> getData() {
        return classes;
    }      

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, schoolNums.toArray(), 1);
    }
}
