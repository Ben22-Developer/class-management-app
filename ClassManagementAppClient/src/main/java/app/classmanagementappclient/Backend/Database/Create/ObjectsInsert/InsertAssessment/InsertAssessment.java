package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessment;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertAssessment extends ObjectsInsert 
{
    private final Assessment assessment;
    
    public InsertAssessment(Connection connection, Object object) throws SQLException {
        super(connection, object);
        assessment = (Assessment) object;
    }

    @Override
    public boolean insert() throws SQLException {        
        boolean res = isAssessmentInserted();
        if (res) {
            setErrorMsg();
            return false;
        }
        
        String query= """
                        INSERT INTO assessmentData (LessonNum, AssessmentName, AssessmentDate, TotalMarks) VALUES (?,?,?,?);
                      """ ;
        Connection connection = super.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            InsertDatabaseManipulation.executePrepareStatement(statement, assessment.toString());
        }
        catch(SQLIntegrityConstraintViolationException e) {
            setErrorMsg();
            return false;
        }
       return true;
    }
    
    private void setErrorMsg () throws SQLException {
       Connection connection = super.getConnection(); 
       UtilityClass uc = super.getUc();
        
        String lessonName = uc.getLessonName(connection, assessment.getLessonNum());
        java.time.LocalDate date = assessment.getAssessmentDate().toLocalDate();
        String dateStr = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        super.setErrorMsg("Duplicate entry for " +lessonName + "\nAttendance type: " + assessment.getAssessmentName() + "\nAttendance date: " + dateStr);
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setIntStatement(statement, assessment.getLessonNum(), 1);
        index = uc.setStringStatement(statement, assessment.getAssessmentName(), index);
        index = uc.setDateStatement(statement, assessment.getAssessmentDate(), index);
        uc.setIntStatement(statement, assessment.getTotalMarks(), index);
    }
    
    private boolean isAssessmentInserted () throws SQLException {
        UtilityClass uc = super.getUc();
        return uc.getAssessment(super.getConnection(), assessment) != 0;
    }
}
