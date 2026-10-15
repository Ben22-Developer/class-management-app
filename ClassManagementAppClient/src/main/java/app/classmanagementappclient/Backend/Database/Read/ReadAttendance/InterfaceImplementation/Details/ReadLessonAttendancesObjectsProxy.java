package app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadObjectOfObjectInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadLessonAttendancesObjectsProxy implements ReadObjectOfObjectInterface{
    
    private final int lessonNum;
    private final List<List<Object>> data = new ArrayList<>();

    public ReadLessonAttendancesObjectsProxy (int lessonNum) {
        this.lessonNum = lessonNum;
    }

    @Override
    public List<List<Object>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
                SELECT AttendanceData.attendanceDate, AttendanceData.recordedAt 
                FROM AttendanceData
                JOIN ClassLessonData ON ClassLessonData.classLessonNum = AttendanceData.LessonNum 
                WHERE AttendanceData.lessonNum = ?
                ORDER BY AttendanceData.attendanceDate, AttendanceData.recordedAt
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        setStatement(statement, uc);
        uc.getObjectsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, lessonNum, 1);
    }      
    
}
