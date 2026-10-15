package app.classmanagementappclient.Backend.Database.Update.AssessmentRecord;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */

public class AssessmentRecordUpdate extends UpdateData {

    private final int assessmentNum; 
    private final int studentClassNum;
    private final double currStudentMarks;
    private final double newStudentMarks;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public AssessmentRecordUpdate (int attendanceNum,int studentClassNum, double currStudentMarks, double newStudentMarks) throws SQLException {
        this.assessmentNum = attendanceNum;
        this.studentClassNum = studentClassNum;
        this.currStudentMarks = currStudentMarks;
        this.newStudentMarks = newStudentMarks;
    }
    
    public boolean updateDetails () {
        return updateData("StudentAssessmentRecordData");
    }    
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        String query = """
                        SELECT studentMarks FROM StudentAssessmentRecordData WHERE studentMarks = ? AND studentClassNum = ? AND assessmentNum = ?
                       """;
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            setStatement(statement, newStudentMarks, studentClassNum, assessmentNum);
            return currStudentMarks == uc.getStatementDoubleResult(statement, 1);
        }
        
    }

    @Override
    public void duplicateCloser() {
        super.setErrorMsg("Duplicates\nSimilar marks error!\nDouble check the details");
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("studentMarks");
    }

    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        query += " WHERE studentClassNum = ? AND assessmentNum = ?";
        
        UtilityClass uc = super.getUc();
        
        //UPDATE StudentAssessmentRecordData SET studentMarks = 14.5 WHERE studentClassNum = 525 AND assessmentNum = 4872
        
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            setStatement(statement, newStudentMarks, studentClassNum, assessmentNum);
            return uc.executeDMLStament(statement);
        }
    }
    
    private void setStatement (PreparedStatement statement, double newStudentMarks, int studentClassNum, int assessmentNum) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setDoubleStatement(statement, newStudentMarks, 1);
        uc.setIntsStatement(statement, new Integer[]{ studentClassNum, assessmentNum }, index);        
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}