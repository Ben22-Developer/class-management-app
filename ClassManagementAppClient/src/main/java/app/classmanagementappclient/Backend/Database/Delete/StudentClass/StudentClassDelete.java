package app.classmanagementappclient.Backend.Database.Delete.StudentClass;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class StudentClassDelete extends DeleteData {

    
    private final int studentClassNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public StudentClassDelete(int studentClassNum) throws SQLException {
        this.studentClassNum = studentClassNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("studentClassData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("studentClassNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, studentClassNum, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
