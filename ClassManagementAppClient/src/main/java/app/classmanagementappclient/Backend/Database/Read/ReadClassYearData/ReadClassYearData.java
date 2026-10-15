package app.classmanagementappclient.Backend.Database.Read.ReadClassYearData;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadClassYearData implements Readables {
    private final ReadDataInterface proxy;
    private Connection connection;

    public ReadClassYearData(ReadDataInterface proxy) {
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
