package app.classmanagementappclient.Medium.AttendanceRecord;

import app.classmanagementappclient.Medium.AttendanceRecord.Delete.RecordedAttendanceDeleteMedium;
import app.classmanagementappclient.Medium.AttendanceRecord.Insert.InsertAttendanceRecordMedium;
import app.classmanagementappclient.Medium.AttendanceRecord.Read.ReadRecordedAttendanceMedium;
import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceRecordMedium {
    
    private String msg;
    private final AttendanceRecord record;

    public AttendanceRecordMedium(AttendanceRecord record) {
        this.record = record;
    }
    
    public AttendanceRecordMedium() {
        this(null);
    }    
    
    public boolean insertAttendanceRecord () {
        boolean res = new InsertAttendanceRecordMedium().insertAttendanceRecordMedium(this, record);
        if (!res)
            showMsg();
        return res;
    }
    
    public List<List<String>> getRecordedStudentsOnSpecificAttendance (int attendanceNum) {
        return new ReadRecordedAttendanceMedium().getRecordedStudentsOnSpecificAttendance(attendanceNum);
    }

    public List<List<String>> getUnRecordedStudentsOnSpecificAttendance (int attendanceNum) {
        return new ReadRecordedAttendanceMedium().getUnRecordedStudentsOnSpecificAttendance(attendanceNum);
    }
    
    public List<List<String>> getAggregateAttendanceReport (List<Integer> attendanceNums, List<Integer> classTermNums) {
        return new ReadRecordedAttendanceMedium().getAggregateAttendanceReport(attendanceNums, classTermNums);
    }
    
    public boolean deleteAttendanceRecord (int attendanceNum, int studentClassNum) {
        
        boolean res = new RecordedAttendanceDeleteMedium().attendanceRecordDelete(this, attendanceNum, studentClassNum);
        if (!res)
            showMsg();
        return res;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    private void showMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}
