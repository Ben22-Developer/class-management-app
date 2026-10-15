/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.Statistics.Process;

import app.classmanagementappclient.Medium.Statistic.*;
import app.classmanagementappclient.Medium.Statistic.Histogram.*;
import app.classmanagementappclient.Medium.Statistic.LineChart.*;
import app.classmanagementappclient.Medium.Statistic.PieChart.ClassPieChartMedium;

import java.util.List;

/**
 *
 * @author BEN
 */
public class StatisticsProcess {
    
    public static List<Double> getHistogramData (List<String> classData) 
    {
        return (List<Double>) new StatisticMedium().readData
        (
            new ClassHistogramMedium
            (classData.getFirst(), classData.get(1),Integer.parseInt(classData.getLast()))
        );
    }
    
    public static List<List<Double>> getLineChartData (List<String> classData, List<Integer> terms) 
    {
        return (List<List<Double>>) new StatisticMedium().readData
        (
            new ClassLineChartMedium
            (classData.getFirst(), classData.get(1),Integer.parseInt(classData.getLast()),terms)              
        );        
    }

    public static List<List<Double>> getPieChartData (List<String> classData) 
    {
        return (List<List<Double>>) new StatisticMedium().readData
        (
            new ClassPieChartMedium
            (classData.getFirst(), classData.get(1),Integer.parseInt(classData.getLast()))              
        );        
    }    
}
