package app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadAssessmentRecordData implements Readables{

    private final ReadDataInterface proxy;
    private Connection connection;
    
    public ReadAssessmentRecordData  (ReadDataInterface proxy) {
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
        proxy.setStatement(statement, uc);
        proxy.readData(statement, uc);
    }
}
