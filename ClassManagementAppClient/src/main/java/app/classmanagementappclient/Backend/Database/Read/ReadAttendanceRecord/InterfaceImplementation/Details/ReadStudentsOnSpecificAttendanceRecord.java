package app.classmanagementappclient.Backend.Database.Read.ReadAttendanceRecord.InterfaceImplementation.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadStudentsOnSpecificAttendanceRecord implements ReadStringOfStringInterface {

    private final int attendanceNum;
    private final List<List<String>> data = new ArrayList<>();

    public ReadStudentsOnSpecificAttendanceRecord (int attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
                SELECT ROW_NUMBER() OVER(ORDER BY studentPersonalData.studentName), studentPersonalData.studentID, studentPersonalData.studentName FROM studentPersonalData
                JOIN studentClassData ON studentClassData.studentNum = studentPersonalData.studentNum
                JOIN StudentAttendanceRecordData ON StudentAttendanceRecordData.studentClassNum = studentClassData.studentClassNum
                WHERE StudentAttendanceRecordData.attendanceNum = ? ORDER BY studentPersonalData.studentName
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, attendanceNum, 1);
    }
    
}
