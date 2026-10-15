package app.classmanagementappclient.Backend.Database.Delete;

import app.classmanagementappclient.Backend.Database.DatabaseManipulation;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
abstract public class DeleteData extends DatabaseManipulation
{
    private final LoggedInTeacher loggedInTeacher = LoggedInTeacher.getLoggedInTeacher();
    private final UtilityClass uc = new UtilityClass();   
    private final DeleteUtility du = new DeleteUtility();
    private String errMsg; 
    
    public DeleteData () throws SQLException {
        super();
    }
    
    protected boolean deleteData (String tableName) 
    {
        boolean deleted;
        try {
            setClmNamesToCheck();
            String query = getDeleteQuery(tableName);
            deleted = executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        finally {
            rememberToCloseConnection();
        }
        
        if (!deleted)
            setErrMsg("Unexpected errror!\nContact the developer if it persists!");
        
        return deleted;
    }
    
    public String getDeleteQuery (String tableName)
    {
        ArrayList<String> clmNamesToDelete = getClmNamesToCheck();
        StringBuilder deleteClause = new StringBuilder();
        
        if (clmNamesToDelete.isEmpty()) 
            setDefaultTableClause(deleteClause, tableName);
        else 
            deleteRowsClause(deleteClause, tableName, clmNamesToDelete);
        
        return deleteClause.toString();
    }
    
    private void deleteRowsClause (StringBuilder deleteClause, String tableName, ArrayList<String> clmNamesToDelete)
    {
        setDefaultTableClause (deleteClause, tableName);
        deleteClmClause(deleteClause, clmNamesToDelete);
    }
    
    private void setDefaultTableClause (StringBuilder deleteClause, String tableName) {
        deleteClause.append("DELETE FROM ").append(tableName);
    }
    
    protected void deleteClmClause (StringBuilder deleteClause, ArrayList<String> clmNamesToDelete)
    {
        deleteClause.append(" WHERE ");
        
        for (int i = 0; i < clmNamesToDelete.size(); i++) 
        {
            if (i == clmNamesToDelete.size() - 1)
                deleteClause.append(clmNamesToDelete.get(i)).append(" = ?");
            else
                deleteClause.append(clmNamesToDelete.get(i)).append(" = ? AND ");
        }
    }
    
    public boolean pinCheck (String pin)
    {
        boolean bool = uc.pinCheck(pin, super.getConnection());
        if (!bool) {
            pinError();
        }
        return bool;
    }
    
    public void pinError () {
        setErrMsg("Incorrect pin entered!");
        rememberToCloseConnection();        
    } 

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public DeleteUtility getDu() {
        return du;
    }

    public LoggedInTeacher getLoggedInTeacher() {
        return loggedInTeacher;
    }

    public UtilityClass getUc() {
        return uc;
    }

    public String getErrorMsg() {
        return errMsg;
    }
    
    
    
    @Override
    public void rememberToCloseConnection() {
        try {
            super.closeConnection();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
    
    public abstract boolean deleteData ();
    public abstract void setClmNamesToCheck();
    public abstract boolean executeQuery (String query) throws SQLException;     
    public abstract ArrayList<String> getClmNamesToCheck();
}
