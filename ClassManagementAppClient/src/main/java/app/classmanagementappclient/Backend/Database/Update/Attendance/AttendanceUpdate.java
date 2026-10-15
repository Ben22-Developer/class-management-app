package app.classmanagementappclient.Backend.Database.Update.Attendance;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class AttendanceUpdate extends UpdateData {

    private final int attendanceNum; 
    private final Date newDate;
    private final Date currDate;
    private final Timestamp recordedtAt;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public AttendanceUpdate(int attendanceNum, Date newDate, Date currDate, Timestamp recordedtAt) throws SQLException {
        this.attendanceNum = attendanceNum;
        this.newDate = newDate;
        this.currDate = currDate;
        this.recordedtAt = recordedtAt;
    }
    
    public boolean updateDetails () {
        return super.updateData("attendanceData");
    }    
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        int res = super.getUc().getAttendanceNum(super.getConnection(), newDate, recordedtAt);
        return res != Integer.MIN_VALUE;
    }

    @Override
    public void duplicateCloser() {
        super.setErrorMsg("Duplicates similar attendance error!\nDouble check the details");
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("attendanceDate");
    }

    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        query += " WHERE attendanceNum = ?";
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            int index = uc.setDateStatement(statement, newDate, 1);
            uc.setIntStatement(statement, attendanceNum, index);
            return uc.executeDMLStament(statement);
        }
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }   
}
