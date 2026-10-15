package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendanceRecord;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;
import app.classmanagementappclient.Backend.Objects.Student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertAttendanceRecord extends ObjectsInsert
{
    private final AttendanceRecord attendanceRecord;
    private final String viewName = "studentAttendanceRecordData";

    public InsertAttendanceRecord(Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.attendanceRecord = (AttendanceRecord) object;
    }

    @Override
    public boolean insert() throws SQLException 
    {
        if (isAttendanceRecorded()) {
            setErrorMsg();
            return false;
        }
        
        String query = "INSERT INTO "+viewName+"(attendanceNum, studentClassNum) VALUES(?,?)";
        try (PreparedStatement statement = super.getConnection().prepareStatement(query)){
            setStatement(statement);
            InsertDatabaseManipulation.executePrepareStatement (statement, attendanceRecord.toString());
        }catch (SQLIntegrityConstraintViolationException e) {
            setErrorMsg();
            return false;
        }
        return true;
    }
    
    private void setErrorMsg () throws SQLException 
    {
        UtilityClass uc = super.getUc();
        Connection connection = super.getConnection();
        
        int studentNum = uc.getStudentNumByStudentClassNum(connection, attendanceRecord.getStudentClassNum());
        Student student = uc.getStudentPersonalData(connection, studentNum);
        
        int lessonNum = uc.getLessonNumByAttendanceNum(connection, attendanceRecord.getAttendanceNum());
        String lessonName = uc.getLessonName(connection, lessonNum);
        
        super.setErrorMsg("The attendance is recorded for:\nStudentID: " + student.getStudentID() + "\nStudentName: " + student.getStudentName() 
                          +"\nLesson: "+lessonName);
    }
    
    private boolean isAttendanceRecorded () throws SQLException {
        UtilityClass uc = super.getUc();
        Connection connection = super.getConnection();
        return uc.getAttendanceRecord(connection, attendanceRecord) != 0;
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { attendanceRecord.getAttendanceNum(), attendanceRecord.getStudentClassNum() }, 1);
    }
    
}
