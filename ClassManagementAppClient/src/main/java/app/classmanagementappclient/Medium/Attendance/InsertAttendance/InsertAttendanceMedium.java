package app.classmanagementappclient.Medium.Attendance.InsertAttendance;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendance.InsertAttendance;
import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertAttendanceMedium {

    public int insertAttendance (AttendanceMedium medium, Attendance attendance) {
        
        InsertAttendance insertAttendance = null;
        int res = Integer.MIN_VALUE;
        try (Connection connection = MediumUtility.getConnection()) {
            insertAttendance = new InsertAttendance(connection, attendance);
            insertAttendance.insert();
            res = insertAttendance.getAttendanceNum();
        } catch (SQLException ex) {
            System.getLogger(InsertAttendanceMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (res == Integer.MIN_VALUE)
            medium.setMsg(insertAttendance.getErrorMsg());
        
        return res;
    }
}
