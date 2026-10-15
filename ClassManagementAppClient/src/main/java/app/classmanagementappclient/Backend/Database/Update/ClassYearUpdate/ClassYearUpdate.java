package app.classmanagementappclient.Backend.Database.Update.ClassYearUpdate;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassYearUpdate extends UpdateData{

    private final ClassYear currClassYearData;
    private final ClassYear newClassYearData;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();
    private int classYearNum;

    public ClassYearUpdate(ClassYear currData, ClassYear newData) throws SQLException {
        this.currClassYearData = currData;
        this.newClassYearData = newData;
    }
    
    public boolean updateDetails () {
        return super.updateData("classYearData");
    }     
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        String query = """
                        SELECT classYearNum FROM classYearData WHERE year = ? AND classNum = ?
                       """;
        int res;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, newClassYearData);
            res = uc.getStatementIntResult(statement, 1);
        }
        return res != Integer.MIN_VALUE;        
    }

    @Override
    public void duplicateCloser() {
        String string = newClassYearData.getClasss().getSchool().getSchoolName() +"/ "+newClassYearData.getClasss().getClassName() + "/ Year" + newClassYearData.getYear() + " already exists!";    
        super.setErrorMsg(string);
        super.rememberToCloseConnection();     
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("Year");
        try {
            setClassYearNum();
        } catch (SQLException ex) {
            System.getLogger(ClassYearUpdate.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void setClassYearNum () throws SQLException {
        String query = """
                        SELECT classYearNum FROM classYearData WHERE year = ? AND classNum = ?
                       """;
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, currClassYearData);
            classYearNum = uc.getStatementIntResult(statement, 1);
        }        
    }
    
    @Override
    public boolean updateDetails (String query) throws SQLException 
    {
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        query = query + "WHERE classYearNum = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            return uc.executeDMLStament(statement);
        }        
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }

    private void setStatement(PreparedStatement statement, ClassYear classYear) throws SQLException {
        UtilityClass uc = super.getUc();        
        uc.setIntsStatement(statement, new Integer[] { classYear.getYear(), classYear.getClassNum() }, 1);
    }
    
    private void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();        
        uc.setIntsStatement(statement, new Integer[] { newClassYearData.getYear(), classYearNum }, 1);
    }
}
