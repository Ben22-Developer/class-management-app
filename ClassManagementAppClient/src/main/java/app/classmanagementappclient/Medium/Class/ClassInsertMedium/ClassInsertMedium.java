package app.classmanagementappclient.Medium.Class.ClassInsertMedium;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClass.InsertClass;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassInsertMedium
{   
    public boolean insertClass (ClassMedium classMedium, Classs classs) 
    {
        boolean inserted = false;
        InsertClass insertClass = null;
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            insertClass = new InsertClass(connection, classs);
            inserted = insertClass.insert();
        } 
        catch (SQLException ex) {
            System.getLogger(ClassInsertMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!inserted) 
            classMedium.setMsg(insertClass.getErrorMsg());
        
        return inserted;
    }   
}
