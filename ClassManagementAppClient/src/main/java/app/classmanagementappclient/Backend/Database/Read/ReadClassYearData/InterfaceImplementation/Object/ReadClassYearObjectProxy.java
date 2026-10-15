package app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassYearObjectProxy implements ReadClassYearObject {

    private final List<ClassYear> yearList = new ArrayList<>();
    private final List<Integer> classNumsList;
    
    public ReadClassYearObjectProxy(List<Integer> classNumsList) {
        this.classNumsList = classNumsList;
    }
    
    @Override
    public String getQuery() {
        String query = """
                            SELECT classYearData.classYearNum, classYearData.year, classYearData.classNum
                            FROM classYearData
                            JOIN classData ON classData.classNum = classYearData.classNum
                            WHERE classData.classNum in (                        
                       """;
        return new UtilityClass().getWildCardsFormattedQuery(query, classNumsList.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getClassYearsRowRes(statement, yearList);
    }
    
    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classNumsList.toArray(), 1);
    }    
    
    @Override
    public List<ClassYear> getData() {
        return yearList;
    }
}
