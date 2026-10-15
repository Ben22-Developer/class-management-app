package app.classmanagementappclient.Backend.Database.Delete.Schools;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class SchoolDelete extends DeleteData
{   
    private final int schoolNum;
    private final ArrayList<String> clmNames = new ArrayList<>();
    
    public SchoolDelete (int schoolNum) throws SQLException {
        super();
        this.schoolNum = schoolNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("schoolData");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmNames.add("schoolNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        boolean b = false;
        Connection connection = super.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, uc);
            b = uc.executeDMLStament(statement);
        }catch (SQLException e) {
            throw new SQLException();
        }
        return b;
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, schoolNum, 1);
    }    
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }    
}
