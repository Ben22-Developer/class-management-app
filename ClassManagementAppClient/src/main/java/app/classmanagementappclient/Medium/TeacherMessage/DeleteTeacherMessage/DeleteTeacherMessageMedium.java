/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.TeacherMessage.DeleteTeacherMessage;

import app.classmanagementappclient.Medium.TeacherMessage.TeacherMessageMedium;
import app.classmanagementappclient.Backend.Database.Delete.TeacherMessage.DeleteTeacherMessage;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteTeacherMessageMedium {
    
    public boolean deleteTeacherMessage (TeacherMessage teacherMessage, TeacherMessageMedium teacherMessageMedium) {
        
        DeleteTeacherMessage deleteTeacherMessage = null;
        try {
            
            deleteTeacherMessage = new DeleteTeacherMessage(teacherMessage);
            return deleteTeacherMessage.deleteData();
        } catch (SQLException ex) {
            teacherMessageMedium.setErrorMsg(deleteTeacherMessage.getErrorMsg());
            System.getLogger(DeleteTeacherMessageMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
}
