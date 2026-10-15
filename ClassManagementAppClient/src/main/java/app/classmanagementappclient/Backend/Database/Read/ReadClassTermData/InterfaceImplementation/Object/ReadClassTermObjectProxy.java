package app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassTermObjectProxy implements ReadClassTermObject {
    
    private final List<Integer> classYearNumsList;
    private final List<ClassTerm> terms = new ArrayList<>();

    public ReadClassTermObjectProxy(List<Integer> classYearNumsList) {
        this.classYearNumsList = classYearNumsList;
    }
    
    @Override
    public String getQuery() {
        String query = """
                            SELECT classTermData.classTermNum, classTermData.term, classTermData.classYearNum
                            FROM classTermData
                            JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
                            WHERE classYearData.classYearNum IN (                        
                       """;    
        return new UtilityClass().getWildCardsFormattedQuery(query, classYearNumsList.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getClassTermRowRes(statement, terms);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classYearNumsList.toArray(), 1);
    }
    
    @Override
    public List<ClassTerm> getData() {
        return terms;
    }    
}
