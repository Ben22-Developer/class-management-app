package app.classmanagementappclient.Medium.Attendance.AttendanceDelete;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Backend.Database.Delete.Attendance.LessonAttendanceDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class AttendanceDeleteMedium {

    public boolean deleteLessonAttendance (AttendanceMedium attendanceMedium, int classLessonNum) {
        
        LessonAttendanceDelete attendanceDelete = null;
        boolean res = false;
        
        try {
            attendanceDelete = new LessonAttendanceDelete(classLessonNum);
            res = attendanceDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(AttendanceDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res) {
            if (attendanceDelete.getErrorMsg().isBlank())
                attendanceMedium.setMsg("An error happened!\nTry again or contact the developer if it persistis");
            else 
                attendanceMedium.setMsg(attendanceDelete.getErrorMsg());
        }
        return res;
    }    
}
