package app.classmanagementappclient.Medium.AttendanceRecord.Delete;

import app.classmanagementappclient.Medium.AttendanceRecord.AttendanceRecordMedium;
import app.classmanagementappclient.Backend.Database.Delete.AttendanceRecord.AttendanceRecordDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class RecordedAttendanceDeleteMedium {

    public boolean attendanceRecordDelete (AttendanceRecordMedium medium, int attendanceNum, int studentClassNum) {
        
        AttendanceRecordDelete recordDelete = null;
        boolean res = false;
        
        try {
            recordDelete = new AttendanceRecordDelete(attendanceNum,studentClassNum);
            res = recordDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(RecordedAttendanceDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res) {
            if (recordDelete.getErrorMsg().isBlank())
                medium.setMsg("An error happened!\nTry again or contact the developer if it persistis");
            else 
                medium.setMsg(recordDelete.getErrorMsg());
        }
        return res;    
    }    
}
