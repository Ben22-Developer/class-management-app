package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessmentRecord;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.AssessmentRecord.AssessmentRecord;
import app.classmanagementappclient.Backend.Objects.Student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertAssessmentRecord extends ObjectsInsert 
{
    private final AssessmentRecord assessmentRecord;
    
    public InsertAssessmentRecord(Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.assessmentRecord = (AssessmentRecord) object;
    }

    @Override
    public boolean insert() throws SQLException {
        
        if (isAssessmentRecordInserted()) {
            logErrorMsg();
            return false;
        }
        
        String query = "INSERT INTO studentAssessmentRecordData(AssessmentNum, StudentClassNum, studentMarks) VALUES(?,?,?)";
        try (PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            setStatement(statement);
            InsertDatabaseManipulation.executePrepareStatement(statement, assessmentRecord.toString());
        } catch (SQLIntegrityConstraintViolationException e) {
            logErrorMsg();
            return false;
        }
        return true;
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setIntsStatement(statement, new Integer[] { assessmentRecord.getAssessmentNum(), assessmentRecord.getStudentClassNum() }, 1);
        uc.setDoubleStatement(statement, assessmentRecord.getStudentMarks(), index);
    }
    
    
    private void logErrorMsg () throws SQLException 
    {
        UtilityClass uc = super.getUc();
        Connection connection = super.getConnection();
        
        int studentNum = uc.getStudentNumByStudentClassNum(connection, assessmentRecord.getStudentClassNum());
        Student student = uc.getStudentPersonalData(connection, studentNum);
        
        List<Object> assessmentDetails = new ArrayList<>();
        uc.getAssessmentDetails(connection, assessmentRecord.getAssessmentNum(), assessmentDetails);
        
        int lessonNum = (int) assessmentDetails.get(0);
        String assessmentName = String.valueOf( assessmentDetails.get(1) );
        java.sql.Date sqlDate = (java.sql.Date) assessmentDetails.get(2);
        java.time.LocalDate date = sqlDate.toLocalDate();
        int totalMarks = (int) assessmentDetails.get(3);
        
        String lessonName = uc.getLessonName(connection, lessonNum);
        
        super.setErrorMsg("This assessment is recorded for student\nStudentID: " + student.getStudentID() + "\nStudentName: " + student.getStudentName() +"\nLesson: "+lessonName + "(" + assessmentName +")" +
                "\nDate: " + date.getDayOfMonth() +"/" + date.getMonthValue() + "/" + date.getYear() + "\nTotal Marks: " + totalMarks);
    }
    
    private boolean isAssessmentRecordInserted () throws SQLException {
        UtilityClass uc = super.getUc();
        return uc.getAssessmentRecord(super.getConnection(), assessmentRecord) != 0;
    }
}
