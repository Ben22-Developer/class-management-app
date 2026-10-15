package app.classmanagementappclient.Medium.School.InsertSchool.Proxies;

import app.classmanagementappclient.Medium.Interfaces.Schools.InsertSchoolMediumInterface;
import app.classmanagementappclient.Medium.School.InsertSchool.InsertSchoolMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;


/**
 *
 * @author BEN
 */
public class InsertSchoolProxyV1 implements InsertSchoolMediumInterface {
    
    public int isInserted = 0;
    
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
        if (res)
            isInserted = 1;
        return true;
    }    
}