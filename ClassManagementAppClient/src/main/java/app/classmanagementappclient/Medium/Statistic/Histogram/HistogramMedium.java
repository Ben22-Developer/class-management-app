package app.classmanagementappclient.Medium.Statistic.Histogram;

import app.classmanagementappclient.Medium.Interfaces.Statistics.StatisticsMediumContract;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDoublesInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.Histogram.ReadHistogramDataset;
import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.ReadStatistics;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author BEN
 */
public class HistogramMedium implements StatisticsMediumContract {
    
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classTerms;
    private final List<String> classLessons;
    private final List<String> studentIDs;
    
    public HistogramMedium(String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerms = classTerms;
        this.classLessons = classLessons;
        this.studentIDs = studentIDs;
    }    
    
    @Override
    public List<Double> readData () {
        
        ReadDoublesInterface histogramInterface = new ReadHistogramDataset(schoolName, className, classYear, classTerms, classLessons, studentIDs);
        executeOperation(histogramInterface);
        return histogramInterface.getData();
    }
    
    protected void executeOperation (ReadDataInterface readData) 
    {
        try (Connection connection = MediumUtility.getConnection()) 
        {
            var statisticsData = new ReadStatistics(readData);
            statisticsData.setConnection(connection);
            statisticsData.executeRead();
        }
        catch (Exception ex) {
            System.getLogger(HistogramMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
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
