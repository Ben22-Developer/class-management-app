/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.WebService.DeleteApiKey;

import app.classmanagementappclient.Backend.Database.Delete.DeleteApiKey.DeleteApiKey;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DeleteApiKeyMedium {
    
    public boolean deleteApiKey () {
        
        DeleteApiKey deleteAPIKey = null;
        
        try {
            deleteAPIKey = new DeleteApiKey();
            return deleteAPIKey.deleteData();
        }
        catch (SQLException ex) {
            System.getLogger(DeleteApiKeyMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException();
        }
    }
}    