package app.classmanagementappclient.Medium;

import app.classmanagementappclient.Backend.Database.DatabaseManipulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class MediumUtility 
{
    public static String[] getConnectionCredentials () 
    {
        Properties prop =  DatabaseManipulation.getDbProperties();    
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        
        return new String[] { url, user, password };
    } 
    
    public static Connection getConnection () throws SQLException {
        String[] credentials = getConnectionCredentials();
        return DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
    }
    
    public static void closeConnection (Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.getLogger(MediumUtility.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
