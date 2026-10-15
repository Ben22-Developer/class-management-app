package app.classmanagementappclient.Backend.Database.Read.ReadStatistics;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.Readables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadStatistics implements Readables{

    private final ReadDataInterface contract;
    private Connection connection;
            
    public ReadStatistics(ReadDataInterface contract) {
        this.contract = contract;
    }
    
    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void executeRead() throws SQLException {
        String query = contract.getQuery();
        PreparedStatement statement = connection.prepareStatement(query);
        contract.setStatement(statement, uc);
        contract.readData(statement, uc);
    }
}
