package app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.ClassInsertMedium;
import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.Interfaces.Classes.InsertClassMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassProxyV1 implements InsertClassMediumInterface {
    
    public int isInserted;
    
    @Override
    public boolean insertClass(ClassMedium medium) {

        boolean res = medium.validationTest();
        
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        
        ClassInsertMedium classInsertMedium = new ClassInsertMedium();
        res = classInsertMedium.insertClass(medium, medium.getClasss());
        
        if (res)
            isInserted = 1; 
        return true;   
    }   
}
