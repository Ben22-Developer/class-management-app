package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadDataInterface {
    public String getQuery();
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException;
    public void setStatement (PreparedStatement statement, UtilityClass uc) throws SQLException;
    public List<?> getData();
}
