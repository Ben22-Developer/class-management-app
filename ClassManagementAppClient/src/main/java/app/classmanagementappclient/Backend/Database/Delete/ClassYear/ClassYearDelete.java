package app.classmanagementappclient.Backend.Database.Delete.ClassYear;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassYearDelete extends DeleteData
{
    private final int classYearNum;
    private final ArrayList<String> clmNames = new ArrayList<>();

    public ClassYearDelete(int classYearNum) throws SQLException {
        this.classYearNum = classYearNum;
    }

    @Override
    public boolean deleteData() {
        return super.deleteData("classYearData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("classYearNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, classYearNum, 1);
            return uc.executeDMLStament(statement);
        }
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
