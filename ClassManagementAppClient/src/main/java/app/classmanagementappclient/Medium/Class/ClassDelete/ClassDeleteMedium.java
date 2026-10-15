package app.classmanagementappclient.Medium.Class.ClassDelete;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Backend.Database.Delete.Classes.ClassDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassDeleteMedium 
{
    public boolean deleteClass (ClassMedium classMedium, int classNum)
    {
        ClassDelete classDelete = null;
        boolean res = false;
        
        try {
            classDelete = new ClassDelete(classNum);
            res = classDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(ClassDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classMedium.setMsg(classDelete.getErrorMsg());

        return res;
    }
}
