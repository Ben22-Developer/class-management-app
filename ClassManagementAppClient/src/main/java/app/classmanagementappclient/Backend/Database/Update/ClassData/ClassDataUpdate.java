package app.classmanagementappclient.Backend.Database.Update.ClassData;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassDataUpdate extends UpdateData
{
    private final Classs newClassData;
    private final Classs currClassData;
    private int classNum;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();

    public ClassDataUpdate(Classs newClassData, Classs currClassData) throws SQLException {
        this.newClassData = newClassData;
        this.currClassData = currClassData;
    }

    public boolean updateDetails () {
        return super.updateData("classData");
    }
    
    @Override
    public boolean duplicateChecker () throws SQLException
    {
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        
        String query =
                """
                    SELECT classNum FROM classData WHERE BINARY className = ? AND schoolNum = ? 
                """;        
        int res;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, newClassData);
            res = uc.getStatementIntResult(statement, 1);
        }
        return res != Integer.MIN_VALUE;
    }
    
    @Override
    public void duplicateCloser () 
    {
        String string = newClassData.getSchool().getSchoolName() +"/ "+newClassData.getClassName() + " already exists!";    
        super.setErrorMsg(string);
        super.rememberToCloseConnection();        
    }
    
    @Override
    public void setDetailsToUpdate () {
        toUpdateClms.add("className");
        try {
            setClassNum();
        } catch (SQLException ex) {
            System.getLogger(ClassDataUpdate.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void setClassNum () throws SQLException 
    {
        Connection connection = super.getConnection();
        String query = 
                """
                    SELECT classNum FROM classData WHERE className = ? AND schoolNum = ? 
                """;
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, currClassData);
            classNum = uc.getStatementIntResult(statement, 1);
        }
    }    
    
    @Override
    public boolean updateDetails (String query) throws SQLException 
    {
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        String completeQuery = query + "WHERE classNum = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(completeQuery)) {
            setStatement(statement);
            return uc.executeDMLStament(statement);
        }
    }
    
    private void setStatement (PreparedStatement statement, Classs classData) throws SQLException {
        UtilityClass uc = super.getUc();
        String className = classData.getClassName();
        int schoolNum = classData.getSchoolNum();
        int index = uc.setStringStatement(statement, className, 1);
        uc.setIntStatement(statement, schoolNum, index);
    }

    private void setStatement (PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, newClassData.getClassName(), 1);
        uc.setIntStatement(statement, classNum, index);
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }
}
