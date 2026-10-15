/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.ClaimAPI.Inherits;

import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.User.UserBuilder;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.ClaimAPI.ClaimAPIKey;
import com.google.gson.Gson;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ClaimFreeTierAPI extends ClaimAPIKey{

    public ClaimFreeTierAPI(JLabel connectionLabel) {
        super(connectionLabel);
    }
    
    @Override
    public void sendRequest () {
        
        new SwingWorker<Object, Object> () {
            
            private int statusCode;
            private String msg;
            
            @Override
            protected Object doInBackground() throws Exception {
                
                UserBuilder userBuilder = new UserBuilder();
                userBuilder.setUsername(LoggedInTeacher.getLoggedInTeacher().getEmail());
                Gson gson = new Gson();
                String jsonString = gson.toJson(userBuilder);
                
                HttpRequest httpRequest = HttpRequest
                        .newBuilder()
                        .header("Content-type", "application/json")
                        .uri(getUri())
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                        .timeout(Duration.of(CommonWebServiceFeatures.getRequestTimeout(), CommonWebServiceFeatures.getChronoUnit()))
                        .build();
                
                HttpResponse<?> response = CommonWebServiceFeatures.getHttpResponse(httpRequest, getConnectionLabel());
                statusCode = response.statusCode();
                msg = String.valueOf( response.body() );
                
//                System.out.println("response: " + response);
//                System.out.println("statusCode " + statusCode);
//                System.out.println("msg: " + msg);
                
                
                publish(msg);
                return null;
            }

            @Override
            protected void process(List<Object> chunks) {
                
                String title = statusCode > 200 ? "Error Message" : "Informational Mesage";
                int msgType = statusCode > 200 ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
                FrontEndUtility.showMsgJOptionPane(msg, title, msgType);
            }
        }.execute();        
    }    
    
}
