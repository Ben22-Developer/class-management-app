/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.LineChart;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.CommonStatisticsOperations;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassLineChartDataset extends ReadLineChartDataset{
    
    public ReadClassLineChartDataset(String schoolName, String className, int classYear, List<Integer> classTerms) {
        super(schoolName, className, classYear, classTerms, null, null);
    }

    @Override
    public String getQuery() {
        String s = """
                %s
                %s
                %s
                %s
                %s)
               \nAS alias
               """.formatted(getSelectQueryHeader(),
                       getFirstNestedQueryHeader1(),
                       getFirstNestedQueryHeader2(),
                       CommonStatisticsOperations.getCommonStatisticsQuery(),
                       CommonStatisticsOperations.setTermsPlaceHolders(getClassTerms().size()));
        
//        System.out.println(s);
        
        return s;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = CommonStatisticsOperations.setCommonClassStatisticsPlaceholders(statement, uc, getSchoolName(), getClassName(), getClassYear(), 1);
        uc.setIntsStatement(statement, getClassTerms().toArray(), index);
    }
}
