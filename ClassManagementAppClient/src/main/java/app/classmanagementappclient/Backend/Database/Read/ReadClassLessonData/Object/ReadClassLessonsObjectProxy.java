package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Object;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassLessonsObjectProxy implements ReadClassLessonObject {

    private final List<Integer> classTermNums;
    private final List<ClassLesson> classLessons = new ArrayList<>();

    public ReadClassLessonsObjectProxy(List<Integer> classTermNums) {
        this.classTermNums = classTermNums;
    }
    
    @Override
    public List<ClassLesson> getData() {
        return classLessons;
    }

    @Override
    public String getQuery() {
        String query = """
                            SELECT classLessonData.classLessonNum, classLessonData.lessonName, classLessonData.classTermNum
                            FROM classLessonData
                            JOIN classTermData ON classTermData.classTermNum = classLessonData.classTermNum
                            WHERE classTermData.classTermNum IN (                        
                       """;    
        return new UtilityClass().getWildCardsFormattedQuery(query, classTermNums.size(), "", " ORDER BY classLessonData.classTermNum, classLessonData.lessonName");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getClassLessonRowRes(statement, classLessons);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classTermNums.toArray(), 1);
    }
}
