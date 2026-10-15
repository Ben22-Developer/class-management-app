package app.classmanagementappclient.Medium.ClassYear.InsertClassYear;

import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassYear.InsertClassYear;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertClassYearMedium {
    
    public boolean insertClassYear (ClassYearMedium classYearMedium, ClassYear classYear) {
        InsertClassYear insertClassYear = null;
        boolean res = false;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertClassYear = new InsertClassYear(connection, classYear);
            res = insertClassYear.insert();
        } catch (SQLException ex) {
            System.getLogger(InsertClassYearMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classYearMedium.setMsg(insertClassYear.getErrorMsg());
        return res;
    }
}
