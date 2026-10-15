package app.classmanagementappclient.Medium.StudentClass.InsertStudentClass;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudentClass.InsertStudentClass;
import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertStudentClassMedium {
    
    public boolean insertStudentToClass (StudentClassMedium studentClassMedium, StudentClass studentClass)
    {
        boolean isInserted = false;
        InsertStudentClass insertStudentClass;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertStudentClass = new InsertStudentClass(connection, studentClass);
            isInserted = insertStudentClass.insert();    
        }
        catch (SQLException ex) {
            throw new RuntimeException();
        }
        if (!isInserted)
            studentClassMedium.setMsg(insertStudentClass.getErrorMsg());
        
        return isInserted;
    }
}
