package app.classmanagementappclient.Medium.Teacher;

import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Backend.Security.SecurityUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Medium.Teacher.CreateTeacher.InsertTeacherMedium;
import app.classmanagementappclient.Medium.Teacher.DeleteTeacher.DeleteTeacherMedium;
import app.classmanagementappclient.Medium.Teacher.ReadTeacher.ReadTeacherMedium;
import app.classmanagementappclient.Medium.Teacher.UpdateTeacher.UpdateTeacherMedium;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TeacherMedium 
{
    private String msg;
    
    private LoggedInTeacher loggedInTeacher = LoggedInTeacher.getLoggedInTeacher();
    
    public boolean registerTeacher (Teacher teacher) 
    {
        boolean isOkay = credentialValidation(teacher);
        
        if (!isOkay) {
            return false;
        }
        InsertTeacherMedium ct = new InsertTeacherMedium();
        return ct.registerTeacher(this, teacher);
    }
    
    public boolean logIn (Teacher teacher) 
    {
        boolean isOkay = credentialValidation(teacher);
        if (!isOkay) {
            return false;
        }        
        InsertTeacherMedium ct = new InsertTeacherMedium();
        return ct.logIn(this, teacher);
    }
 
    public boolean logOut() {
        InsertTeacherMedium ct = new InsertTeacherMedium();
        return ct.logOut(this);
    }  
    
    public List<String> getUserData () {
        ReadTeacherMedium rt = new ReadTeacherMedium();
        return rt.getUserData();
    } 
    
    public String getUserPin () {
        ReadTeacherMedium rt = new ReadTeacherMedium();
        return rt.getTeacherPin();
    } 
    
    public boolean updateData (Teacher newTeacher) 
    {
        String teacherEncryptedPin = getUserPin ();
        String teacherUnencryptedPin = pinChecker(teacherEncryptedPin);
        
        if (teacherUnencryptedPin == null) 
            return false;
        
        boolean isOkay = credentialValidation(new Teacher(newTeacher.getName(), teacherUnencryptedPin, newTeacher.getPhoneNumber(), newTeacher.getEmail()));
        
        if (!isOkay) 
            return false; 
        
        UpdateTeacherMedium ut = new UpdateTeacherMedium();
        
        return ut.updateTeacher
        (
           this,  
           new Teacher(loggedInTeacher.getTeacherNum(), loggedInTeacher.getName(), teacherEncryptedPin, loggedInTeacher.getPhoneNumber(), loggedInTeacher.getEmail()), 
           newTeacher
        );
    }
    
    
    public boolean pinUpdate (String pin, String confirmedPin, String username) 
    {
        Teacher teacher = readTeacherByUsername (username);
        
        
        if (teacher == null) 
        {
            setMsg("Invalid username provided!");
            return false;
        }
        
        if (!credentialValidation(new Teacher(teacher.getName(), pin, teacher.getPhoneNumber(), teacher.getEmail())))
            return false;
        
        var updateTeacherMedium = new UpdateTeacherMedium();
        return updateTeacherMedium.updateTeacherPin(this, pin, confirmedPin, teacher.getTeacherNum());
    }    
    
    public boolean deleteAccount () 
    {
        String teacherPin = getUserPin ();
        boolean pinIsOkay = pinChecker(teacherPin, "Are you sure you want to delete your account?\nEnter your password:") != null;
        
        if (!pinIsOkay)
            return false;
        
        DeleteTeacherMedium deleteTeacherMedium = new DeleteTeacherMedium();
        return deleteTeacherMedium.deleteAccount(this);
    }
    
    private Teacher readTeacherByUsername (String username)
    {
        ReadTeacherMedium readTeacherMedium = new ReadTeacherMedium();
        Teacher teacher = readTeacherMedium.getTeacherByUsername(username);
        
        if (teacher == null) 
        {
            setMsg("Invalid username provided!");
        }        
        return teacher;
    }
    
    private String pinChecker (String teacherPin, String...customTitle) 
    {
        String txt = (customTitle.length == 0) ? "Enter your pin here" : customTitle[0];
        String pin = FrontEndUtility.getUserInput(txt, "Pin Checker");
        
        if (pin.isBlank()) {
            setMsg("No pin entered!");
            return null;
        }
        if (SecurityUtility.encrypted_NonEncryptedCheckUp(teacherPin, pin)) {
            return pin;
        }
        else {
            setMsg("Incorrect pin entered!");
            return null;
        }
    }
    
    private boolean credentialValidation (Teacher teacher)
    {
        Validation validation = new Validation();
        String[] credentials = { teacher.getName(), teacher.getPin(), teacher.getPhoneNumber(), teacher.getEmail() };
        
        for (int i = 0; i < credentials.length; i++) {
            if(!isCredentialValid(validation, i, credentials[i])) {
                setMsg(validation.getErrorMsg());
                return false;
            }
        }
        return true;        
    }
    
    private boolean isCredentialValid (Validation v, int index, String credential) {
        switch (index) {
            case 0 -> {
                return v.isNameValid(credential);
            }
            case 1 -> {
                return v.isPinValid(credential);
            }           
            case 2 -> {
                return v.isPhoneNumberValid(credential);
            }
            case 3 -> {
                return v.isEmailValid(credential);
            }           
        }
        return false;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
