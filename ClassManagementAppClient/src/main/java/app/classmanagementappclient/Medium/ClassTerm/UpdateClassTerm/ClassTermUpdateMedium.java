package app.classmanagementappclient.Medium.ClassTerm.UpdateClassTerm;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Backend.Database.Update.ClassTermUpdate.ClassTermUpdate;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassTermUpdateMedium {
    
    public boolean updateTerm (ClassTermMedium classTermMedium, ClassTerm currData, ClassTerm newData) 
    {
        ClassTermUpdate update = null;
        boolean res = false;
        try {
            update = new ClassTermUpdate(currData, newData);
            res = update.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(ClassTermUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classTermMedium.setMsg(update.getErrorMsg());
        
        return res;        
    }
}
