package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacher;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Backend.Security.SecurityUtility;
import java.sql.*;

/**
 *
 * @author BEN
 */
public class InsertTeacher extends ObjectsInsert 
{
    private final Teacher teacher;
    private final String viewName = "teacherPersonalData";
    
    public InsertTeacher (Connection connection, Object object) throws SQLException {
        super (connection, object);
        teacher = (Teacher) object;
    }

    @Override
    public boolean insert() throws SQLException {
        if (isTeacherInDB()) {
            setErrorMsg("User with email " + teacher.getEmail() + " already exist!");
            return false;
        }
        boolean res = false;
        try {
            res = executeInsert();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            setErrorMsg("User with email " + teacher.getEmail() + " already exist!");
            return false;
        }
        
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertClass.insert");
            return false;
        }
        teacher.setTeacherNum( getTeacherNum(super.getConnection(), teacher) );
        return true;
    }

    private boolean executeInsert () throws SQLException {
        Connection connection = super.getConnection();
        String query = "INSERT INTO " + viewName +" (teacherName, teacherPhoneNumber, teacherEmail, teacherPin) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, teacher.getName());
    }    
    
    private boolean isTeacherInDB () throws SQLException {
        return getTeacherNum(super.getConnection(), teacher) != Integer.MIN_VALUE;
    }
    
    public Teacher getTeacher (Connection connection, Teacher teacher) throws SQLException {
        UtilityClass uc = super.getUc();
        
        PreparedStatement statement = connection.prepareStatement("SELECT teacherNum, teacherName, teacherPhonenumber, teacherEmail, teacherPin FROM " + viewName +" WHERE teacherEmail=? AND teacherName=? AND teacherPhonenumber=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        uc.setStringsStatement(statement, new String[] { teacher.getEmail(), teacher.getName(), teacher.getPhoneNumber() }, 1);
        
        Teacher dbTeacher = uc.getTeacher(statement);
        
        if (dbTeacher == null || !SecurityUtility.encrypted_NonEncryptedCheckUp(dbTeacher.getEncryptedPin(), teacher.getPin()))
            return null;
        
        return dbTeacher;
    }
    
    public int getTeacherNum (Connection connection, Teacher teacher) throws SQLException {
        UtilityClass uc = super.getUc();
        PreparedStatement statement = connection.prepareStatement("SELECT teacherNum FROM " + viewName +" WHERE teacherEmail = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        uc.setStringsStatement(statement, new String[] { teacher.getEmail() }, 1);
        return uc.getStatementIntResult(statement, 1);
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {        
        UtilityClass uc = super.getUc();
        uc.setStringsStatement(statement, new String[] {teacher.getName(),teacher.getPhoneNumber(), teacher.getEmail(), teacher.getEncryptedPin()}, 1);  
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
