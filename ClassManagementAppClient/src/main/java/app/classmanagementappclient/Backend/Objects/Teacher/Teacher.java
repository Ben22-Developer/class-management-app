package app.classmanagementappclient.Backend.Objects.Teacher;

import app.classmanagementappclient.Backend.Security.SecurityUtility;;

/**
 *
 * @author BEN
 */
public class Teacher
{
    private int teacherNum;
    private String name;
    private String pin;
    private String encryptedPin;
    private String phoneNumber;
    private String email;

    public Teacher (String name, String pin, String phoneNumber, String email) {
        this.name = name;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
        setEncrptedPin();
    }
    
    public Teacher (int teacherNum, String name, String encryptedPin, String phoneNumber, String email) {
        this.teacherNum = teacherNum;
        this.name = name;
        this.encryptedPin = encryptedPin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public Teacher (String email) {
        this(0,null, null, null, email);
    }    

    public void setEncrptedPin ()
    {
        int hashingFactor = SecurityUtility.getPasswordHashingCostFactor();
        encryptedPin = SecurityUtility.encrypt(pin, hashingFactor);
    }

    public String getName() {
        return name;
    }
    
    public String getPin() {
        return pin;
    }    
    
    public String getEncryptedPin() {
        return encryptedPin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setTeacherNum (int teacherNum) {
     this.teacherNum = teacherNum;
    }
    
    public int getTeacherNum () {
        return teacherNum;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Teacher{" + "teacherNum=" + teacherNum + ", name=" + name + ", pin=" + pin + ", phoneNumber=" + phoneNumber + ", email=" + email + '}';
    }
    
    
}
