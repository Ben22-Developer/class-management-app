/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject.ContactUs;

import java.sql.Timestamp;

/**
 *
 * @author BEN
 */
public class TeacherMessage {
    
    private final String message;
    private Timestamp timestamp;

    public TeacherMessage(String message) {
        this.message = message;
    }
    
    public TeacherMessage(String message, Timestamp timestamp) {
        this(message);
        this.timestamp = timestamp;
    }    

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TeacherMessage{" + ", message=" + message + ", timestamp=" + timestamp + '}';
    }
}
