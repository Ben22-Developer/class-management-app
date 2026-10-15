package app.classmanagementappclient.Backend.Database.Read;

import app.classmanagementappclient.Backend.Database.DatabaseManipulation;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ReadData extends DatabaseManipulation
{    
    private final Readables readable;
    
    public ReadData (Readables readable) throws SQLException {
        super();
        this.readable = readable;
    }
    
    public void execute () 
    {    
        readable.setConnection(super.getConnection());
        try {            
            readable.executeRead();
        }
        catch (SQLException ex) {
            throw new RuntimeException();
        }
        finally {
            rememberToCloseConnection();
        }
    }

    @Override
    public void rememberToCloseConnection() {
        try {
            super.closeConnection();
        }catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
}
