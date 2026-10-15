package app.classmanagementappclient.Backend.Database.Delete.ClassLesson;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassLessonDelete extends DeleteData 
{
    private final int classLessonNum;
    private final ArrayList<String> clmNames = new ArrayList<>();    

    public ClassLessonDelete(int classLessonNum) throws SQLException {
        this.classLessonNum = classLessonNum;
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData("classLessonData");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNames.add("classLessonNum");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException {
        UtilityClass uc = super.getUc();
        try (PreparedStatement statement = super.getPreparedStatement(query)) {
            uc.setIntStatement(statement, classLessonNum, 1);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNames;
    }
}
