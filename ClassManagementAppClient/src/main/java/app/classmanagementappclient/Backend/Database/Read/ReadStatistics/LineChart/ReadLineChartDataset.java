package app.classmanagementappclient.Backend.Database.Read.ReadStatistics.LineChart;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDoublesOfDoublesInterface;
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
public class ReadLineChartDataset implements ReadDoublesOfDoublesInterface {

    private final String schoolName;
    private final String className;
    private final int classYear;
    private final List<Integer> classTerms;
    private final List<String> classLessons;
    private final List<String> studentIDs;
    private final List<List<Double>> data = new ArrayList<>();
    
    public ReadLineChartDataset(String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs) {
        this.schoolName = schoolName;
        this.className = className;
        this.classYear = classYear;
        this.classTerms = classTerms;
        this.classLessons = classLessons;
        this.studentIDs = studentIDs;
    }    
    
    @Override
    public String getQuery() {
        
        String query = """
                        %s
                        %s
                        %s
                        %s
                        %s
                        %s
                        %s)
                       \nAS alias
                       """.formatted(getSelectQueryHeader(),
                               getFirstNestedQueryHeader1(),
                               getFirstNestedQueryHeader2(),
                               CommonStatisticsOperations.getCommonStatisticsQuery(),
                               CommonStatisticsOperations.setTermsPlaceHolders(classTerms.size()),
                               CommonStatisticsOperations.setLessonNamePlaceHolders(classLessons.size()),
                               CommonStatisticsOperations.setStudentIDsPlaceHolders(studentIDs.size()));
        
//        System.out.println(query);
        
        return query;
    }
    
    protected String getSelectQueryHeader () 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT").append("\n");
        
        for (int i = 0; i < classTerms.size(); i++) {
            
            sb.append("\n(Term").append(classTerms.get(i)).append("/Term").append(classTerms.get(i)).append("Total * 100) AS ").append("term").append(classTerms.get(i)).append("Percentage");
            appendComma(sb, i, classTerms.size());
        }
        sb.append("\nFROM");
        
        return sb.toString();
    }
    
    protected String getFirstNestedQueryHeader1 () {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("\t(SELECT").append("\n");
        
        for (int i = 0; i < classTerms.size(); i++)    
            sb.append("\n\tSUM(").append("CASE WHEN classTermData.term = ").append(classTerms.get(i)).append(" THEN (studentAssessmentRecordData.studentMarks) ELSE 0 END) AS Term").append(classTerms.get(i)).append(",");
        
        return sb.toString();
    }
    
    protected String getFirstNestedQueryHeader2 () {
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < classTerms.size(); i++) {
            
            sb.append("\n\tSUM(").append("CASE WHEN classTermData.term = ").append(classTerms.get(i)).append(" THEN (assessmentData.totalMarks) ELSE 0 END) AS Term").append(classTerms.get(i)).append("Total");
            appendComma(sb, i, classTerms.size());
        }
        
        sb.append("\nFROM studentAssessmentRecordData\n");
        return sb.toString();
    }
    
    private void appendComma (StringBuilder sb, int currIndex, int totalIteration) {
        sb.append((currIndex == totalIteration - 1) ? "" : ",").append("\n");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        CommonStatisticsOperations.setCommonPlaceholders(statement, uc, schoolName, className, classYear, classTerms, classLessons, studentIDs, 1);
    }    
    
    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getRowDoubleStatementResult(statement, data);
    }
    
    @Override
    public List<List<Double>> getData() {
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
