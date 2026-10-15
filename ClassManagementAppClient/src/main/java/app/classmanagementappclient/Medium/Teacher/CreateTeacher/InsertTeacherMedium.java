package app.classmanagementappclient.Medium.Teacher.CreateTeacher;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacher.InsertTeacher;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.FileHandlers.TeacherFileManipulator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertTeacherMedium 
{
    
    public boolean registerTeacher (TeacherMedium tm, Teacher teacher) 
    {
        boolean isOkay = false;
        String[] credentials = MediumUtility.getConnectionCredentials();
        
        try (Connection connection = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]))
        {
            InsertTeacher insertTeacher = new InsertTeacher(connection, teacher);
            isOkay = insertTeacher.insert();
            if (!isOkay)
                tm.setMsg(insertTeacher.getErrorMsg());
            else 
                putTeacherInApp (teacher, "You're successfully registered!", tm);
        } 
        catch (SQLException ex) {
            System.getLogger(TeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return isOkay;
    }
    
    public boolean logIn (TeacherMedium tm, Teacher teacher)
    {
        String[] credentials = MediumUtility.getConnectionCredentials();
        
        try (Connection connection = DriverManager.getConnection(credentials[0], credentials[1], credentials[2])) 
        {
            InsertTeacher insertTeacher = new InsertTeacher(connection, teacher);
            teacher = insertTeacher.getTeacher(connection, teacher);
            if (teacher != null) 
                putTeacherInApp(teacher, "You've successfully logged in!", tm);
            else
                tm.setMsg("Invalid credentials provided!");
        } 
        catch (Exception ex) {
            teacher = null;
            tm.setMsg(ex.getMessage());
            System.getLogger(TeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return teacher != null;
    }
    
    public boolean logOut (TeacherMedium tm) {
        LoggedInTeacher.makeTeacherLoggedOut();
        tm.setMsg("U're successfully logged out!");
        return true; 
    }
    
    private void putTeacherInApp (Teacher teacher, String msg, TeacherMedium tm) {
        LoggedInTeacher.setLoggedInTeacher(teacher);
        TeacherFileManipulator.storeTeacherIfLoggedIn();
        tm.setMsg(msg);
    }    
}
