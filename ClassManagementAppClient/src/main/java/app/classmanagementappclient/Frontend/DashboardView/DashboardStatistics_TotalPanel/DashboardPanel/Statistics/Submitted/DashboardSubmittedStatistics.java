/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Submitted;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.DashboardStatisticsUtility;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.StatisticsPanel;

import java.util.List;

/**
 *
 * @author BEN
 */
public class DashboardSubmittedStatistics {
    
    public static void submittedData (StatisticsPanel statisticsPanel) 
    {
        String schoolName = statisticsPanel.getSchoolBox().getSelectedItem().toString();
        String className = statisticsPanel.getClassBox().getSelectedItem().toString();
        int academicYear = Integer.parseInt(statisticsPanel.getAcademicYearBox().getSelectedItem().toString());
        List<Integer> terms = new ClassTermMedium().getClassYearTerms(schoolName, className, academicYear);
        
        if (terms.isEmpty()) {
            DashboardStatisticsUtility.noStatisticalDataDisplay(statisticsPanel, "The class selected has no registered terms!");
            DashboardStatisticsUtility.noStatisticalDataDisplay1(statisticsPanel);
        }
        else
            runProcess(statisticsPanel, List.of(schoolName, className, String.valueOf(academicYear)), terms);
    }
    
    private static void runProcess (StatisticsPanel statisticsPanel, List<String> classData, List<Integer> terms) {
        
        if (DashboardStatisticsUtility.render(statisticsPanel, classData, terms))
            DashboardStatisticsUtility.statisticalDataDisplayTitle(statisticsPanel,classData);
        else {
            DashboardStatisticsUtility.noStatisticalDataDisplay(statisticsPanel,"No saved assessment marks for statistical display for " + classData.toString().replace(",", "/"));        
            DashboardStatisticsUtility.noStatisticalDataDisplay1(statisticsPanel);
        }
    }
}
