package app.classmanagementappclient.Medium.StudentClass.DeleteStudentClass;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Database.Delete.StudentClass.StudentClassDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteStudentClassMedium {


    public boolean deleteStudentClass (StudentClassMedium studentClassMedium, int studentClassNum) 
    {    
        StudentClassDelete studentClassDelete = null;
        boolean res = false;
        
        try {
            studentClassDelete = new StudentClassDelete(studentClassNum);
            res = studentClassDelete.deleteData();
        } catch (SQLException ex) {
            System.getLogger(DeleteStudentClassMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            studentClassMedium.setMsg( studentClassDelete.getErrorMsg() );
        
        return res;
    }    
}
