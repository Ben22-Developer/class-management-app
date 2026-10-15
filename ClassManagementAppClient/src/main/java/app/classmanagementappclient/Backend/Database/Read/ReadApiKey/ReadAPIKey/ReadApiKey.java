package app.classmanagementappclient.Backend.Database.Read.ReadApiKey.ReadAPIKey;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadApiKey implements ReadStringInterface{

    private final List<String> apiKey = new ArrayList<>();
    
    @Override
    public String getQuery() { //  teacherPersonalData
        return "SELECT apiKey FROM teacher_ApiKeyView WHERE teacherNum = ?";
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        uc.getStringsStatementByClmRes(statement, 1, apiKey);
        
        if (apiKey.size() > 1) 
            throw new RuntimeException("Expecting 1 api key which isn't the case at ReadApiKey.readData");
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
    
    @Override
    public List<String> getData() {
        return apiKey;
    }    
}
