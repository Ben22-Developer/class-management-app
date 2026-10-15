package app.classmanagementappclient.Frontend.StatisticsWindow.Chart.Render;

import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class RenderNumbers {
    
    private static final String greenPerformanceRate = "G";
    private static final String orangePerformanceRate = "O";
    private static final String redPerformanceRate = "R";
    
    
    public static void renderNumbers(StatisticsWindowDialog window, List<Double> statisticsData) 
    {
        List<Double> sortedStatisticsData = statisticsData.parallelStream().sorted().toList();
        int mean = getMean(sortedStatisticsData);
        int highest = Integer.parseInt(String.valueOf((int) (double) sortedStatisticsData.getLast()));
        int lowest = Integer.parseInt(String.valueOf((int) (double) sortedStatisticsData.getFirst()));
        String performanceRate = getPerformanceRate(mean);
        
        renderValues(mean, window.getMeanLabel());
        renderValues(highest, window.getHighestLabel());
        renderValues(lowest, window.getLowestLabel());
        renderValues(performanceRate, window.getPerformanceRateLabel());
    }
    
    private static int getMean (List<Double> statisticsData) {
        
        double sum = 0;
        
        for (double data : statisticsData)
            sum+=data;
        
        return Integer.parseInt( String.valueOf((int) (sum/statisticsData.size())) );
    }  
    
    private static String getPerformanceRate (int mean) {
        
        if (mean <= 100 && mean >= 70)
            return greenPerformanceRate;
        
        else if (mean < 70 && mean >= 50)
            return orangePerformanceRate;        
        
        return redPerformanceRate;
    }
    
    private static void renderValues (int value, JLabel label) {
        label.setText(String.valueOf(value));
        label.setForeground(setFgColor(value));
    }
    
    private static void renderValues (String value, JLabel label) {
        label.setText(value);
        label.setForeground(setFgColor(value));
    }    
    
    private static Color setFgColor (int value) {
        
        if (value <= 100 && value >= 70)
            return new Color(0,215,0);
        
        else if (value < 70 && value >= 50)
            return new Color(255,140,0);        
        
        return new Color(255,0,0);        
    }
    
    private static Color setFgColor (String value) {
        return switch (value) {
            case greenPerformanceRate -> new Color(0,215,0);
            case orangePerformanceRate -> new Color(255,140,0);    
            case redPerformanceRate -> new Color(255,0,0);        
            default -> throw new AssertionError();
        };       
    }    
}
