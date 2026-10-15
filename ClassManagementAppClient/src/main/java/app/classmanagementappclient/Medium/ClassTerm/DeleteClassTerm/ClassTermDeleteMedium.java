package app.classmanagementappclient.Medium.ClassTerm.DeleteClassTerm;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Backend.Database.Delete.ClassTerm.ClassTermDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassTermDeleteMedium 
{
    public boolean deleteClassTerm (ClassTermMedium classTermMedium, int classTermNum)
    {
        ClassTermDelete classTermDelete = null;
        boolean res = false;
        
        try {
            classTermDelete = new ClassTermDelete(classTermNum);
            res = classTermDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(ClassTermDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classTermMedium.setMsg(classTermDelete.getErrorMsg());

        return res;
    }     
}
