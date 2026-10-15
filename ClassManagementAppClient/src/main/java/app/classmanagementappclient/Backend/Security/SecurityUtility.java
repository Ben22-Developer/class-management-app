/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Security;

import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.Main;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class SecurityUtility {
    
    private static final Properties securityProperties = new Properties();
    
    private static final String PasswordHashingCostFactorKey = "PasswordHashingCostFactor";
    
    public static void initSecurityProperties () 
    {
        String path = Main.getApplicationFilesPaths().getProperty("SecurityProperties");
        PropertiesFileManipulator.propertyLoad(path, securityProperties);
    }
    
    public static int getPasswordHashingCostFactor () 
    {
        return Integer.parseInt(securityProperties.getProperty(PasswordHashingCostFactorKey));
    }

    public static String encrypt (String nonEncrypted, int costFactor)
    {
        return BCrypt.hashpw(nonEncrypted, BCrypt.gensalt(costFactor));
    }
    
    public static boolean encrypted_NonEncryptedCheckUp (String encrypted, String nonEncrypted)
    {
        return BCrypt.checkpw(nonEncrypted, encrypted);
    }
}
