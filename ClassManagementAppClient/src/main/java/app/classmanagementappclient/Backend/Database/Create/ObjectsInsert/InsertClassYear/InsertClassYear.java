package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassYear;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertClassYear extends ObjectsInsert
{
    private final ClassYear year;
    private int classYearNum;
    
    public InsertClassYear(Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.year = (ClassYear) object;
    }

    @Override
    public boolean insert() throws SQLException 
    {
        boolean res = isClassYearInserted();
        
        if (res) {
            setErrorMsg();
            return false;
        }
        
        try {
            res = executeInsert();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            setErrorMsg();
            return false;
        }
        
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertClassYear.insert");
            return false;
        }
        
        setClassYearNum();
        return true;
    }

    
    private boolean executeInsert () throws SQLException {
        Connection connection = super.getConnection();
        String query = " INSERT INTO classYearData (year, classNum) VALUES (?,?) ";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, year.toString());        
    }
    
    private void setErrorMsg () {
        
        LoggedInTeacher loggedInTeacher = LoggedInTeacher.getLoggedInTeacher();
        
        super.setErrorMsg("\nTeacher: " + loggedInTeacher.getName() + 
                          "\nNum: " + loggedInTeacher.getTeacherNum() + 
                          "\nAlready teaches at: " + year.getClasss().getSchool().getSchoolName() +
                          "\nClass: "+ year.getClasss().getClassName() + " ClassNum: " + year.getClassNum() + 
                          "\nYear: " + year.getYear());        
    }
    
    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { year.getYear(),year.getClassNum() }, 1);
    }
    
    private boolean isClassYearInserted () throws SQLException {
        setClassYearNum();
        return classYearNum != Integer.MIN_VALUE;
    }

    public int getClassYearNum() {
        return classYearNum;
    }

    public void setClassYearNum() throws SQLException {
        UtilityClass uc = super.getUc();
        classYearNum = uc.getClassYearNum(super.getConnection(), year.getClassNum(), year.getYear());
    }
}
