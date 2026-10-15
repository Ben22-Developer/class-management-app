package app.classmanagementappclient.Backend.Database.Delete.Assessment;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class LessonAssessmentDelete extends DeleteData {

    private final int assessmentNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public LessonAssessmentDelete (int assessmentNum) throws SQLException {
        this.assessmentNum = assessmentNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("assessmentData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("assessmentNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, assessmentNum, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
