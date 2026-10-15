package app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassTerm.InsertClassTerm;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertClassTermMedium {
        
    public boolean insertClassTerm (ClassTermMedium classTermMedium, ClassTerm classTerm) {
        
        InsertClassTerm insertTerm = null;
        boolean res = false;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertTerm = new InsertClassTerm(connection, classTerm);
            res = insertTerm.insert();
        } catch (SQLException ex) {
            System.getLogger(InsertClassTermMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        if (!res)
            classTermMedium.setMsg(insertTerm.getErrorMsg());
        return res;
    }
}
