/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.PieChart;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDoublesOfDoublesInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.CommonStatisticsOperations;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadPieChartDataset implements ReadDoublesOfDoublesInterface {

    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classTerms;
    private final List<String> classLessons;
    private final List<String> studentIDs;
    private final List<List<Double>> data = new ArrayList<>();
    
    public ReadPieChartDataset(String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerms = classTerms;
        this.classLessons = classLessons;
        this.studentIDs = studentIDs;
    }
    
    @Override
    public List<List<Double>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        String query = """
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
                        %s
                        %s
                        %s
                   ) AS percentageAlias
                ) As zoneAlias
               """.formatted(CommonStatisticsOperations.getCommonStatisticsQuery(),
                       CommonStatisticsOperations.setTermsPlaceHolders(classTerms.size()),
                       CommonStatisticsOperations.setLessonNamePlaceHolders(classLessons.size()),
                       CommonStatisticsOperations.setStudentIDsPlaceHolders(studentIDs.size()));
        
//        System.out.println(query);
        
        return query;
    }
    
    

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getRowDoubleStatementResult(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        CommonStatisticsOperations.setCommonPlaceholders(statement, uc, schoolName, className, classYear, classTerms, classLessons, studentIDs, 1);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getClassName() {
        return className;
    }

    public int getClassYear() {
        return classYear;
    }

    public List<Integer> getClassTerms() {
        return classTerms;
    }

    public List<String> getClassLessons() {
        return classLessons;
    }

    public List<String> getStudentIDs() {
        return studentIDs;
    }
    
    
}
