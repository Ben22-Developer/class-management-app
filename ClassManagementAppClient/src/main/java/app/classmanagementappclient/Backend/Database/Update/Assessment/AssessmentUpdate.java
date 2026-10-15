package app.classmanagementappclient.Backend.Database.Update.Assessment;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author BEN
 */
public class AssessmentUpdate extends UpdateData {

    private final Assessment currAssessmentDetails;
    private final Assessment newAssessmentDetails;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();
    private final LinkedHashMap <String, String> dataToDataType = new LinkedHashMap<>();

    public AssessmentUpdate(Assessment currAssessmentDetails, Assessment newAssessmentDetails) throws SQLException {
        this.currAssessmentDetails = currAssessmentDetails;
        this.newAssessmentDetails = newAssessmentDetails;
    }    
    
    public boolean updateDetails () {
        return updateData("AssessmentData");
    }    
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        String query = """
                        SELECT AssessmentNum FROM AssessmentData WHERE lessonNum = ? AND assessmentName = ? AND assessmentDate = ? AND totalMarks = ?
                       """;
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            setStatement(statement, newAssessmentDetails.getLessonNum(), newAssessmentDetails.getAssessmentName(), newAssessmentDetails.getAssessmentDate(), newAssessmentDetails.getTotalMarks());
            return uc.getStatementIntResult(statement, 1) != Integer.MIN_VALUE;
        }        
    }

    @Override
    public void duplicateCloser() {
        super.setErrorMsg("Duplicates\nAssessment details for this lesson u're using are already there!\nDouble check the details");
    }

    @Override
    public void setDetailsToUpdate() {
        assessmentNameCheck();
        assessmentDateCheck();
        totalMarksCheck();
    }
    
    private void assessmentNameCheck () {
        if (currAssessmentDetails.getAssessmentName().equals(newAssessmentDetails.getAssessmentName()))
            return;
        
        setDSA("AssessmentName",newAssessmentDetails.getAssessmentName(), "String"); 
    }
    
    private void assessmentDateCheck () {
        if (currAssessmentDetails.getAssessmentDate().equals(newAssessmentDetails.getAssessmentDate()))
            return;
        
        setDSA("AssessmentDate", newAssessmentDetails.getAssessmentDate().toLocalDate().toString(), "LocalDate"); 
    }    

    private void totalMarksCheck () {
        if (currAssessmentDetails.getTotalMarks() == newAssessmentDetails.getTotalMarks())
            return;
    
        setDSA("TotalMarks", String.valueOf(newAssessmentDetails.getTotalMarks()), "Integer");     
    }
    
    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        query = queryComplete(query);
        UtilityClass uc = super.getUc();
        var udu = super.getUdu();
        
        System.out.println(dataToDataType);
        System.out.println(query);
        
        //UPDATE StudentAssessmentRecordData SET studentMarks = 14.5 WHERE studentClassNum = 525 AND assessmentNum = 4872
        
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            udu.setComplexStatements(statement, dataToDataType, uc, 1);
            return uc.executeDMLStament(statement);
        }
    }
    
    private String queryComplete (String query) {
        setDSA("AssessmentNum", String.valueOf(currAssessmentDetails.getAssessmentNum()), "Integer");
        return query + " WHERE assessmentNum = ?";
    }
    
    private void setDSA (String toUpdateClm, String data, String dataType) {
        toUpdateClms.add(toUpdateClm);
        dataToDataType.put(data, dataType);
    }    
    
    private void setStatement (PreparedStatement statement, int lessonNum, String assessmentName, Date assessmentDate, int totalMarks) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setIntStatement(statement, lessonNum, 1);
        index = uc.setStringStatement(statement, assessmentName, index);
        index = uc.setIntStatement(statement, totalMarks, index);
        uc.setDateStatement(statement, assessmentDate, index);
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}