package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadClassLessonData implements Readables{
    
    private final ReadDataInterface proxy;
    private Connection connection;

    public ReadClassLessonData(ReadDataInterface proxy) {
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
