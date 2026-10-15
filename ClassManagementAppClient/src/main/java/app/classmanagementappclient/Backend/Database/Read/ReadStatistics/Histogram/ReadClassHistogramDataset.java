/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.Histogram;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.CommonStatisticsOperations;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadClassHistogramDataset extends ReadHistogramDataset {
    
    public ReadClassHistogramDataset(String schoolName, String className, int classYear) {
        super(schoolName, className, classYear, null, null, null);
    }
    
    
    @Override
    public String getQuery() {
        var s = """
                SELECT (studentAssessmentRecordData.studentMarks / assessmentData.TotalMarks) * 100 FROM studentAssessmentRecordData
                %s
               """.formatted(CommonStatisticsOperations.getCommonStatisticsQuery());
        
//        System.out.println(s);        
        return s;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        CommonStatisticsOperations.setCommonClassStatisticsPlaceholders(statement, uc, getSchoolName(), getClassName(), getClassYear(), 1);
    }
}
