/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.TeacherMessage.CreateTeacherMessage;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.TeacherMessage.TeacherMessageMedium;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacherMessage.InsertTeacherMessage;
import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;

import java.sql.Connection;
import java.sql.DataTruncation;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertTeacherMessageMedium {
    
    public boolean insertTeacherMessage (TeacherMessage teacherMessage, TeacherMessageMedium teacherMessageMedium) {
        
        InsertTeacherMessage insertTeacherMessage = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
        
            insertTeacherMessage = new InsertTeacherMessage (connection, teacherMessage);
            return insertTeacherMessage.insert();
        } 
        catch (SQLException ex) {
            
            if (ex instanceof DataTruncation d) {
                teacherMessageMedium.setErrorMsg("Data too long to be inserted!\nPlease contact the developer or our agent if the error persists.");
            }
            else
                throw new RuntimeException(ex.getMessage());
        }
        return false;
    }
}
