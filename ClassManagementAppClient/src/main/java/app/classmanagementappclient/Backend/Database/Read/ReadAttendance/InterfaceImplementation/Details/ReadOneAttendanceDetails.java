package app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadObjectInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadOneAttendanceDetails implements ReadObjectInterface {
    
    private final int attendanceNum;
    private final List<Object> data = new ArrayList<>();

    public ReadOneAttendanceDetails (int attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    @Override
    public List<Object> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
                SELECT attendanceDate, recordedAt FROM attendanceData WHERE attendanceNum = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        setStatement(statement, uc);
        uc.getOneRowObjectsStatementResults(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, attendanceNum, 1);
    }
}
