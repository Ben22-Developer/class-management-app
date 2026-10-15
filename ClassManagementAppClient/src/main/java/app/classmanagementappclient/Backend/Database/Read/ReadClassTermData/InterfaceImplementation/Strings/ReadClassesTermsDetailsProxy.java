package app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Strings;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */

public class ReadClassesTermsDetailsProxy implements ReadStringOfStringInterface {
    
    private final List<List<String>> classTermDetails = new ArrayList<>();
    private final List<Integer> classTermNums;

    public ReadClassesTermsDetailsProxy(List<Integer> classTermNums) {
        this.classTermNums = classTermNums;
    }
    
    @Override
    public List<List<String>> getData() {
        return classTermDetails;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT schoolData.SchoolName, classData.ClassName, 
                            classYearData.year, classTermData.term
                            FROM teacherPersonalData
                            JOIN schoolData ON schoolData.teacherNum = teacherPersonalData.teacherNum
                            JOIN classData ON classData.schoolNum = schoolData.schoolNum
                            JOIN classyearData ON classData.ClassNum = classyearData.ClassNum
                            JOIN classtermData ON classtermData.ClassYearNum = classyearData.ClassYearNum
                            WHERE classTermData.classTermNum IN (
                       """;    
        return new UtilityClass().getWildCardsFormattedQuery(query, classTermNums.size(), "") + " ORDER BY school.SchoolName";
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, classTermDetails);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classTermNums.toArray(), 1);
    }   
}