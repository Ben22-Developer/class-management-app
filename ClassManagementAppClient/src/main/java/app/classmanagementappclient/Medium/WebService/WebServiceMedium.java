package app.classmanagementappclient.Medium.WebService;

import app.classmanagementappclient.Medium.WebService.CreateApiKey.CreateApiKeyMedium;
import app.classmanagementappclient.Medium.WebService.DeleteApiKey.DeleteApiKeyMedium;
import app.classmanagementappclient.Medium.WebService.ReadApiKey.ReadApiKeyMedium;
import app.classmanagementappclient.Backend.Objects.WebObject.ApiKey;

/**
 *
 * @author BEN
 */
public class WebServiceMedium {
    
    public void createTeacherAPIKey (ApiKey apiKey) {
        new CreateApiKeyMedium().createApiKey(apiKey);
    }        
    
    public String readTeacherAPIKey () {
        return new ReadApiKeyMedium().readAPIKey();
    }
    
    public void deleteTeacherAPIKey () {
        new DeleteApiKeyMedium().deleteApiKey();
    }
}
