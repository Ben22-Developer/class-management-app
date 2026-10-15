/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.SettingsWindow.WebService.API.ClaimAPI;

import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.Common.WebService.WebService;

import java.net.URI;

/**
 *
 * @author BEN
 */
public abstract class ClaimAPIKey extends WebService{
    
    private final URI uri;
    
    public ClaimAPIKey(javax.swing.JLabel connectionLabel) {
        super(connectionLabel);
        this.uri = URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.apiClaimPath);
    }
    
    public void getAPIKey () {
        sendRequest();
    }

    public URI getUri() {
        return uri;
    }
    
    public abstract void sendRequest();
}
