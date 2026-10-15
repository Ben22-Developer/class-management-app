package app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Nums;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAssessmentNumProxy implements ReadIntsInterface {
    
    private final int lessonNum;
    private final String assessmentName; 
    private final java.sql.Date date;
    private final List<Integer> attendanceNum = new ArrayList<>();

    public ReadAssessmentNumProxy (int lessonNum, String assessmentName, java.sql.Date date) {
        this.lessonNum = lessonNum;
        this.assessmentName = assessmentName;
        this.date = date;
    }
    
    @Override
    public List<Integer> getData() {
        return attendanceNum;
    }

    @Override
    public String getQuery() {
        return """
                select assessmentNum FROM assessmentData WHERE lessonNum = ? AND assessmentName = ? AND assessmentDate = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, attendanceNum, 1);
        
        if (attendanceNum.size() > 1)
            throw new RuntimeException("lessonNum & AssessmentName & asessmentDate determine 1 row of AssessmentData which isn't the case here\nBackend.Database.Read.ReadAssessment.InterfaceImplementation.Nums.ReadAssessmentNumProxy");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, lessonNum, 1);
        index = uc.setStringStatement(statement, assessmentName, index);
        uc.setDateStatement(statement, date, index);
    }  
    
}
