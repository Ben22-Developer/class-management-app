package app.classmanagementappclient.Backend.Database.Delete.Attendance;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class LessonAttendanceDelete extends DeleteData 
{
    private final int attendanceNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public LessonAttendanceDelete (int attendanceNum) throws SQLException {
        this.attendanceNum = attendanceNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("attendanceData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("AttendanceNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, attendanceNum, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
