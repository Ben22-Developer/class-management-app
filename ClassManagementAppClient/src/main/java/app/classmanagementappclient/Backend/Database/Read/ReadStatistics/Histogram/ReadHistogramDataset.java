package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.Histogram;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDoublesInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadStatistics.CommonStatisticsOperations;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadHistogramDataset implements ReadDoublesInterface {
    
    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classTerms;
    private final List<String> classLessons;
    private final List<String> studentIDs;
    private final List<Double> data = new ArrayList<>();
    
    public ReadHistogramDataset(String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerms = classTerms;
        this.classLessons = classLessons;
        this.studentIDs = studentIDs;
    }
    
    
    
    @Override
    public String getQuery() {
        var s = """
                SELECT (studentAssessmentRecordData.studentMarks / assessmentData.TotalMarks) * 100 FROM studentAssessmentRecordData
                %s
                %s
                %s
                %s
               """.formatted(CommonStatisticsOperations.getCommonStatisticsQuery(),
                       CommonStatisticsOperations.setTermsPlaceHolders(classTerms.size()),
                       CommonStatisticsOperations.setLessonNamePlaceHolders(classLessons.size()),
                       CommonStatisticsOperations.setStudentIDsPlaceHolders(studentIDs.size()));
        
//        System.out.println(s);
        
        return s;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getClmDoubleStatementResult(statement, data, 1);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        CommonStatisticsOperations.setCommonPlaceholders(statement, uc, schoolName, className, classYear, classTerms, classLessons, studentIDs, 1);
    }
    
    @Override
    public List<Double> getData() {
        return data;
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
