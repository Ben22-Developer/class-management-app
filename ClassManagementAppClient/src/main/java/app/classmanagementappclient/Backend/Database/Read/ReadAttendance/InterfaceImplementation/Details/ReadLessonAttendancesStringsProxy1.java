package app.classmanagementappclient.Backend.Database.Read.ReadAttendance.InterfaceImplementation.Details;

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
public class ReadLessonAttendancesStringsProxy1 implements ReadStringOfStringInterface {
    
    private final List<Integer> lessonNums;
    private final List<List<String>> data = new ArrayList<>();

    public ReadLessonAttendancesStringsProxy1  (List<Integer> lessonNum) {
        this.lessonNums = lessonNum;
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        
        var uc =  new UtilityClass();
        String query = """
                SELECT classLessonData.lessonName, AttendanceData.attendanceDate, AttendanceData.recordedAt 
                FROM AttendanceData
                JOIN ClassLessonData ON ClassLessonData.classLessonNum = AttendanceData.LessonNum 
                JOIN ClassTermData ON ClassTermData.classTermNum = ClassLessonData.classTermNum
                WHERE AttendanceData.attendanceNum IN ( 
               """;
        query =  uc.getWildCardsFormattedQuery(query, lessonNums.size(), "");
        String orderBy = """
                            ORDER BY field(AttendanceData.attendanceNum,
                         """;
        orderBy = uc.getWildCardsFormattedQuery(orderBy, lessonNums.size(), "");
        return query + orderBy;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntsStatement(statement, lessonNums.toArray(), 1);
         uc.setIntsStatement(statement, lessonNums.toArray(), index);
    }      
    
}
