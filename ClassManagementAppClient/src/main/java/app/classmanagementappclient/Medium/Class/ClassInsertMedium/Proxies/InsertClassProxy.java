package app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.ClassInsertMedium;
import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.Interfaces.Classes.InsertClassMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassProxy implements InsertClassMediumInterface {

    @Override
    public boolean insertClass(ClassMedium medium) {

        boolean res = medium.validationTest();
        
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        ClassInsertMedium classInsertMedium = new ClassInsertMedium();
        res = classInsertMedium.insertClass(medium, medium.getClasss());            
        
        if (!res)
            medium.showErrMsg();
        
        return res;
           
    }
    
}
