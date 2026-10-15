package app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Details;

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
public class ReadTermsAssessmentDetailsProxy implements ReadStringOfStringInterface {
    
    private final List<Integer> classTermNums;
    private final List<List<String>> assessmentDetails = new ArrayList<>();

    public ReadTermsAssessmentDetailsProxy (List<Integer> classTermNums) {
        this.classTermNums = classTermNums;
    }

    @Override
    public List<List<String>> getData() {
        return assessmentDetails;
    }

    @Override
    public String getQuery() {
        String query = """
                    SELECT 
                    ROW_NUMBER() OVER (ORDER BY ClassTermData.Term, ClassLessonData.lessonName, assessmentData.assessmentName, assessmentData.assessmentDate, assessmentData.totalMarks),
                    ClassTermData.Term, classLessonData.lessonName, 
                    assessmentData.assessmentName, assessmentData.assessmentDate, assessmentData.totalMarks,assessmentData.assessmentNum
                    FROM assessmentdata
                    JOIN ClassLessonData ON ClassLessonData.classLessonNum = assessmentData.LessonNum 
                    JOIN ClassTermData ON ClassTermData.classTermNum = ClassLessonData.classTermNum
                    WHERE ClassTermData.classTermNum IN (
                """;
        return new UtilityClass().getWildCardsFormattedQuery(query, classTermNums.size(), "");
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, assessmentDetails);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, classTermNums.toArray(), 1);
    }
}
