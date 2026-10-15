package app.classmanagementappclient.Medium.Attendance.AttendanceRead;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Details.*;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Nums.ReadAttendanceNum;
import app.classmanagementappclient.Backend.Database.Read.ReadAttendance.ReadAttendanceData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAttendanceMedium {    
    
    public int getAttendanceNum (int lessonNum, java.sql.Date date, Timestamp timestamp) {
        
        ReadAttendanceNum proxy = new ReadAttendanceNum(lessonNum, date, timestamp);
        executeReadOperation(proxy);
        return proxy.getData().get(0);
        
    }
    
    public List<List<Object>> getAllAttendancesInTerm (int classTermNum) {
        
        ReadAllTermAttendanceDetailsProxy proxy = new ReadAllTermAttendanceDetailsProxy(classTermNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    public List<List<Object>> getLessonAttendances (int lessonNum) {
        
        ReadLessonAttendancesObjectsProxy proxy = new ReadLessonAttendancesObjectsProxy(lessonNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    public List<Object> getAttendanceDetails (int attendanceNum) {
        
        ReadOneAttendanceDetails proxy = new ReadOneAttendanceDetails(attendanceNum);
        executeReadOperation(proxy);
        return proxy.getData();
    } 
    
    public List<List<String>> getStringsAttendanceDetails (int lessonNum) {
        
        var proxy = new ReadLessonAttendancesStringsProxy(lessonNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }   
    
    public List<List<String>> getStringsAttendanceDetails (List<Integer> attendanceNums) {
        
        var proxy = new ReadLessonAttendancesStringsProxy1(attendanceNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }   
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            var attendanceData = new ReadAttendanceData(proxy);
            attendanceData.setConnection(connection);
            attendanceData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(ReadAttendanceMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }  
}
