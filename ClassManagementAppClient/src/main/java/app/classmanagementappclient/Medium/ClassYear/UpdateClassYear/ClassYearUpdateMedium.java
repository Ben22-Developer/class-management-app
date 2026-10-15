package app.classmanagementappclient.Medium.ClassYear.UpdateClassYear;

import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Backend.Database.Update.ClassYearUpdate.ClassYearUpdate;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassYearUpdateMedium {
    public boolean updateYear (ClassYearMedium classYearMedium, ClassYear currData, ClassYear newData) 
    {
        ClassYearUpdate update = null;
        boolean res = false;
        try {
            update = new ClassYearUpdate(currData, newData);
            res = update.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(ClassYearUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classYearMedium.setMsg(update.getErrorMsg());
        
        return res;        
    }    
}
