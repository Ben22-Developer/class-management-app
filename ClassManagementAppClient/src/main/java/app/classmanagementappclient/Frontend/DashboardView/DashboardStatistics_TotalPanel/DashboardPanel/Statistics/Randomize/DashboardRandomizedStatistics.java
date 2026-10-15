/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Randomize;

import app.classmanagementappclient.Medium.AssessmentRecord.Read.ReadAssessmentRecordMedium;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.DashboardStatisticsUtility;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.StatisticsPanel;

import java.security.SecureRandom;
import java.util.List;


/**
 *
 * @author BEN
 */
public class DashboardRandomizedStatistics {

    public static void randomStatistics (StatisticsPanel statisticsPanel) {
        
        var studentRecords = new ReadAssessmentRecordMedium().getAllStudentAssessmentRecord();
        
        if (studentRecords.isEmpty()) {
            DashboardStatisticsUtility.noStatisticalDataDisplay(statisticsPanel,"No saved assessment marks for statistical display.");
            DashboardStatisticsUtility.noStatisticalDataDisplay1(statisticsPanel);
        }
        else
            randomProcess(statisticsPanel, studentRecords.get(new SecureRandom().nextInt(studentRecords.size())));
    }
    
    private static void randomProcess (StatisticsPanel statisticsPanel, List<String> random) {
        
        List<Integer> terms = DashboardStatisticsUtility.getTerms(random);
        
        if (DashboardStatisticsUtility.render(statisticsPanel, random, terms))
            DashboardStatisticsUtility.statisticalDataDisplayTitle(statisticsPanel,random);
        else {
            DashboardStatisticsUtility.noStatisticalDataDisplay(statisticsPanel,"No saved assessment marks for statistical display.");
            DashboardStatisticsUtility.noStatisticalDataDisplay1(statisticsPanel);
        }
    }
}
