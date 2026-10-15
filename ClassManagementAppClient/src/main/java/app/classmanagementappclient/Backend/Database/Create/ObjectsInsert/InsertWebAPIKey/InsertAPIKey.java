/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertWebAPIKey;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.WebObject.ApiKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class InsertAPIKey extends ObjectsInsert  
{
    private final ApiKey apiKey;
    private final String viewName = "teacherPersonalData";

    public InsertAPIKey(Connection connection, Object object) throws SQLException {
        super(connection, object);
        apiKey = (ApiKey) object;
    }
    
    @Override
    public boolean insert() throws SQLException {
        
        String query = "INSERT INTO teacher_ApiKeyView VALUES(?,?)";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement(statement, "API KEY: " + apiKey.key() + " inserted");
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = getUc();
        int index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
        uc.setStringStatement(statement, apiKey.key(), index);
    }
    
}
