package app.classmanagementappclient.Medium.AssessmentRecord.Update;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Backend.Database.Update.AssessmentRecord.AssessmentRecordUpdate;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class UpdateAssessmentRecordMedium {
    
       public boolean updateAssessmentRecord (AssessmentRecordMedium medium, int attendanceNum,int studentClassNum, double currStudentMarks, double newStudentMarks) {
        
        boolean res = false;
        AssessmentRecordUpdate update = null;
        
        try {
            
            update = new AssessmentRecordUpdate(attendanceNum, studentClassNum, currStudentMarks, newStudentMarks);
            res = update.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(UpdateAssessmentRecordMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res) 
            medium.setMsg(update.getErrorMsg());
        
        return res;
    }
}
