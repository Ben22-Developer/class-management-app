package app.classmanagementappclient.Backend.Database;

import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public abstract class DatabaseManipulation 
{
    private static Properties dbProperties = new Properties();
    private Connection connection;
//    private PreparedStatement preparedStatement;

    public DatabaseManipulation () throws SQLException 
    {
        String url = dbProperties.getProperty("url");
        String user = dbProperties.getProperty("user"); 
        String password = dbProperties.getProperty("password"); 
 
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public static Properties getDbProperties() {
        return dbProperties;
    }

    
    public Connection getConnection() {
        return connection;
    }

    protected PreparedStatement getPreparedStatement (String query) throws SQLException {
        PreparedStatement statement  = connection.prepareStatement(query);
        return statement;
    }
    
    public void closeConnection () throws SQLException {
        connection.close();
    }
    
    public static void setProperties() {
        String path = Main.getApplicationFilesPaths().getProperty("DbProperties");
        PropertiesFileManipulator.propertyLoad (path, dbProperties);
    }
    
    abstract public void rememberToCloseConnection();
}
