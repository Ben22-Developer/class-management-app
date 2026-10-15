package app.classmanagementappclient.Backend.Database.Read.ReadAggregates.ReadAggregateAttendance;

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
public class ReadAggregateAttendanceProxy implements ReadStringOfStringInterface {
    
    private String clmName = "clm";
    private int clmNbr = 1;
    private final String subQueryName = "clms";
    private final List<Integer> attendanceNums;
    private final List<Integer> classTermNums;
    private final String[] clmNames;
    private final List<List<String>> data = new ArrayList<>();
    private final UtilityClass uc = new UtilityClass();
    
    public ReadAggregateAttendanceProxy(List<Integer> attendanceNums, List<Integer> classTermNums) {
        this.attendanceNums = attendanceNums;
        this.classTermNums = classTermNums;
        this.clmNames = new String[attendanceNums.size()];
    }
    

    @Override
    public String getQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryFormat(queryBuilder);
        
        System.out.println(queryBuilder.toString());
        
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
        
        queryBuilder.append("SELECT").append(" ").append(subQueryName).append(".*,").append(" ").append("((");
        int attendanceSize = attendanceNums.size();
        int i = 0;
        
        while (i < attendanceSize) {
            
            String currClmName = clmName + clmNbr;
            clmNames[i] = currClmName;
            
            if (i == attendanceSize - 1)
                queryBuilder.append(currClmName).append(")/").append(clmNbr).append(") * 100 FROM (");
            else
                queryBuilder.append(currClmName).append("+");
            i += 1;
            clmNbr += 1;
        }
        queryBuilder.append("\n");
    }
    
    private void selectClms (StringBuilder queryBuilder) {
        
        queryBuilder.append("SELECT ROW_NUMBER() OVER (ORDER BY studentpersonaldata.studentName) AS No,\n").append("studentpersonaldata.studentID, studentpersonaldata.studentName,\n");
        
        for (int i = 0; i < clmNames.length; i++)
            if (i == clmNames.length - 1)
                queryBuilder.append("MAX( ").append(" CASE WHEN studentattendancerecord.attendanceNum = ").append(attendanceNums.get(i)).append(" THEN 1 ELSE 0 END ) AS ").append(clmNames[i]).append("\n");
            else
                queryBuilder.append("MAX( ").append(" CASE WHEN studentattendancerecord.attendanceNum = ").append(attendanceNums.get(i)).append(" THEN 1 ELSE 0 END ) AS ").append(clmNames[i]).append(",\n");
        
        queryBuilder.append("FROM studentpersonaldata\n");
    }
    
    private void joinSection (StringBuilder queryBuilder) {
        
        queryBuilder.append("JOIN studentclassdata ON studentclassdata.studentNum = studentpersonaldata.studentNum\n").append("LEFT JOIN studentattendancerecord ON studentattendancerecord.StudentClassNum = studentclassdata.StudentClassNum\n").append("AND studentattendancerecord.attendanceNum IN (");
        
        for (int i = 0; i < attendanceNums.size(); i++)
            if (i == attendanceNums.size() - 1)
                queryBuilder.append(attendanceNums.get(i)).append(")");
            else
                queryBuilder.append(attendanceNums.get(i)).append(",");
        
        queryBuilder.append("\n");
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
        queryBuilder.append("GROUP BY studentpersonaldata.studentID, studentpersonaldata.studentName\n").append(") AS ").append(subQueryName);
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
