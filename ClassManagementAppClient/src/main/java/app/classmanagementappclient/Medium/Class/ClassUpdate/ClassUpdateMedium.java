package app.classmanagementappclient.Medium.Class.ClassUpdate;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Backend.Database.Update.ClassData.ClassDataUpdate;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassUpdateMedium 
{
    public boolean updateClass (ClassMedium classMedium, Classs currClassDetails, Classs newClassDetails) 
    {
        ClassDataUpdate classDataUpdate = null;
        boolean res = false;
        try {
            classDataUpdate = new ClassDataUpdate(newClassDetails, currClassDetails);
            res = classDataUpdate.updateDetails();
        } catch (SQLException ex) {
            System.getLogger(ClassUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classMedium.setMsg(classDataUpdate.getErrorMsg());
        
        return res;        
    }
}
