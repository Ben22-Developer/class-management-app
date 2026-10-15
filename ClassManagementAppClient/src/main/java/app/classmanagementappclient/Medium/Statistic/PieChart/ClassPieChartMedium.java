/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Statistic.PieChart;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.PieChart.ReadClassPieChartDataset;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassPieChartMedium extends PieChartMedium{
    
    public ClassPieChartMedium(String schoolName, String className, int classYear) {
        super(schoolName, className, classYear, null, null, null);
    }

    @Override
    public List<List<Double>> readData () {
        
        var pieChartInterface = new ReadClassPieChartDataset(getSchoolName(), getClassName(), getClassYear());
        executeOperation(pieChartInterface);
        return pieChartInterface.getData();
    }
}
