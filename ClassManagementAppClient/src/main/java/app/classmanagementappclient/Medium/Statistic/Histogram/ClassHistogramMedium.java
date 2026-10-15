/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.Statistic.Histogram;

import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.Histogram.ReadClassHistogramDataset;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassHistogramMedium extends HistogramMedium {
    
    public ClassHistogramMedium(String schoolName, String className, int classYear) {
        super(schoolName, className, classYear, null, null, null);
    }

    @Override
    public List<Double> readData() {
        var histogramInterface = new ReadClassHistogramDataset(getSchoolName(), getClassName(), getClassYear());
        executeOperation(histogramInterface);
        return histogramInterface.getData();
    }
}
