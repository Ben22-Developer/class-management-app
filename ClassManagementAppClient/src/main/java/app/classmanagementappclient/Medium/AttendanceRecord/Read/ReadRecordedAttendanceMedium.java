package app.classmanagementappclient.Medium.AttendanceRecord.Read;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadAggregates.ReadAggregateAttendance.ReadAggregateAttendanceProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendanceRecord.InterfaceImplementation.Details.ReadStudentsOnSpecificAttendanceRecord;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendanceRecord.InterfaceImplementation.Details.ReadUnRecordedStudents;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendanceRecord.ReadRecordedAttendanceData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadRecordedAttendanceMedium {
    
    public List<List<String>> getRecordedStudentsOnSpecificAttendance (int attendanceNum) {
        
        var proxy = new ReadStudentsOnSpecificAttendanceRecord(attendanceNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }    
    
    
    public List<List<String>> getUnRecordedStudentsOnSpecificAttendance (int attendanceNum) {
        
        var proxy = new ReadUnRecordedStudents(attendanceNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }    
    
    public List<List<String>> getAggregateAttendanceReport (List<Integer> attendanceNums, List<Integer> classTermNums) {
        var proxy = new ReadAggregateAttendanceProxy(attendanceNums, classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            var attendanceData = new ReadRecordedAttendanceData(proxy);
            attendanceData.setConnection(connection);
            attendanceData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(ReadRecordedAttendanceMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }  
}
