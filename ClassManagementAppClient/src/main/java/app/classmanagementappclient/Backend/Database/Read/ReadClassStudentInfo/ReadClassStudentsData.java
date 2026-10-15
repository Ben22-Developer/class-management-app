package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadClassStudentsData implements Readables
{
    private final ReadDataInterface proxy;
    private Connection connection;

    public ReadClassStudentsData(ReadDataInterface proxy) {
        this.proxy = proxy;
    }
    
    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void executeRead() throws SQLException {
        String query = proxy.getQuery();
        PreparedStatement statement = connection.prepareStatement(query);
        proxy.readData(statement, uc);        
    }
}
