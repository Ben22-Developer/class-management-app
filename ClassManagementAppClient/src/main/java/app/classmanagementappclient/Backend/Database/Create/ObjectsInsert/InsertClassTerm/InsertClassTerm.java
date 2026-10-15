package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassTerm;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertClassTerm extends ObjectsInsert
{
    private final ClassTerm term;
    private int classTermNum;

    public InsertClassTerm(Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.term = (ClassTerm) object;
    }

    @Override
    public boolean insert() throws SQLException
    {
        boolean res = isClassTermInserted();
        if (res) {
            setErrorMsg();
            return false;
        }
        
        try {
            res = executeInsert();
        }catch (SQLIntegrityConstraintViolationException e) {
            setErrorMsg();
            return false;            
        }
        
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertClassTerm.insert");
            return false;
        }
        
        setClassTermNum();
        return true;        
    }
    
    private boolean executeInsert () throws SQLException {
        Connection connection = super.getConnection();
        String query = "INSERT INTO classTermData (term, classYearNum) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, term.toString());        
    }
    
    private void setErrorMsg () {
        super.setErrorMsg("\nTeacher: " + LoggedInTeacher.getLoggedInTeacher().getName() + " Num: " + LoggedInTeacher.getLoggedInTeacher().getTeacherNum() + 
                    "\nAlready teaches at: " + term.getClassYear().getClasss().getSchool().getSchoolName() +
                    "\nClass: "+ term.getClassYear().getClasss().getClassName() + " ClassNum: " + term.getClassYear().getClassNum() + 
                    "\nYear: " + term.getClassYear().getYear() + 
                    "\nTerm: " + term.getTerm());        
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { term.getTerm(), term.getClassYearNum() }, 1);
    }

    private boolean isClassTermInserted() throws SQLException {
        setClassTermNum();
        return classTermNum != Integer.MIN_VALUE;
    }    
    
    public int getClassTermNum() {
        return classTermNum;
    }

    public void setClassTermNum() throws SQLException {
        UtilityClass uc = super.getUc();
        classTermNum =uc.getClassTermNum(super.getConnection(), term.getClassYearNum(), term.getTerm());
    }
}
