package app.classmanagementappclient.Medium.Attendance;

import app.classmanagementappclient.Medium.Attendance.AttendanceDelete.AttendanceDeleteMedium;
import app.classmanagementappclient.Medium.Attendance.AttendanceRead.ReadAttendanceMedium;
import app.classmanagementappclient.Medium.Attendance.AttendanceUpdate.AttendanceUpdateMedium;
import app.classmanagementappclient.Medium.Attendance.InsertAttendance.InsertAttendanceMedium;
import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceMedium {
    
    private final Attendance attendance;
    private String msg;

    public AttendanceMedium(Attendance attendance) {
        this.attendance = attendance;
    }
    
    public AttendanceMedium() {
        this(null);
    }    
    
    public int insertAttendance () {
        int attendanceNum = new InsertAttendanceMedium().insertAttendance(this, attendance);
        if (attendanceNum == Integer.MIN_VALUE)
            showMsg();
        return attendanceNum;
    }
    
    public List<List<Object>> getAllAttendanceDetailsInTerm (int classTermNum) {
        return new ReadAttendanceMedium().getAllAttendancesInTerm(classTermNum);
    }

    public List<Object> getAttendanceDetails (int attendanceNum) {
        return new ReadAttendanceMedium().getAttendanceDetails(attendanceNum);
    }    
    
    public List<List<Object>> getLessonAttendance (int lessonNum) {
        return new ReadAttendanceMedium().getLessonAttendances(lessonNum);
    }  
    
    public List<List<String>> getStringsAttendanceDetails (int lessonNum) {
        return new ReadAttendanceMedium().getStringsAttendanceDetails(lessonNum);
    }
    
    public List<List<String>> getStringsAttendanceDetails (List<Integer> attendanceNums) {
        return new ReadAttendanceMedium().getStringsAttendanceDetails(attendanceNums);
    }    
    
    public int getAttendanceNum (int lessonNum, java.sql.Date date, Timestamp timestamp) {
        return new ReadAttendanceMedium().getAttendanceNum(lessonNum, date, timestamp);
    }
    
    public boolean updateAttendance (int attendanceNum, java.sql.Date newDate, java.sql.Date currDate, Timestamp recordedtAt) {
        return new AttendanceUpdateMedium().updateAttendance(this, attendanceNum, newDate, currDate, recordedtAt);
    }
    
    public boolean deleteAttendanceMedium (int attendanceNum) {
        boolean res = new AttendanceDeleteMedium().deleteLessonAttendance(this, attendanceNum);
        if (!res)
            showMsg();
        return res;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
    private void showMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}
