package app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Nums;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAttendanceNum implements ReadIntsInterface {
    
    private final int lessonNum;
    private final java.sql.Date date;
    private final Timestamp timestamp;
    private final List<Integer> attendanceNum = new ArrayList<>();

    public ReadAttendanceNum(int lessonNum, java.sql.Date date, Timestamp timestamp) {
        this.lessonNum = lessonNum;
        this.date = date;
        this.timestamp = timestamp;
    }
    
    @Override
    public List<Integer> getData() {
        return attendanceNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT AttendanceNum FROM AttendanceData WHERE lessonNum = ? AND attendanceDate = ? AND recordedAt = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, attendanceNum, 1);
        
        if (attendanceNum.size() > 1)
            throw new RuntimeException("lessonNum & attendanceDate & recordedAt determine 1 row of AttendanceData which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, lessonNum, 1);
        index = uc.setDateStatement(statement, date, index);
        uc.setTimestampStatement(statement, timestamp, index);
    }  
    
}
