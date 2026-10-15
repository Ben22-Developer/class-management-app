package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendance;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;
import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertAttendance extends ObjectsInsert
{
    private final Attendance attendance;
    private final String viewName = "attendanceData";
    private int attendanceNum = Integer.MIN_VALUE;
    
    public InsertAttendance(Connection connection, Object object) throws SQLException {
        super(connection, object);
        attendance = (Attendance) object;
    }

    @Override
    public boolean insert() throws SQLException 
    {
        UtilityClass uc = super.getUc();
        int i = 0;
        while (i < 5) {
            try {
                unsafeTransaction();
                break;
            }
            catch (MySQLTransactionRollbackException | SQLIntegrityConstraintViolationException ex) {
                uc.threadSleep();
            }
            i ++;
        }
        
        if (i >= 5) {
            super.setErrorMsg("Insertion failed\n1.The lesson you are trying to access may not be there!\n2.The attendance you're trying to record is already there!\nRecover steps:\n1.Do a quick refresh to view if the lesson hasn't been deleted\n2.Use 'Help' menu item for quick recover\n3.If the error persists contact the developer");
            return false;
        }
            
        return true;
    }
    
    private void unsafeTransaction () throws SQLException  
    {
        startTransaction();
        setGapLock();
        executeInsert();
        commit();
    }
    
    private void startTransaction () throws SQLException {
        UtilityClass uc = super.getUc();
        String query = "START TRANSACTION";   
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        uc.executeStatement(statement);
    }

    private void setGapLock () throws SQLException {
        UtilityClass uc = super.getUc();
        String query = "SELECT attendanceNum FROM attendanceData WHERE attendanceNum > (SELECT attendanceNum FROM attendanceData ORDER BY attendanceNum DESC LIMIT 1) FOR UPDATE";
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        uc.executeStatement(statement);
    }

    private void executeInsert () throws SQLException {
        String query = "INSERT INTO "+viewName+"(lessonNum, attendanceDate) VALUES(?,?)";
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        setStatement(statement);
        InsertDatabaseManipulation.executePrepareStatement(statement, attendance.toString());
        setAttendanceNums();
    }    

    private void commit () throws SQLException {
        UtilityClass uc = super.getUc();
        String query = "COMMIT";
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        uc.executeStatement(statement);
    }
    
    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setIntStatement(statement, attendance.getLessonNum(),1);
        uc.setDateStatement(statement, attendance.getDate(), index);
    }
    
    private void setAttendanceNums() throws SQLException {
        UtilityClass uc = super.getUc();
        attendanceNum = uc.getLastlyInsertedAttendanceNum(super.getConnection(), attendance);
    }

    public int getAttendanceNum() {
        return attendanceNum;
    }
}
