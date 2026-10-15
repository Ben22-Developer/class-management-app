package app.classmanagementappclient.Frontend.SettingsWindow.WebService.API;

import app.classmanagementappclient.Medium.WebService.WebServiceMedium;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.Common.WebService.WebService;
import app.classmanagementappclient.Frontend.Exception.ServerException;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load.WebServiceWindowLoad;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpRequest;

/**
 *
 * @author BEN
 */
public class DeleteAPIKey extends WebService {
    
    private final SettingsWindow sw;
    
    public DeleteAPIKey (JLabel label, SettingsWindow sw) {
        super(label);
        this.sw = sw;
    }
    
    public void delete () {
        
        int response = FrontEndUtility.showConfirmDialog("Are you sure you want to delete your API key?", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
        
        if (response != 0)
            return;
        
        new SwingWorker<Object, Object> () {
            @Override
            protected Object doInBackground() throws Exception {
                onlineAPIKeyDelete();
                new WebServiceMedium().deleteTeacherAPIKey();
                WebServiceWindowLoad.apiKeyCheckUp(sw);
                new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("API key deleted successfully", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            
        }.execute();
    }
    
    private void onlineAPIKeyDelete () throws ServerException {
        
        var request = HttpRequest
        .newBuilder()
        .uri(URI.create( CommonWebServiceFeatures.getBaseURL()+CommonWebServiceFeatures.deleteAccountPath ))
        .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
        .DELETE()
        .build();

        var response = CommonWebServiceFeatures.getHttpResponse(request, getConnectionLabel());
        if (response.statusCode() > 300) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("An error occured during this operation!", "Error Message", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        }
    }
}
