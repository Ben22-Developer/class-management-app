package app.classmanagementappclient.Medium.Student.CreateStudent;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudent.InsertStudent;
import app.classmanagementappclient.Backend.Objects.Student.Student;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertStudentMedium 
{
    public boolean insertStudent (StudentMedium studentMedium, Student student)
    {
        boolean res = false;
        InsertStudent insertStudent = null;
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            insertStudent = new InsertStudent(connection, student);
            res = insertStudent.insert();
        }
        catch (SQLException exception) {
            throw new RuntimeException();
        }
        
        if (!res)
            studentMedium.setMsg(insertStudent.getErrorMsg());
        return res;
    }
            
}
