package app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataCollection;

import app.classmanagementappclient.Medium.Interfaces.Statistics.StatisticsMediumContract;
import app.classmanagementappclient.Medium.Statistic.Histogram.HistogramMedium;
import app.classmanagementappclient.Medium.Statistic.LineChart.LineChartMedium;
import app.classmanagementappclient.Medium.Statistic.PieChart.PieChartMedium;
import app.classmanagementappclient.Medium.Statistic.StatisticMedium;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.ChartEnum.ChartEnum;

import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author BEN
 */
public class ChartDataCollect {
    
    private StatisticsMediumContract histogram;
    private StatisticsMediumContract piechart;
    private StatisticsMediumContract linechart;
    
    private final StatisticMedium statisticMedium = new StatisticMedium();
    
    private ChartEnum chart;

    public ChartDataCollect(String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs) {
        this.histogram = new HistogramMedium(schoolName, className, classYear, classTerms, classLessons, studentIDs);
        this.piechart = new PieChartMedium(schoolName, className, classYear, classTerms, classLessons, studentIDs);
        this.linechart = new LineChartMedium(schoolName, className, classYear, classTerms, classLessons, studentIDs);
    }
    
    public ChartEnum getChart() {
        return chart;
    }

    public void setChart(String chartName) {
        this.chart = setChartEnum(chartName);
    }    
    
    private ChartEnum setChartEnum (String chartName) {
        
        Pattern histogramPattern = Pattern.compile("histogram", Pattern.CASE_INSENSITIVE);
        Pattern piePattern = Pattern.compile("pie", Pattern.CASE_INSENSITIVE);
        Pattern linePattern = Pattern.compile("line", Pattern.CASE_INSENSITIVE);        
        
        if (histogramPattern.matcher(chartName).find())
            return ChartEnum.Histogram;
        else if (piePattern.matcher(chartName).find())
            return ChartEnum.PieChart; 
        else if (linePattern.matcher(chartName).find())
            return ChartEnum.LineChart; 
        throw new AssertionError("Invalid chart name at ChartDataCollect.getChartIndex");
    }
    
    public Object getChartData () {
        
        return switch (chart) 
        {
            case ChartEnum.Histogram ->  statisticMedium.readData(histogram);
            case ChartEnum.PieChart -> statisticMedium.readData(piechart);
            case ChartEnum.LineChart -> statisticMedium.readData(linechart);
            default -> throw new AssertionError();
        };
    }    
}
