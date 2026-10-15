package app.classmanagementappclient.Medium.Assessment;

import app.classmanagementappclient.Medium.Assessment.Delete.AssessmentDeleteMedium;
import app.classmanagementappclient.Medium.Assessment.Insert.InsertAssessmentMedium;
import app.classmanagementappclient.Medium.Assessment.Read.ReadAssessmentMedium;
import app.classmanagementappclient.Medium.Assessment.Update.AssessmentUpdateMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentMedium {

    private StringBuilder msg = new StringBuilder();
    
    public boolean insertAssessment (int lessonNum, String assessmentName, Date assessmentDate, String totalMarks) {
        
        boolean res = validate(assessmentName, totalMarks);
        if (!res) {
            showMsg();
            return false;
        }
        
       Assessment assessment = new Assessment(lessonNum, assessmentName, assessmentDate, Integer.parseInt(totalMarks));
       res = new InsertAssessmentMedium().insertAssessmentMedium(this, assessment);
       
        if (!res)
            showMsg();
        return res;
    }
    
    public boolean updateAssessment (int lessonNum, String assessmentName, Date assessmentDate, String totalMarks, Assessment currAssessment) {
        
        if (!validate(assessmentName, totalMarks)) {
            showMsg();
            return false;
        }
        
        Assessment newAssessment = new Assessment(lessonNum, assessmentName, assessmentDate, Integer.parseInt(totalMarks));
        boolean res = new AssessmentUpdateMedium().updateAssessmentMediumData(this, currAssessment, newAssessment);
        
        if (!res)
            showMsg();
        return res;
    }
    
    public int getAssessmentNum (int lessonNum, String lessonName, java.sql.Date date) {
        return new ReadAssessmentMedium().getAssessmentNum(lessonNum, lessonName, date);
    }

    public int getAssessmentNums (String schoolName, String className, int year, int term, String lessonName, String assessmentName, Date date) {
        return new ReadAssessmentMedium().getAssessmentNums(schoolName, className, year, term, lessonName, assessmentName, date);
    }
    
    public List<List<Object>> getAllAssessmentsInTerm (int classTermNum) {
        return new ReadAssessmentMedium().getAllAssessmentsInTerm(classTermNum);
    }
    
    public List<List<Object>> getAllClassAssessments (int lessonNum) {
        return new ReadAssessmentMedium().getAllClassAssessments(lessonNum);
    } 
    
    public List<List<String>> getAssessmentsInTerms (List<Integer> classTermNums) {
        return new ReadAssessmentMedium().getAssessmentsInTerms(classTermNums);
    }
    
    public List<List<String>> getReportRow (List<List<Integer>> assessmentNums, List<Integer> classTermNums) {
        return new ReadAssessmentMedium().getReportRow(assessmentNums, classTermNums);
    }
    
    public boolean deleteLessonAssessment (int classLessonNum) {
        
        boolean res = new AssessmentDeleteMedium().deleteLessonAssessment(this, classLessonNum);
        if (!res)
            showMsg();
        return res;
    }
    
    private boolean validate (String assessmentName, String totalMarks) {
        
        Validation validation = new Validation();
        boolean aggregate = true;
        boolean res = validation.validateTotalMarks(totalMarks);
        if (!res) {
            setMsg(validation.getErrorMsg());
            aggregate = false;
        }
        
        res = validation.isAssessmentNameValid(assessmentName);
        if (!res) {
            setMsg(validation.getErrorMsg());
            aggregate = false;
        }        
        return aggregate;
    }
    
    public void setMsg(String msg) {
        this.msg.append(msg).append("\n");
    }

    public String getMsg() {
        return msg.toString();
    }
    
    public void showMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }       
}
