package app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.ReadAllProjects;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
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
public class ReadAllProjectsProxy implements ReadStringOfStringInterface {

    private final List<List<String>> data = new ArrayList<>();
    private final String query;

    public ReadAllProjectsProxy(String query) {
        this.query = query;
    }
    
    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
}
