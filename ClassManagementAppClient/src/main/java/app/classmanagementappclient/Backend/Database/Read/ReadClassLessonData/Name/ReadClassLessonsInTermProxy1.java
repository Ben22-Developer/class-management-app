package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Name;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassLessonsInTermProxy1 implements ReadStringInterface {

    private final int classTermNum;
    private final List<String> lessons = new ArrayList<>();

    
    public ReadClassLessonsInTermProxy1(int classTermNum) {
        this.classTermNum = classTermNum;
    }
    
    @Override
    public String getQuery() {
        return """
                SELECT lessonName FROM classLessonData WHERE classTermNum = ? ORDER BY lessonName
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByClmRes(statement, 1, lessons);
    }

    @Override
    public List<String> getData() {
        return lessons;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, classTermNum, 1);
    }
}
