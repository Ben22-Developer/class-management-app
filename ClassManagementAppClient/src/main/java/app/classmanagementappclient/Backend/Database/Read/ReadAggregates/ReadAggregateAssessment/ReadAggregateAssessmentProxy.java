package app.classmanagementappclient.Backend.Database.Read.ReadAggregates.ReadAggregateAssessment;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAggregateAssessmentProxy implements ReadStringOfStringInterface {
    
    private final String clmName = "clm";
    private int clmNbr = 1;
    private final String subQueryName = "sub";
    private final List<List<Integer>> assessmentNums;
    private final List<Integer> classTermNums;
    private final String[] clmNames;
    private final List<List<String>> data = new ArrayList<>();
    private final UtilityClass uc = new UtilityClass();
    
    public ReadAggregateAssessmentProxy (List<List<Integer>> assessmentNums, List<Integer> classTermNums) {
        this.assessmentNums = assessmentNums;
        this.classTermNums = classTermNums;
        this.clmNames = new String[assessmentNums.size()];
    }
    

    @Override
    public String getQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryFormat(queryBuilder);
        
//        System.out.println(queryBuilder.toString());
        
        return queryBuilder.toString();
    }
    
    private void queryFormat (StringBuilder queryBuilder) {
        
        preSubQuery(queryBuilder);
        selectClms(queryBuilder);
        joinSection(queryBuilder);
        whereClause(queryBuilder);
        groupBy_SubQueryPart(queryBuilder);
    }
    
    private void preSubQuery (StringBuilder queryBuilder) {
        
        queryBuilder.append("SELECT").append(" *,").append(" ");
        int attendanceSize = assessmentNums.size();
        int i = 0;
        
        while (i < attendanceSize) {
            
            String currClmName = clmName + clmNbr;
            clmNames[i] = currClmName;
            
            if (i == attendanceSize - 1)
                queryBuilder.append(currClmName).append(" FROM (");
            else
                queryBuilder.append(currClmName).append("+");
            i += 1;
            clmNbr += 1;
        }
        queryBuilder.append("\n");
    }
    
    private void selectClms (StringBuilder queryBuilder) {
        
        queryBuilder.append("SELECT DISTINCT studentpersonaldata.studentID, studentpersonaldata.studentName,\n");
        
        for (int i = 0; i < assessmentNums.size(); i++) {
            
            queryBuilder.append("SUM(CASE WHEN studentAssessmentRecordData.AssessmentNum IN (");
            setAssessmentNumsByRowQueryFormat(queryBuilder, assessmentNums.get(i));
            if (i == assessmentNums.size() - 1)
                queryBuilder.append(") THEN studentAssessmentRecordData.studentMarks ELSE 0 END) AS ").append(clmNames[i]).append("\n");
            else    
                queryBuilder.append(") THEN studentAssessmentRecordData.studentMarks ELSE 0 END) AS ").append(clmNames[i]).append(",\n");
            
        }
    }
    
    private void setAssessmentNumsByRowQueryFormat (StringBuilder queryBuilder, List<Integer> assessmentNumsByRow) {
        for (int i = 0; i < assessmentNumsByRow.size(); i++)    
            if (i == assessmentNumsByRow.size() - 1)
                queryBuilder.append(assessmentNumsByRow.get(i));
            else
                queryBuilder.append(assessmentNumsByRow.get(i)).append(",");
    }
    
    private void joinSection (StringBuilder queryBuilder) {
        queryBuilder.append("FROM studentpersonaldata\n");
        queryBuilder.append("JOIN studentclassdata ON studentclassdata.studentNum = studentpersonaldata.studentNum\n");
        queryBuilder.append("LEFT JOIN studentassessmentrecorddata ON studentassessmentrecorddata.StudentClassNum = studentclassdata.studentClassNum\n");
    }
    
    private void whereClause (StringBuilder queryBuilder) {
        
        queryBuilder.append("WHERE studentclassdata.classtermnum IN (");
        
        for (int i = 0; i < classTermNums.size(); i++)
            if (i == classTermNums.size() - 1)
                queryBuilder.append(classTermNums.get(i)).append(")");
            else
                queryBuilder.append(classTermNums.get(i)).append(",");            
        
        queryBuilder.append("\n");
    }
    
    private void groupBy_SubQueryPart (StringBuilder queryBuilder) {
        queryBuilder.append("GROUP BY studentpersonaldata.studentID, studentpersonaldata.studentName\n");
        queryBuilder.append("ORDER BY studentpersonaldata.studentName,studentpersonaldata.studentID\n");
        queryBuilder.append(") AS ").append(subQueryName);
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }


    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
