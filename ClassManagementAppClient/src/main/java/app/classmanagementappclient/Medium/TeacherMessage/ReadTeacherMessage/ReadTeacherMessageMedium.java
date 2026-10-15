/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Medium.TeacherMessage.ReadTeacherMessage;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadTeacherMessage.ReadAllTeacherMessage.ReadAllTeacherMessage;
import app.classmanagementappclient.Backend.Database.Read.ReadTeacherMessage.ReadTeacherMessageData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacherMessageMedium {

    public List<List<String>> readTeacherMessages () {
        
        var readAllTeacherMessage = new ReadAllTeacherMessage();
        executeReadOperation(readAllTeacherMessage);;
        return readAllTeacherMessage.getData();
    }

    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            
            var readTeacherMessageData = new ReadTeacherMessageData(proxy);
            readTeacherMessageData.setConnection(connection);
            readTeacherMessageData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(getClass().getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }     
}
