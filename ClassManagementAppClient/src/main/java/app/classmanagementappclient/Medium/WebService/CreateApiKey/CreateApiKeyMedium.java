/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.WebService.CreateApiKey;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertWebAPIKey.InsertAPIKey;
import app.classmanagementappclient.Backend.Objects.WebObject.ApiKey;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class CreateApiKeyMedium {
    
    public void createApiKey (ApiKey apiKey) {
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            var insertAPIKey = new InsertAPIKey(connection, apiKey);
            insertAPIKey.insert();
        } 
        catch (SQLException ex) {
            System.getLogger(CreateApiKeyMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException();
        }
    }
}
