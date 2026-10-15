package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert;


import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
abstract public class ObjectsInsert {
    
    private final UtilityClass uc = new UtilityClass();
    private final Connection connection;
    private final Object object;
    private String errorMsg;
    
    public ObjectsInsert(Connection connection, Object object) throws SQLException {
        this.connection = connection;
        this.object = object;
    }

    public Connection getConnection() {
        return connection;
    }

    public Object getObject() {
        return object;
    }

    protected void setErrorMsg (String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public UtilityClass getUc() {
        return uc;
    }
    
    public abstract boolean insert() throws SQLException;
    public abstract void setStatement(PreparedStatement statement) throws SQLException;
}
