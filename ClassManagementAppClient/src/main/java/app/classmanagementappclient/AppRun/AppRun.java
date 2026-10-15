//package src.AppRun;

package app.classmanagementappclient.AppRun;

import app.classmanagementappclient.Backend.Security.SecurityUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;

/**
 *
 * @author BEN
 */
public class AppRun 
{
    public static void startApp() {
        FrontEndUtility.startApp();
        SecurityUtility.initSecurityProperties();
    }
}
