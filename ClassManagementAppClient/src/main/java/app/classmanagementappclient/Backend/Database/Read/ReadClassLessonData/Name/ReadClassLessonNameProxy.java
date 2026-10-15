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
public class ReadClassLessonNameProxy implements ReadStringInterface {

    private final int lessonNum;
    private final List<String> lessons = new ArrayList<>();

    
    public ReadClassLessonNameProxy (int classTermNum) {
        this.lessonNum = classTermNum;
    }
    
    @Override
    public String getQuery() {
        return """
                SELECT lessonName FROM classLessonData WHERE classLessonNum = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByClmRes(statement, 1, lessons);
        
        if (lessons.size() > 1)
            throw new RuntimeException("At ReadClassLessonNameProxy.readData lessonNum considered getting only 1 lesson name\nAnd it's not working as epected!");
    }

    @Override
    public List<String> getData() {
        return lessons;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, lessonNum, 1);
    }    
    
}
