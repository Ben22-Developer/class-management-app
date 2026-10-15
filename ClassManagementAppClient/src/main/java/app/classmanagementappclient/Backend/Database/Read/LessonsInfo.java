package app.classmanagementappclient.Backend.Database.Read;

import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class LessonsInfo
{
    public String getLessonName (int classID, Connection connection)
    {
        UtilityClass urc = new UtilityClass();
        String query = "SELECT lessonname FROM classes WHERE classid = ?";
        String lessonName = "";
        
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            urc.setIntStatement (statement, classID, 1);
            lessonName = urc.getStatementStringResult(statement, 1);
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        return lessonName;
    }
}
