package app.classmanagementappclient.Medium.AssessmentRecord.Insert;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessmentRecord.InsertAssessmentRecord;
import app.classmanagementappclient.Backend.Objects.AssessmentRecord.AssessmentRecord;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertAssessmentRecordMedium {
 

    public boolean insertAssessmentRecord (AssessmentRecordMedium medium, AssessmentRecord assessmentRecord) {
        
        InsertAssessmentRecord insertAssessment = null;
        boolean res = false;
        try (Connection connection = MediumUtility.getConnection()) {
            
            insertAssessment = new InsertAssessmentRecord(connection, assessmentRecord);
            res = insertAssessment.insert();
            
        } catch (SQLException ex) {
            System.getLogger(InsertAssessmentRecordMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            medium.setMsg(insertAssessment.getErrorMsg());
        
        return res;
    }    
    
}
