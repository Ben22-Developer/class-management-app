package app.classmanagementappclient.Medium.AssessmentRecord.Delete;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Backend.Database.Delete.AssessmentRecord.AssessmentRecordDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class AssessmentRecordDeleteMedium {


    public boolean deleteLessonAssessment (AssessmentRecordMedium assessmentMedium, int assessmentNum, int studentClassNum) {
        
        AssessmentRecordDelete assessmentDelete = null;
        boolean res = false;
        
        try {
            assessmentDelete = new AssessmentRecordDelete(assessmentNum, studentClassNum);
            res = assessmentDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(AssessmentRecordDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res) {
            if (assessmentDelete.getErrorMsg().isBlank())
                assessmentMedium.setMsg("An error happened!\nTry again or contact the developer if it persistis");
            else 
                assessmentMedium.setMsg(assessmentDelete.getErrorMsg());
        }
        return res;
    }    
    
}
