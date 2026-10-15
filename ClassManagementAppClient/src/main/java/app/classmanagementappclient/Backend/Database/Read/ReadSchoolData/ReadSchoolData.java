package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadSchoolData implements Readables
{
    private Connection connection;
    private final ReadDataInterface proxy;
    
    public ReadSchoolData (ReadDataInterface schoolDataInterface) {
        this.proxy = schoolDataInterface;
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
