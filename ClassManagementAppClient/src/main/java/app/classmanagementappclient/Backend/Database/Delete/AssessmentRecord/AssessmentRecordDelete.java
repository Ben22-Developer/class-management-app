package app.classmanagementappclient.Backend.Database.Delete.AssessmentRecord;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class AssessmentRecordDelete extends DeleteData{

    private final int assessmentNum;
    private final int studentClassNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public AssessmentRecordDelete (int assessmentNum, int studentClassNum) throws SQLException {
        this.assessmentNum = assessmentNum;
        this.studentClassNum = studentClassNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("studentAssessmentRecordData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("AssessmentNum");
        clmNames.add("studentClassNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntsStatement(statement, new Integer[] {assessmentNum, studentClassNum}, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
    
}
