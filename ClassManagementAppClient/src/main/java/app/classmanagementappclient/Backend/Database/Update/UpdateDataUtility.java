package app.classmanagementappclient.Backend.Database.Update;

import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class UpdateDataUtility 
{
    public int setComplexStatements (PreparedStatement statement, LinkedHashMap <String, String> dataToDataType, UtilityClass uc, int index) 
    {
        Set<Entry<String, String>> entrySet = dataToDataType.entrySet();
        
        try {
            for (Entry<String, String> entry : entrySet) {
                String data = entry.getKey();
                String dataType = entry.getValue();
                index = setStatement(statement, data, dataType, index, uc);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        return index;
    }
    
    private int setStatement (PreparedStatement statement, String data, String dataType, int index, UtilityClass uc) throws SQLException
    {
        boolean isInt = dataType.equals("Integer");
        boolean isStr = dataType.equals("String");
        boolean isDate = dataType.equals("LocalDate");
        
        if (isInt) {
            return setIntegerStatement(statement, data, uc, index);
        }
        else if (isStr) {
            return setStringStatement(statement, data, uc, index);
        }
        else if (isDate) {
            return setDateStatement(statement, data, uc, index);
        }
        return 0;
    }
    
    private int setIntegerStatement (PreparedStatement statement, String data, UtilityClass uc, int index) throws SQLException 
    {
        int intData = Integer.parseInt(data);
        return uc.setIntStatement(statement, intData, index);
    } 
    
    private int setStringStatement (PreparedStatement statement, String data, UtilityClass uc, int index) throws SQLException {
        return uc.setStringStatement(statement, data, index);
    } 

    private int setDateStatement (PreparedStatement statement, String data, UtilityClass uc, int index) throws SQLException {
        
        LocalDate date = LocalDate.parse(data);
        return uc.setDateStatement(statement, java.sql.Date.valueOf(date), index);
    }     
}
