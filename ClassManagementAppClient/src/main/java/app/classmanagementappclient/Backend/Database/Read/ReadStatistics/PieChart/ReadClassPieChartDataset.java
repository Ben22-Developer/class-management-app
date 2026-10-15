/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.PieChart;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.CommonStatisticsOperations;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadClassPieChartDataset extends ReadPieChartDataset{
    
    public ReadClassPieChartDataset(String schoolName, String className, int classYear) {
        super(schoolName, className, classYear, null, null, null);
    }

    @Override
    public String getQuery() {
        return """
               SELECT 
               
                (redZone/ (greenZone + orangeZone + redZone))*100 AS redZonePoints,
               
               (orangeZone/ (greenZone + orangeZone + redZone))*100 AS orangeZonePoints,
               
               (greenZone/(greenZone + orangeZone + redZone))*100 AS greenZonePoints
               
               FROM
               (
                    SELECT

                    SUM(CASE WHEN percentage BETWEEN 0 AND 49 THEN 1 ELSE 0 END) AS redZone,

                    SUM(CASE WHEN percentage BETWEEN 50 AND 69 THEN 1 ELSE 0 END) AS orangeZone,

                    SUM(CASE WHEN percentage BETWEEN 70 AND 100 THEN 1 ELSE 0 END) AS greenZone

                    FROM
                    (
                        SELECT (studentAssessmentRecordData.studentMarks/AssessmentData.totalMarks) * 100 AS percentage
                       
                        FROM studentAssessmentRecordData
                        %s
                   ) AS percentageAlias
                ) As zoneAlias
               """.formatted(CommonStatisticsOperations.getCommonStatisticsQuery());
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        CommonStatisticsOperations.setCommonClassStatisticsPlaceholders(statement, uc, getSchoolName(), getClassName(), getClassYear(), 1);
    }
    
    
}
