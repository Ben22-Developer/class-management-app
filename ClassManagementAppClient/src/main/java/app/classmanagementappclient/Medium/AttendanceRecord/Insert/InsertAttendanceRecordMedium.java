package app.classmanagementappclient.Medium.AttendanceRecord.Insert;

import app.classmanagementappclient.Medium.AttendanceRecord.AttendanceRecordMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendanceRecord.InsertAttendanceRecord;
import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertAttendanceRecordMedium {
    
    public boolean insertAttendanceRecordMedium (AttendanceRecordMedium medium, AttendanceRecord record) {
        
        boolean res = false;
        InsertAttendanceRecord insertAttendanceRecord = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertAttendanceRecord = new InsertAttendanceRecord(connection, record);
            res = insertAttendanceRecord.insert();
        } catch (SQLException ex) {
            System.getLogger(InsertAttendanceRecordMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            medium.setMsg( insertAttendanceRecord.getErrorMsg() );
        return res;
    }
}
