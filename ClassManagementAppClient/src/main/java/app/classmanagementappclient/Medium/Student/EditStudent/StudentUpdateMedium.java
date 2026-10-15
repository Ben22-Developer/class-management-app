package app.classmanagementappclient.Medium.Student.EditStudent;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Database.Update.Student.StudentDataUpdate;
import app.classmanagementappclient.Backend.Objects.Student.Student;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class StudentUpdateMedium {
    
    public boolean updateStudentData (StudentMedium studentMedium, Student currStudentData, Student newStudentData) {
        
        StudentDataUpdate dataUpdate = null;
        boolean bool = false;
        try {
            dataUpdate= new StudentDataUpdate(currStudentData, newStudentData);
            bool = dataUpdate.updateData();
        } 
        catch (SQLException ex) {
            System.getLogger(StudentUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        if (!bool)
            studentMedium.setMsg( dataUpdate.getErrorMsg() );
        return bool;
    }
}
