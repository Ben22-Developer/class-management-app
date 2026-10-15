package app.classmanagementappclient.Medium.School.InsertSchool;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertSchool.InsertSchool;
import app.classmanagementappclient.Backend.Objects.School.School;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertSchoolMedium {
    public boolean insertSchool (SchoolMedium schoolMedium, School school) {
        boolean isInserted = false;
        InsertSchool insertSchool;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertSchool = new InsertSchool(connection, school);
            isInserted = insertSchool.insert();    
        }
        catch (SQLException ex) {
            throw new RuntimeException();
        }
        if (!isInserted)
            schoolMedium.setMsg(insertSchool.getErrorMsg());
        
        return isInserted;
    }
    
    
}
