package app.classmanagementappclient.Backend.Database.Read.ReadTeacherData;

import app.classmanagementappclient.Backend.Database.Read.Readables;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacherPersonalData implements Readables
{    
    private List<String> data;
    private Connection connection;
    private final UtilityClass uc = new UtilityClass();
    
    public ReadTeacherPersonalData () throws SQLException {
        super();
    }    

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }    
    
    @Override
    public void executeRead () throws SQLException {
        String query = "SELECT teacherName,teacherPhoneNumber, teacherEmail, teacherPin FROM teacherPersonalData WHERE teacherNum = ?"; 
        List<List<String>> lists = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query) ) {
            setStatement(statement);
            uc.getStringsStatementByRowRes(statement, lists);
            data = lists.getFirst();
        }
    }
    
    public void setStatement (PreparedStatement statement) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
    
    public List<String> getData() {
        return data;
    }
}
