package app.classmanagementappclient.Medium.School.InsertSchool.Proxies;

import app.classmanagementappclient.Medium.Interfaces.Schools.InsertSchoolMediumInterface;
import app.classmanagementappclient.Medium.School.InsertSchool.InsertSchoolMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;

/**
 *
 * @author BEN
 */
public class InsertSchoolProxy implements InsertSchoolMediumInterface {
    
    @Override
    public boolean insertSchool(SchoolMedium schoolMedium)
    {
        boolean res = schoolMedium.validateSchool();
        
        if (!res) {
            schoolMedium.showErrMsg();
            return false;
        }
        
        InsertSchoolMedium insertSchool = new InsertSchoolMedium();
        res = insertSchool.insertSchool(schoolMedium,schoolMedium.getSchool());
        
        if (!res)
            schoolMedium.showErrMsg();
        return res;
    }    
}
