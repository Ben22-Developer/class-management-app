/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.Teacher;

import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.Main;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class LoggedInTeacher implements Serializable
{
    
    private static LoggedInTeacher loggedInTeacher;
    private static Properties teacherProperties = new Properties();

    private final int teacherNum;
    private final String name;
    private final String phoneNumber;
    private final String email;

    public LoggedInTeacher (int teacherNum, String name, String phoneNumber, String email) {
        this.teacherNum = teacherNum;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getTeacherNum() {
        return teacherNum;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "LoggedInTeacher{" + "teacherNum=" + teacherNum + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + '}';
    }
    
//    public static void setTeacherProperties(Properties aTeacherProperties) {
//        teacherProperties = aTeacherProperties;
//    }

//    public static Properties getTeacherProperties() {
//        return teacherProperties;
//    }

    
    public static void setTeacher(Teacher teacher) {
        loggedInTeacher = (teacher == null) ? null : new LoggedInTeacher(teacher.getTeacherNum(), teacher.getName(), teacher.getPhoneNumber(), teacher.getEmail());
    }
    
    public static void setProperties () 
    {
        String path = Main.getApplicationFilesPaths().getProperty("TeacherInfo");
        PropertiesFileManipulator.propertyLoad(path, teacherProperties);
    }

    public static void setLoggedInTeacher (Teacher teacher) {
        setTeacher(teacher);
        loggedInStatus(true);
    }
    
    public static void setLoggedInTeacher (LoggedInTeacher loggedInTeacher) {
        LoggedInTeacher.loggedInTeacher = loggedInTeacher;
        loggedInStatus(true);
    }
    
    public static void makeTeacherLoggedOut () {
        loggedInStatus(false);
        setTeacher(null);
    }
    
    private static void loggedInStatus (boolean isLoggedIn) {
       String path = Main.getApplicationFilesPaths().getProperty("TeacherInfo");
       teacherProperties.setProperty("isLoggedIn", String.valueOf(isLoggedIn));
       PropertiesFileManipulator.propertyStore(path, teacherProperties);
    }

    public static Properties getProperties () {
        return teacherProperties;
    }

    public static LoggedInTeacher getLoggedInTeacher() {
        return loggedInTeacher;
    }
}
