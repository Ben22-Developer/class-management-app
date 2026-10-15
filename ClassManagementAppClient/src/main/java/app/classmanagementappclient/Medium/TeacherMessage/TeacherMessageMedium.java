/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.TeacherMessage;

import app.classmanagementappclient.Medium.TeacherMessage.CreateTeacherMessage.InsertTeacherMessageMedium;
import app.classmanagementappclient.Medium.TeacherMessage.DeleteTeacherMessage.DeleteTeacherMessageMedium;
import app.classmanagementappclient.Medium.TeacherMessage.ReadTeacherMessage.ReadTeacherMessageMedium;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TeacherMessageMedium {
    
    private String errorMsg;

    
    public void insertTeacherMessage (TeacherMessage teacherMessage) {
        
        boolean bool = new InsertTeacherMessageMedium().insertTeacherMessage(teacherMessage, this);
        
        if (!bool) {
            showErrorMessage();
            throw new RuntimeException("Teacher message not inserted");
        }
    }
    
    public List<List<String>> readTeacherMessages () {
        return new ReadTeacherMessageMedium().readTeacherMessages();
    }
    
    public void deleteTeacherMessages (TeacherMessage teacherMessage) { 
        
        boolean bool = new DeleteTeacherMessageMedium().deleteTeacherMessage(teacherMessage, this);
        
        if (!bool) {
            showErrorMessage();
            throw new RuntimeException("Teacher message not deleted");
        }        
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public void showErrorMessage () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(errorMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}
