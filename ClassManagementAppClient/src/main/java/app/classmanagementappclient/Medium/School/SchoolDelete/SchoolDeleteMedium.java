package app.classmanagementappclient.Medium.School.SchoolDelete;

import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Backend.Database.Delete.Schools.SchoolDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class SchoolDeleteMedium {
    
    public boolean deleteSchool (SchoolMedium schoolMedium, int schoolNum) 
    {    
        SchoolDelete schoolDelete = null;
        boolean res = false;
        
        try {
            schoolDelete = new SchoolDelete(schoolNum);
            res = schoolDelete.deleteData();
        } catch (SQLException ex) {
            System.getLogger(SchoolDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            schoolMedium.setMsg( schoolDelete.getErrorMsg() );
        
        return res;
    }
}
