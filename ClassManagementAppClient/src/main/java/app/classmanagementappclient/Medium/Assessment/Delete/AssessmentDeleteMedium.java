package app.classmanagementappclient.Medium.Assessment.Delete;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Backend.Database.Delete.Assessment.LessonAssessmentDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class AssessmentDeleteMedium {


    public boolean deleteLessonAssessment (AssessmentMedium assessmentMedium, int classLessonNum) {
        
        LessonAssessmentDelete assessmentDelete = null;
        boolean res = false;
        
        try {
            assessmentDelete = new LessonAssessmentDelete(classLessonNum);
            res = assessmentDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(AssessmentDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
