/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.Common.WebService;

import app.classmanagementappclient.Backend.Objects.CustomGson.LocalDateAdapter;
import app.classmanagementappclient.Backend.Objects.Teacher.*;
import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Main;
import app.classmanagementappclient.Medium.WebService.WebServiceMedium;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class CommonWebServiceFeatures {
    
    public static final String apiClaimPath = "/all/free/api/claim";
    public static final String apiConfirmPath = "/all/free/api/confirm";
    
    public static final String deleteAccountPath = "/secure/account/delete";
    
    public static final String testSecureApiPath = "/secure/test";
    
    public static final String postSchedule = "/secure/post/schedule";
    public static final String readAllSchedules = "/secure/read/all/schedule";
    public static final String readAllSchedulesArchive = "/secure/read/all/archive/schedule";
    public static final String putSchedule = "/secure/put/schedule";
    public static final String deleteSchedules = "/secure/delete/schedules";
    public static final String deleteArchiveSchedules = "/secure/delete/archive/schedules";
    
    public static final String sendEmailToAdmin = "/secure/send/admin/message";
    
    public static final String passwordRestCode = "/all/password-reset-code";
    
    public static final String aiChat = "/secure/ai/chat";
    
    public static boolean onGoingRequest = false;
//    public static String apiClaimPath = "/free/api/claim";
    
    private static Properties properties = new Properties();
    
    public static void loadWebProperties () {
        String path = Main.getApplicationFilesPaths().get("WebProperties").toString();
        PropertiesFileManipulator.propertyLoad(path, properties);
    }
    
    public static String getBaseURL () {
        return properties.get("ServerBaseURL").toString();
    }
    
    public static String getHttpBasicCredentials () {
        
        String username = LoggedInTeacher.getLoggedInTeacher().getEmail();
        String password = teacherApiKey();
        
        if (password.isBlank()) {
            noApiKeyMsg();
            throw new RuntimeException("Invalid API Key (password) at CommonWebServiceFeatures.getHttpBasicCredentials");
        }
        
        String credentials = username + ":" + password;
        
        return "Basic "+ Base64.getEncoder().encodeToString(credentials.getBytes());
    }
    
    public static void noApiKeyMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("You have no API key saved yet!\nClaim for it and try our service later.", "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    public static String teacherApiKey () {
        return new WebServiceMedium().readTeacherAPIKey();
    }
    
    public static HttpResponse<?> getHttpResponse (HttpRequest httpRequest, JLabel label) {
        
        
        var sw = new SwingWorker<HttpResponse<?>, ConnectionEnum> () {
            
            @Override
            protected HttpResponse<?> doInBackground() throws Exception {
                
//                System.out.println("Sleeping at " + this.getClass().getName() + ".getHttpResponse");
                if (onGoingRequest)
                    throw new RuntimeException();
                 
                
                onGoingRequest = true;
                publish(ConnectionEnum.ConnectionLabel);
//                FrontEndUtility.sleep(10_000);
                
                var response = HttpClient
                    .newHttpClient()
                    .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .get();
                
                setConnectionLabelConnected (label);
                return response;
            }

            @Override
            protected void process(List<ConnectionEnum> chunks) {
                
                if (chunks.getFirst().equals(ConnectionEnum.ConnectionLabel))
                    setConnectionLabelLoading(label);
            }
            
            
        };
        
        try {
            sw.execute();
            return sw.get();
        } 
        catch (Exception e) {
            
            System.out.println(e);
            
            if (e instanceof HttpTimeoutException)
                setConnectionLabelDisconnected(label); 
            else
                new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("An error occured while connecting to the server", "Error Message", JOptionPane.ERROR_MESSAGE);
//            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            onGoingRequest = false;
        }
    }
    
    private static void setConnectionLabelLoading (JLabel label) {
        label.setForeground(Color.ORANGE);
        label.setText("Loading ...");       
    }
    
    private static void setConnectionLabelConnected (JLabel label) {
        SwingUtilities.invokeLater(() -> {
            label.setForeground(Color.GREEN);
            label.setText("Connected");
        });        
    }

    private static void setConnectionLabelDisconnected (JLabel label) {
        SwingUtilities.invokeLater(() -> {
            label.setForeground(Color.RED);
            label.setText("Disconnected");
        });        
    }    
    
    public static int getRequestTimeout () {
        Object object = properties.get("RequestTimeout");
        String string = String.valueOf(object);
        int integer = Integer.parseInt(string);
        return integer;
    }
    
    public static ChronoUnit getChronoUnit () {
        return ChronoUnit.MINUTES;
    }
    
    public static Gson getRegisteredLocalDateGson () {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
    
    public static void addMultipartBytes (List<byte[]> list, String boundary, String key, String value, String contentType, boolean...closeBoundary) 
    {
        String row = "--" + boundary + "\r\n" +
                         "Content-Disposition: form-data; name=\"" + key + "\"\r\n" +
                         "Content-Type: " + contentType + "\r\n\r\n" +
                         value + "\r\n";
        
        list.add (row.getBytes(StandardCharsets.UTF_8));
        
        if (closeBoundary.length != 0) {
            String s = "--"+boundary+"--\r\n";
            list.add(s.getBytes(StandardCharsets.UTF_8));
        }
    }
    
    public static byte[] formartMultipartBytesArr (List<byte[]> list)
    {
        int totalLength = list.stream().mapToInt(b -> b.length).sum();
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        
        for (byte[] b : list) {
            System.arraycopy(b, 0, result, currentIndex, b.length);
            currentIndex += b.length;
        }
        return result;        
    }
}

enum ConnectionEnum {
    ConnectionLabel
}