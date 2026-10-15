package app.classmanagementappclient.Medium.AssessmentRecord;

import app.classmanagementappclient.Medium.AssessmentRecord.Delete.AssessmentRecordDeleteMedium;
import app.classmanagementappclient.Medium.AssessmentRecord.Insert.InsertAssessmentRecordMedium;
import app.classmanagementappclient.Medium.AssessmentRecord.Read.ReadAssessmentRecordMedium;
import app.classmanagementappclient.Medium.AssessmentRecord.Update.UpdateAssessmentRecordMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.AssessmentRecord.AssessmentRecord;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentRecordMedium {
    
    private String msg;
    
    public boolean insertAssessmentRecord(int assessmentNum, int studentClassNum, String studentMarks) {
        
        boolean res = validate(studentMarks);
        if (!res) {
            showMsg();
            return false;
        }
        
        res = handleInsertTransact(assessmentNum, studentClassNum, studentMarks);
        
        if (!res)
            showMsg();
        
        return res;
    }
    
    public boolean insertAssessmentRecord_NoShowMsg (int assessmentNum, int studentClassNum, String studentMarks) {
        
        boolean res = validate(studentMarks);
        if (!res) 
            return false;
        
        res = handleInsertTransact(assessmentNum, studentClassNum, studentMarks);
        
        return res;
    }  
    
    private boolean handleInsertTransact (int assessmentNum, int studentClassNum, String studentMarks) {
        AssessmentRecord assessmentRecord = new AssessmentRecord(assessmentNum, studentClassNum, Double.parseDouble(studentMarks));
        return new InsertAssessmentRecordMedium().insertAssessmentRecord(this, assessmentRecord);        
    }
    
    public List<List<String>> getRecordedStudentsOnSpecificAssessment (int assessmentNum) {
        return new ReadAssessmentRecordMedium().getRecordedStudentsOnSpecificAssessment(assessmentNum);
    }
    
    public List<List<String>> getUnRecordedStudentsOnSpecificAssessment (int assessmentNum) {
        return new ReadAssessmentRecordMedium().getUnRecordedStudentsOnSpecificAssessment(assessmentNum);
    }
    
    public List<List<String>> getAllStudentAssessmentRecord () {
        return new ReadAssessmentRecordMedium().getAllStudentAssessmentRecord();
    }
    
    public boolean updateAssessmentRecord (int attendanceNum,int studentClassNum, String currStudentMarks, String newStudentMarks) {
        
        if (!validate(newStudentMarks)) {
            showMsg();
            return false;
        }
        
        boolean res = new UpdateAssessmentRecordMedium().updateAssessmentRecord(this, attendanceNum, studentClassNum, Double.parseDouble(currStudentMarks), Double.parseDouble(newStudentMarks));
        
        if (!res)
            showMsg();
        
        return res;
    }
    
    public boolean deleteLessonAssessment (int assessmentNum, int studentClassNum) {
        
        boolean res = new AssessmentRecordDeleteMedium().deleteLessonAssessment(this, assessmentNum, studentClassNum);
        
        if (!res)
            showMsg();
        
        return res;
    }
    
    private boolean validate (String studentMarks) {
        
        Validation validation = new Validation();
        boolean res = validation.validateStudentMarks(studentMarks);
        
        if (!res) {
            setMsg(validation.getErrorMsg());
            res = false;
        }   
        return res;
    }    

    public void showMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
