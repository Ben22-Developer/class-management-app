/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Process.StatisticsProcess;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Randomize.DashboardRandomizedStatistics;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Submitted.DashboardSubmittedStatistics;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.StatisticsPanel;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.Render.ChartRender;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class DashboardStatisticsUtility {
    
    public static void randomStatisticData (StatisticsPanel statisticsPanel) {
        SwingUtilities.invokeLater(() -> {
            statisticsPanel.enableButtons(false);
            DashboardRandomizedStatistics.randomStatistics(statisticsPanel);        
            statisticsPanel.enableButtons(true);
        });
    }
    
//    DashboardSubmittedStatistics
    
    public static void dashboardSubmittedStatistics (StatisticsPanel statisticsPanel) {
        SwingUtilities.invokeLater(() -> {
            statisticsPanel.enableButtons(false);
            DashboardSubmittedStatistics.submittedData(statisticsPanel);        
            statisticsPanel.enableButtons(true);
        });
    }    
    
    public static void noStatisticalDataDisplay (StatisticsPanel panel, String txt) {
        panel.getStatisticsTitleLabel().setText(txt);
    }

    public static void noStatisticalDataDisplay1 (StatisticsPanel statisticsPanel) {
        JPanel[] panels = { statisticsPanel.getLineChartPanel(), statisticsPanel.getPieChartPanel(), statisticsPanel.getHistogramPanel() };
        for (JPanel panel : panels) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        }
    }
    
    public static void statisticalDataDisplayTitle (StatisticsPanel statisticsPanel, List<String> classData) {
        statisticsPanel.getStatisticsTitleLabel().setText(classData.getFirst() + "/" + classData.get(1) + "/" + classData.getLast());
    }    
    
    public static List<Integer> getTerms (List<String> classData) {
        return new ClassTermMedium().getClassYearTerms
        (classData.getFirst(), classData.get(1), Integer.parseInt(classData.getLast()));
    }

    public static boolean render (StatisticsPanel statisticsPanel, List<String> classData, List<Integer> terms) {
        
        List<Double> histogramData = StatisticsProcess.getHistogramData(classData);
        List<List<Double>> lineChartData = StatisticsProcess.getLineChartData(classData, terms);
        List<List<Double>> pieChartData = StatisticsProcess.getPieChartData(classData);

        if (histogramData.isEmpty() || lineChartData.isEmpty() || pieChartData.isEmpty())
            return false;
        
        ChartRender.showHistogram(statisticsPanel.getHistogramPanel(), histogramData);
        ChartRender.showLineChart(statisticsPanel.getLineChartPanel(), lineChartData, terms);
        ChartRender.showPieChart(statisticsPanel.getPieChartPanel(), pieChartData);
        
        return true;
    }
    
}
