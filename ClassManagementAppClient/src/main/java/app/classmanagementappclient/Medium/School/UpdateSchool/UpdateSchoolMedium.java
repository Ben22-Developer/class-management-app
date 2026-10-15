package app.classmanagementappclient.Medium.School.UpdateSchool;

import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Backend.Database.Update.SchoolUpdate.SchoolDataUpdate;
import app.classmanagementappclient.Backend.Objects.School.School;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class UpdateSchoolMedium {
    
    public boolean updateSchoolDetails (SchoolMedium schoolMedium, School currSchoolDetails, School newSchoolDetails) {
        SchoolDataUpdate schoolDetailsUpdate = null;
        boolean res = false;
        try {
            schoolDetailsUpdate = new SchoolDataUpdate(currSchoolDetails, newSchoolDetails);
            res = schoolDetailsUpdate.updateDetails();
        } 
        catch (SQLException ex) {
            System.getLogger(UpdateSchoolMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            schoolMedium.setMsg(schoolDetailsUpdate.getErrorMsg());
        
        return res;
    }
}
