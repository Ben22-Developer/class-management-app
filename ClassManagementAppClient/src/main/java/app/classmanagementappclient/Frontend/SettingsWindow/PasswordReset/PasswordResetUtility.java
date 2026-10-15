/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.PasswordReset;

import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.User.Username;
import app.classmanagementappclient.Backend.Objects.WebObject.*;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class PasswordResetUtility 
{
    
    public static void usernameSubmit (Username username, JLabel connectionLabel, JComponent[] emailSubmitComponents, JComponent[] emailCodeSubmitComponents)
    {
        HttpRequest httpRequest;
        Gson gson = new Gson();
        
        
        try 
        {         
            FrontEndUtility.enableJComponents(emailSubmitComponents , false);
            
            usernameValidate (username.getUsername(), emailSubmitComponents);
            
            httpRequest = HttpRequest
                    .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.passwordRestCode))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(username)))
                    .header("Content-Type", "application/json")
                    .build();
            
            HttpResponse<?> httpResponse = CommonWebServiceFeatures.getHttpResponse(httpRequest, connectionLabel);         
            
            afterEmailSubmit(httpResponse, emailSubmitComponents, emailCodeSubmitComponents);
            
        }
        catch (Exception ex) {
            FrontEndUtility.showMsgJOptionPane(ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void usernameValidate (String username, JComponent[] emailSubmitComponents)
    {
        var validate = new Validation();
        
        boolean res = validate.isEmailValid(username);
        
        if (res)
            return;
        FrontEndUtility.enableJComponents(emailSubmitComponents , true);
        throw new RuntimeException("Invalid email format!");
    }
    
    private static void afterEmailSubmit (HttpResponse<?> httpResponse, JComponent[] emailSubmitComponents, JComponent[] emailCodeSubmitComponents)
    {
        boolean canSubmitode = httpResponse.statusCode() < 300;
        
        FrontEndUtility.enableJComponents(emailSubmitComponents , !canSubmitode);
        FrontEndUtility.enableJComponents(emailCodeSubmitComponents , canSubmitode);
        FrontEndUtility.showMsgJOptionPane(httpResponse.body().toString(), canSubmitode == true ? "Information Message" : "Error Message", canSubmitode == true ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    
    public static void emailCodeSubmit (JLabel connectionLabel, PasswordResetCode passwordResetCode, JComponent[] emailCodeComponents, JComponent[] pinComponents)
    {
        HttpRequest httpRequest;
        Gson gson = new Gson();
        
        try 
        {         
            FrontEndUtility.enableJComponents(emailCodeComponents, false);
            
            httpRequest = HttpRequest
                    .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.passwordRestCode))
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(gson.toJson(passwordResetCode)))
                    .header("Content-Type", "application/json")
                    .build();
            
            HttpResponse<?> httpResponse = CommonWebServiceFeatures.getHttpResponse(httpRequest, connectionLabel);         
            
            confirmPasswordRestCodeResult(httpResponse, emailCodeComponents, pinComponents);
        }
        catch (Exception ex) {
            System.getLogger(PasswordResetUtility.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private static void confirmPasswordRestCodeResult  (HttpResponse httpResponse, JComponent[] emailComponents, JComponent[] pinComponents)
    {
            if (httpResponse.statusCode() < 300)
            {
                FrontEndUtility.showMsgJOptionPane(httpResponse.body().toString(), "Information Message", JOptionPane.INFORMATION_MESSAGE);
                FrontEndUtility.enableJComponents(pinComponents, true);
            }
            else 
            {
                FrontEndUtility.showMsgJOptionPane(httpResponse.body().toString(), "Error Message", JOptionPane.ERROR_MESSAGE);        
                FrontEndUtility.enableJComponents(emailComponents, true);
            }
    }
    
    public static void resetPin (String pin, String confirmedPin, String username, JComponent[] pinComponents)
    {
        FrontEndUtility.enableJComponents(pinComponents, false);
        
        var teacherMedium = new TeacherMedium();
        
        boolean res = teacherMedium.pinUpdate(pin, confirmedPin, username);
        
        if (res) 
        {
            FrontEndUtility.showMsgJOptionPane(teacherMedium.getMsg(), "Information Message", JOptionPane.INFORMATION_MESSAGE);
        }
        else 
        {
            FrontEndUtility.showMsgJOptionPane(teacherMedium.getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
            FrontEndUtility.enableJComponents(pinComponents, true);
        }
        
    }
}


