package app.classmanagementappclient.Backend.Database.Read;

import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public interface Readables {
    public final UtilityClass uc = new UtilityClass();
    public void setConnection (Connection connection);
    public void executeRead () throws SQLException;
//    public void setStatement (PreparedStatement statement) throws SQLException;
}
