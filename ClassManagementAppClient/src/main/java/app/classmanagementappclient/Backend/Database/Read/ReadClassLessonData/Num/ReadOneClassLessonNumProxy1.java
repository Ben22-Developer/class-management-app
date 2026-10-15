package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadIntsInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadOneClassLessonNumProxy1 implements ReadIntsInterface {
    
    private final int classTermNum;
    private final String lessonName;
    private final List<Integer> classLessonNum = new ArrayList<>();

    public ReadOneClassLessonNumProxy1 (int classTermNum, String lessonName) {
        this.classTermNum = classTermNum;
        this.lessonName = lessonName;
    }

    @Override
    public List<Integer> getData() {
        return classLessonNum;
    }

    @Override
    public String getQuery() {
        return """
                SELECT classLessonNum FROM classLessonData WHERE classTermNum = ? AND lessonName = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getOneClmIntsStatementResult(statement, classLessonNum, 1);
        
        if (classLessonNum.size() > 1)
            throw new RuntimeException("termNum & lesson determine 1 row of ClassLessonData which isn't the case here!");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setIntStatement(statement, classTermNum, 1);
        uc.setStringStatement(statement, lessonName, index);
    }    
}
