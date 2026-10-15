package app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadObjectOfObjectInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAllTermAttendanceDetailsProxy implements ReadObjectOfObjectInterface{
    
    private final int classTermNum;
    private final List<List<Object>> attendances = new ArrayList<>();

    public ReadAllTermAttendanceDetailsProxy (int classTermNum) {
        this.classTermNum = classTermNum;
    }

    @Override
    public List<List<Object>> getData() {
        return attendances;
    }

    @Override
    public String getQuery() {
        return """
                    SELECT  ClassLessonData.classLessonNum, classLessonData.lessonName, AttendanceData.attendanceDate, AttendanceData.recordedAt 
                    FROM AttendanceData
                    RIGHT JOIN ClassLessonData ON ClassLessonData.classLessonNum = AttendanceData.LessonNum 
                    JOIN ClassTermData ON ClassTermData.classTermNum = ClassLessonData.classTermNum
                    WHERE ClassTermData.classTermNum = ?
                    ORDER BY ClassLessonData.lessonName, AttendanceData.attendanceDate, AttendanceData.recordedAt;
                """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getObjectsStatementByRowRes(statement, attendances);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, classTermNum, 1);
    }
}
