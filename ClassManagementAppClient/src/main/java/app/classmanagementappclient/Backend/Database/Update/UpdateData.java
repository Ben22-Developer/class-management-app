package app.classmanagementappclient.Backend.Database.Update;

import app.classmanagementappclient.Backend.Database.DatabaseManipulation;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
abstract public class UpdateData extends DatabaseManipulation
{
    private final LoggedInTeacher teacher = LoggedInTeacher.getLoggedInTeacher();
    private final UtilityClass uc = new UtilityClass();
    private final UpdateDataUtility udu = new UpdateDataUtility();
    private String errMsg;
    
    public UpdateData () throws SQLException {
        super();
    }

    public LoggedInTeacher getLoggedInTeacher() {
        return teacher;
    }

    public UtilityClass getUc() {
        return uc;
    }

    public UpdateDataUtility getUdu() {
        return udu;
    }
    
    protected boolean updateData (String tableName) 
    {
        boolean suceeded;
        try 
        {
            boolean isDuplicate = duplicateChecker();
            
            if (isDuplicate) {
                duplicateCloser(); // remember to close the connection in that method
                return false;
            }
            setDetailsToUpdate();
            ArrayList<String>toUpdateClms = getUpdateClms();
            String query = getUpdateQueryStatements(tableName, toUpdateClms);
            suceeded = updateDetails(query);
            
            if (!suceeded && errMsg == null) 
                    setErrorMsg("<html><p> Unexpected error, no update done!<br>If it continues to persist then contact the developer!</p></html>");
        } 
        catch (SQLIntegrityConstraintViolationException e) {
            suceeded = false;
            setErrorMsg("Update fail!\nYou're trying to update the data which already exists.");
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        finally {
            rememberToCloseConnection();
        }
        
        return suceeded;        
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
        setErrorMsg("Incorrect pin entered!");
        rememberToCloseConnection();        
    }    

    public String getUpdateQueryStatements (String tableName, ArrayList<String> clmNamesToUpdate)
    {
        StringBuilder updateClause = getUpdateClause(tableName, clmNamesToUpdate);
        return updateClause.toString();
    }
    
    private StringBuilder getUpdateClause (String tableName, ArrayList<String> clmNamesToUpdate)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(tableName).append(" SET ");
        clmsIterator(builder, clmNamesToUpdate);
        return builder;
    }
    
    private void clmsIterator (StringBuilder builder, ArrayList<String> clms) 
    {
        for (int i = 0; i < clms.size(); i++) {
            appendStringBuilders(builder, clms.get(i), i == (clms.size() - 1));
        }        
    }
    
    private void appendStringBuilders (StringBuilder builder, String appendStr, boolean isLastIteration) 
    {
        builder.append(appendStr);
        if (isLastIteration) {
            builder.append(" = ? ");
        }
        else {
            builder.append(" = ?, ");
        }
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
    
    public void setErrorMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public String getErrorMsg() {
        return this.errMsg;
    }
    
    public abstract boolean duplicateChecker() throws SQLException;
    public abstract void duplicateCloser();
    public abstract void setDetailsToUpdate();
    public abstract boolean updateDetails (String query) throws SQLException;
    public abstract ArrayList<String> getUpdateClms();
}
