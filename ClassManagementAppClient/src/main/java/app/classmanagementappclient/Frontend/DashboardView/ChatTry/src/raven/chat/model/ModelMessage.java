package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.model;

import javax.swing.*;
import java.sql.Timestamp;


public class ModelMessage {

    private Icon icon;
    private String message;
    private final Timestamp timestamp;    
    
    public ModelMessage(Icon icon, String message, Timestamp timestamp) {
        this.icon = icon;
        this.message = message;
        this.timestamp = timestamp;
    }   
    
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
