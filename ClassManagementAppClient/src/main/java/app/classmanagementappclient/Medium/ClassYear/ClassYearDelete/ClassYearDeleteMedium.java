package app.classmanagementappclient.Medium.ClassYear.ClassYearDelete;

import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Backend.Database.Delete.ClassYear.ClassYearDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassYearDeleteMedium {
    public boolean deleteClassYear (ClassYearMedium classYearMedium, int classYearNum)
    {
        ClassYearDelete classYearDelete = null;
        boolean res = false;
        
        try {
            classYearDelete = new ClassYearDelete(classYearNum);
            res = classYearDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(ClassYearDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classYearMedium.setMsg(classYearDelete.getErrorMsg());

        return res;
    }    
}
