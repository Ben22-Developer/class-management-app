package app.classmanagementappclient.Backend.Database.Delete.ClassTerm;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassTermDelete extends DeleteData 
{
    private final int classTermNum;
    private final ArrayList<String> clmNames = new ArrayList<>();

    public ClassTermDelete (int classTermNum) throws SQLException {
        this.classTermNum = classTermNum;
    }

    @Override
    public boolean deleteData() {
        return super.deleteData("classTermData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("classTermNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, classTermNum, 1);
            return uc.executeDMLStament(statement);
        }
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }    
}
