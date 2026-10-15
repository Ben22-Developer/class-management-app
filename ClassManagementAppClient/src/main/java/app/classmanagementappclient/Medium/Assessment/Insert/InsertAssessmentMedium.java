package app.classmanagementappclient.Medium.Assessment.Insert;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessment.InsertAssessment;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertAssessmentMedium {

    public boolean insertAssessmentMedium (AssessmentMedium medium, Assessment assessment) {
        
        boolean res = false;
        InsertAssessment insertAssessment = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertAssessment = new InsertAssessment(connection, assessment);
            res = insertAssessment.insert();
        } catch (SQLException ex) {
            System.getLogger(InsertAssessmentMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            medium.setMsg( insertAssessment.getErrorMsg() );
        return res;
    }
    
}
