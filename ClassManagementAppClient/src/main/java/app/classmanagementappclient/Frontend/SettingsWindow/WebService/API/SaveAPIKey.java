/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.WebService.API;

import app.classmanagementappclient.Medium.WebService.WebServiceMedium;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.WebObject.ApiKey;
import app.classmanagementappclient.Backend.Objects.User.UserBuilder;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.Common.WebService.WebService;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load.WebServiceWindowLoad;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class SaveAPIKey extends WebService {
    
    private final SettingsWindow settingsWindow;
    
    public SaveAPIKey (SettingsWindow settingsWindow, JLabel connectionLabel) {
        super(connectionLabel);
        this.settingsWindow = settingsWindow;
    }
    
    public void saveAPIKey () {
        
        String apiKey = FrontEndUtility.getUserInput("Enter your API key from the email here", "API Key Register");
        
        if (apiKey.isBlank()) {
            showMsgJOptionPane("No API Key provided!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean isValid = onlineApiKeySave(apiKey);
        
        if (isValid)
            saveApiKey(apiKey);
        else
            showMsgJOptionPane("Unexisting API Key provided!", "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    private boolean onlineApiKeySave (String apiKey)
    {
        var sw = new SwingWorker<Boolean, Object> () {
            @Override
            protected Boolean doInBackground() throws Exception {
     
                UserBuilder userBuilder = new UserBuilder();
                userBuilder = userBuilder.setUsername(LoggedInTeacher.getLoggedInTeacher().getEmail()).setUserPassword(apiKey);
                
                Gson gson = new Gson();
                String jsonString = gson.toJson(userBuilder);
                
                var request = HttpRequest
                        .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.apiConfirmPath))
                        .header("Content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                        .build();
                
                HttpResponse<?> response = CommonWebServiceFeatures.getHttpResponse(request, getConnectionLabel());
                
                return response.statusCode() < 300;
            }
        };
        
        try {
            sw.execute();
            return sw.get();
        } catch (Exception ex) {
            System.getLogger(SaveAPIKey.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            showMsgJOptionPane("An error occured while saving the api key", "Error Message", JOptionPane.ERROR_MESSAGE);
//            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    private void saveApiKey (String apiKey) {
     
        new WebServiceMedium().createTeacherAPIKey(new ApiKey(apiKey));
        showMsgJOptionPane("API key saved successfully", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
        WebServiceWindowLoad.apiKeyCheckUp(settingsWindow);
    }
    
    private void showMsgJOptionPane (String msg, String title, int msgType) {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, title, msgType);
    }
}
