package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.Duplicates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class DuplicatesHandler 
{
    public boolean duplicateCheck (PreparedStatement statement) throws SQLException
    {
        ResultSet set = statement.executeQuery();
        boolean bool = false;
        
        while (set.next()) 
        {
            bool = true;
            
            ResultSetMetaData metaData = set.getMetaData();
            System.err.println("You were about to make a duplicate entry! On:\n");
            
            for (int i = 1; i <= metaData.getColumnCount(); i++) 
            {
                System.out.print(metaData.getColumnName(i) +"\t");
            }
            
            System.out.println();
            
            for (int i = 1; i <= metaData.getColumnCount(); i++) 
            {
                System.out.print(set.getObject(i) +"\t");
            }
            System.out.println();
        }
        
        return bool;
    }
}
