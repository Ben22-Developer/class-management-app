package app.classmanagementappclient.Medium.Teacher.DeleteTeacher;

import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import app.classmanagementappclient.Backend.Database.Delete.Teacher.TeacherDelete;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteTeacherMedium 
{
    public boolean deleteAccount (TeacherMedium tm) 
    {
        boolean isOk = false;
        TeacherDelete teacherDelete = null;
        try {
            teacherDelete = new TeacherDelete();
            isOk = teacherDelete.deleteData();
        } 
        catch (SQLException ex) {
            System.getLogger(DeleteTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        String msg = (isOk == true) ? "Account Deleted Successfully!" : teacherDelete.getErrorMsg();
        tm.setMsg(msg);
        if (isOk) {
            LoggedInTeacher.makeTeacherLoggedOut();
            LoggedInTeacher.setTeacher(null);
        }
        return isOk;
    }
}
