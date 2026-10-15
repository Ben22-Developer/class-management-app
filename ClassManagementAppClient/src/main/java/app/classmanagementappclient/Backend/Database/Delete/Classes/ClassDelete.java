package app.classmanagementappclient.Backend.Database.Delete.Classes;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassDelete extends DeleteData
{
    private final ArrayList<String> clmNames = new ArrayList<>();
    private final int classNum;
    
    public ClassDelete (int classNum) throws SQLException {
        super();
        this.classNum = classNum;
    }

    @Override
    public boolean deleteData() {
        return super.deleteData("classData");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmNames.add("classNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, classNum, 1);
            return uc.executeDMLStament(statement);
        }
    }   
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
