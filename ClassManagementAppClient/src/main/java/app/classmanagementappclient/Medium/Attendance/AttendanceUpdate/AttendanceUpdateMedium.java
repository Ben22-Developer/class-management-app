package app.classmanagementappclient.Medium.Attendance.AttendanceUpdate;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Backend.Database.Update.Attendance.AttendanceUpdate;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author BEN
 */
public class AttendanceUpdateMedium {
    
    public boolean updateAttendance (AttendanceMedium medium, int attendanceNum, Date newDate, Date currDate, Timestamp recordedtAt) {
        
        boolean res = false;
        AttendanceUpdate update = null;
        
        try {
            
            update = new AttendanceUpdate(attendanceNum, newDate, currDate, recordedtAt);
            res = update.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(AttendanceUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res) 
            medium.setMsg(update.getErrorMsg());
        
        return res;
    }
}
