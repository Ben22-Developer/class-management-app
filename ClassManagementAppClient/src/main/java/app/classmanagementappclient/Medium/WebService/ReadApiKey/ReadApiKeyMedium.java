/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.WebService.ReadApiKey;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadApiKey.ReadAPIKey.ReadApiKey;
import app.classmanagementappclient.Backend.Database.Read.ReadApiKey.ReadApiKeyData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadApiKeyMedium {
    
    public String readAPIKey () 
    {
        ReadStringInterface readAPIKey = new ReadApiKey();
        readData(readAPIKey);
        return readAPIKey.getData().isEmpty() ? "" : readAPIKey.getData().getFirst();
    }
    
    private void readData (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {
            var readaApiKeyData = new ReadApiKeyData(proxy);
            readaApiKeyData.setConnection(connection);
            readaApiKeyData.executeRead();
        }
        catch (SQLException ex) {
            System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException();
        }        
    }
}
