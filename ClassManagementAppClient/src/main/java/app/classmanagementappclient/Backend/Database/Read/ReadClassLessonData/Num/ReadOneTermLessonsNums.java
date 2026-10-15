package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num;

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
public class ReadOneTermLessonsNums implements ReadObjectOfObjectInterface {
    
    private final int classTermNum;
    private final List<String> lessonNames;
    private final List<List<Object>> classLessonsNums_Names = new ArrayList<>();

    public ReadOneTermLessonsNums (int classTermNum, List<String> lessonNames) {
        this.classTermNum = classTermNum;
        this.lessonNames = lessonNames;
    }

    @Override
    public List<List<Object>> getData() {
        return classLessonsNums_Names;
    }

    @Override
    public String getQuery() {
        String query = 
                """
                    SELECT classLessonNum, lessonName FROM classLessonData WHERE classTermNum = ? AND lessonName IN (
                """;
        return new UtilityClass().getWildCardsFormattedQuery(query, lessonNames.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getObjectsStatementByRowRes(statement, classLessonsNums_Names);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, classTermNum, 1);
        uc.setStringsStatement(statement, lessonNames.toArray(), index);
    }    
}

