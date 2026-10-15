package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClass;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertClass extends ObjectsInsert
{   
    private final Classs classs;
    private int classNum;
    
    public InsertClass (Connection connection, Object object) throws SQLException {
        super(connection, object);
        classs = (Classs) object;
    }   

    @Override
    public boolean insert() throws SQLException
    {
        if (isClassInserted()) {
            super.setErrorMsg("\nTeacher: " + LoggedInTeacher.getLoggedInTeacher().getName() + "\nNum: " + LoggedInTeacher.getLoggedInTeacher().getTeacherNum() + "\nAlready teaches at: " + classs.getSchool().getSchoolName() +"\nClass: "+ classs.getClassName() + "\nClassNum: " + getClassNum());
            return false;
        }
        
        
        boolean res = false;
        try {
            res = executeInsert();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            super.setErrorMsg("\nTeacher: " + LoggedInTeacher.getLoggedInTeacher().getName() + "\nNum: " + LoggedInTeacher.getLoggedInTeacher().getTeacherNum() + "\nAlready teaches at: " + classs.getSchool().getSchoolName() +"\nClass: "+ classs.getClassName() + "\nClassNum: " + getClassNum());
            return false;
        }
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertClass.insert");
            return false;
        }
        
        setClassNum();
        return true;
    }
    
    private boolean executeInsert() throws SQLException {
        Connection connection = super.getConnection();
        String query = "INSERT INTO classData (className, schoolNum) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, classs.toString());    
    }
    

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, classs.getClassName(), 1);
        uc.setIntStatement(statement, classs.getSchoolNum(), index);
    }
    
    private boolean isClassInserted () throws SQLException {
        setClassNum();
        return getClassNum() != Integer.MIN_VALUE;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum() throws SQLException {  
        UtilityClass uc = super.getUc();
        classNum = uc.getClassNum(super.getConnection(), classs.getSchoolNum(), classs.getClassName());
    }
}
