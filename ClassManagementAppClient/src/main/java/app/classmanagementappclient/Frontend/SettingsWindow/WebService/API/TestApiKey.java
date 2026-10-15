/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.WebService.API;

import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.Common.WebService.WebService;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TestApiKey extends WebService {
    
    public TestApiKey(JLabel connectionLabel) {
        super(connectionLabel);
    }
    
    public void test () {
        
        new SwingWorker<Object, HttpResponse<?>> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                var request = HttpRequest
                        .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.testSecureApiPath))
                        .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                        .GET()
                        .build();
                
                HttpResponse<?> response = CommonWebServiceFeatures.getHttpResponse(request, getConnectionLabel());
                publish(response);
                return null;
            }

            @Override
            protected void process(List<HttpResponse<?>> chunks) {
                
                int status = chunks.getFirst().statusCode();
                
                String msg = status < 300 ? "API key & connection test succedded" : "API key & connection test failed";
                
                FrontEndUtility.showMsgJOptionPane(msg, "API key & Connection Status", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }.execute();
        
    }
}
