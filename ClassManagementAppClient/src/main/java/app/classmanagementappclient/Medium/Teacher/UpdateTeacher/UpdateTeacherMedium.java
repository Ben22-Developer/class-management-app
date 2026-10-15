package app.classmanagementappclient.Medium.Teacher.UpdateTeacher;

import app.classmanagementappclient.Backend.Database.Update.Teacher.TeacherDetailsUpdate;
import app.classmanagementappclient.Backend.Database.Update.Teacher.TeacherPinUpdate;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class UpdateTeacherMedium
{
    public boolean updateTeacher (TeacherMedium tm, Teacher currTeacher, Teacher newTeacher)
    {
        boolean res = false;
        TeacherDetailsUpdate detailsUpdate = null;
        
        try {
            detailsUpdate = new TeacherDetailsUpdate();
            detailsUpdate.setCurrTeacherData(currTeacher);
            detailsUpdate.setNewTeacherData(newTeacher);
            res = detailsUpdate.updateDetails();
        } 
        catch (SQLException ex) {
            System.getLogger(UpdateTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        String msg = (res == true) ? "Data updated successfully\nIf you updated your username/email contact us via Gmail so that we synchronizes the changes. (If u uses our online services)" : detailsUpdate.getErrorMsg();
        tm.setMsg(msg);
        return res;
    }   
    
    public boolean updateTeacherPin (TeacherMedium tm, String pin, String confirmedPin, int teacherNum)
    {
        boolean res = false;
        TeacherPinUpdate pinUpdate = null;
        
        try {
            pinUpdate = new TeacherPinUpdate(pin, confirmedPin, teacherNum);
            res = pinUpdate.updateDetails();
        } 
        catch (SQLException ex) {
            System.getLogger(UpdateTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        String msg = (res == true) ? "Pin is successfully updated!" : pinUpdate.getErrorMsg();
        tm.setMsg(msg);
        return res;
    }   
}
