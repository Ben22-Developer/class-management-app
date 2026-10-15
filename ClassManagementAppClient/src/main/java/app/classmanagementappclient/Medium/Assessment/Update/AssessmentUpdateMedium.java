package app.classmanagementappclient.Medium.Assessment.Update;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Backend.Database.Update.Assessment.AssessmentUpdate;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class AssessmentUpdateMedium {
    
    public boolean updateAssessmentMediumData (AssessmentMedium medium, Assessment currAssessmentDetails, Assessment newAssessmentDetails) {
        
        boolean res = false;
        AssessmentUpdate update = null;
        
        try {
            update = new AssessmentUpdate(currAssessmentDetails, newAssessmentDetails);
            res = update.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(AssessmentUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            medium.setMsg(update.getErrorMsg());
        
        return res;
    }
}
