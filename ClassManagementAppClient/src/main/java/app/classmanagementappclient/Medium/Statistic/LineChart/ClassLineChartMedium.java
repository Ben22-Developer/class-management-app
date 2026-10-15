/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Statistic.LineChart;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.LineChart.ReadClassLineChartDataset;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassLineChartMedium extends LineChartMedium {
    
    public ClassLineChartMedium(String schoolName, String className, int classYear, List<Integer> classTerms) {
        super(schoolName, className, classYear, classTerms, null, null);
    }

    
    @Override
    public List<List<Double>> readData () {
        
        var lineChartInterface = new ReadClassLineChartDataset(getSchoolName(), getClassName(), getClassYear(), getClassTerms());
        executeOperation(lineChartInterface);
        return lineChartInterface.getData();
    }
    
}
