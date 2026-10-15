package app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm.Proxies;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm.InsertClassTermMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassTerm.InsertClassTermMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassTermProxy implements InsertClassTermMediumInterface {

    @Override
    public boolean insertClass(ClassTermMedium medium) {
        boolean res = medium.validateTerm();
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        InsertClassTermMedium classTermMedium = new InsertClassTermMedium();
        res = classTermMedium.insertClassTerm(medium, medium.getClassTerm());
        if (!res) 
            medium.showErrMsg();
        return res;        
    }
}
