package app.classmanagementappclient.Medium.ClassYear.InsertClassYear.Proxies;

import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.ClassYear.InsertClassYear.InsertClassYearMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassYear.InsertClassYearMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassYearProxyV1 implements InsertClassYearMediumInterface {
    
    public int isInserted;
    
    @Override
    public boolean insertClass(ClassYearMedium medium) {
        boolean res = medium.validateYear();
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        InsertClassYearMedium insertClassYearMedium = new InsertClassYearMedium();
        res = insertClassYearMedium.insertClassYear(medium, medium.getClassYear());
        
        if (res)
            isInserted = 1; 
        
        return true;        
    }
    
}
