package app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Details;

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
public class ReadAllTermAssessmentDetailsProxy implements ReadObjectOfObjectInterface {
    
    private final int classTermNum;
    private final List<List<Object>> assessments = new ArrayList<>();

    public ReadAllTermAssessmentDetailsProxy (int classTermNum) {
        this.classTermNum = classTermNum;
    }

    @Override
    public List<List<Object>> getData() {
        return assessments;
    }

    @Override
    public String getQuery() {
        return """
                    SELECT ClassLessonData.classLessonNum, classLessonData.lessonName, 
                    assessmentData.assessmentName, assessmentData.assessmentDate, assessmentData.totalMarks
                    FROM assessmentdata
                    RIGHT JOIN ClassLessonData ON ClassLessonData.classLessonNum = assessmentData.LessonNum 
                    JOIN ClassTermData ON ClassTermData.classTermNum = ClassLessonData.classTermNum
                    WHERE ClassTermData.classTermNum = ?
                    ORDER BY ClassLessonData.lessonName,assessmentData.assessmentName, assessmentData.assessmentDate, assessmentData.totalMarks
                """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getObjectsStatementByRowRes(statement, assessments);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, classTermNum, 1);
    }
}
