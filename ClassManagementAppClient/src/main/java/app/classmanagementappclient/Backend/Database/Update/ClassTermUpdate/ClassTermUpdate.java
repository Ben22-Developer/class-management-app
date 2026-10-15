package app.classmanagementappclient.Backend.Database.Update.ClassTermUpdate;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassTermUpdate extends UpdateData {

    private final ClassTerm currData;
    private final ClassTerm newData;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();
    private int classTermNum;

    public ClassTermUpdate(ClassTerm currData, ClassTerm newData) throws SQLException {
        this.currData = currData;
        this.newData = newData;
    }    
    
    public boolean updateDetails () {
        return super.updateData("classTermData");
    }         
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        String query = """
                        SELECT classTermNum FROM classTermData WHERE term = ? AND classYearNum = ?
                       """;
        int res;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, newData);
            res = uc.getStatementIntResult(statement, 1);
        }
        return res != Integer.MIN_VALUE;           
    }

    @Override
    public void duplicateCloser() {
        String string = newData.getClassYear().getClasss().getSchool().getSchoolName() +"/ "+ newData.getClassYear().getClasss().getClassName() + 
                        "/ Year " + newData.getClassYear().getYear() + "/ Term " + newData.getTerm() + " already exists!";
        super.setErrorMsg(string);
        super.rememberToCloseConnection();    
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("Term");
        try {
            setClassTermNum();
        } catch (SQLException ex) {
            System.getLogger(ClassTermUpdate.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void setClassTermNum () throws SQLException {
        String query = """
                        SELECT classTermNum FROM classTermData WHERE term = ? AND classYearNum = ?
                       """;
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, currData);
            classTermNum = uc.getStatementIntResult(statement, 1);
        }        
    }    
    
    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        query = query + "WHERE classTermNum = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            return uc.executeDMLStament(statement);
        }       
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }

    private void setStatement(PreparedStatement statement, ClassTerm classTerm) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { classTerm.getTerm(), classTerm.getClassYearNum() }, 1);
    }

    private void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { newData.getTerm(), classTermNum }, 1);
    }
}
