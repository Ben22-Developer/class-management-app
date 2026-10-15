package app.classmanagementappclient.Backend.Database.Delete.AttendanceRecord;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class AttendanceRecordDelete extends DeleteData{

    private final int attendanceNum;
    private final int studentClassNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public AttendanceRecordDelete (int attendanceNum, int studentClassNum) throws SQLException {
        this.attendanceNum = attendanceNum;
        this.studentClassNum = studentClassNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("studentAttendanceRecordData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("AttendanceNum");
        clmNames.add("studentClassNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntsStatement(statement, new Integer[] {attendanceNum, studentClassNum}, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
    
}
