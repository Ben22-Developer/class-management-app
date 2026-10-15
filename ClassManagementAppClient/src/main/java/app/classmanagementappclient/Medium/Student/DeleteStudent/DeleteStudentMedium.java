package app.classmanagementappclient.Medium.Student.DeleteStudent;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Database.Delete.Students.General.StudentDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteStudentMedium {
    

    public boolean deleteStudentClass (StudentMedium studentMedium, String studentID) 
    {    
        StudentDelete studentDelete = null;
        boolean res = false;
        
        try {
            studentDelete = new StudentDelete(studentID);
            res = studentDelete.deleteData();
        } catch (SQLException ex) {
            System.getLogger(DeleteStudentMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            studentMedium.setMsg( studentDelete.getErrorMsg() );
        
        return res;
    }    
}
