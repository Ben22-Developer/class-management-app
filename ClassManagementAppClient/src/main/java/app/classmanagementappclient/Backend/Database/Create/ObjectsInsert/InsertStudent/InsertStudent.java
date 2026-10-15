package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudent;


import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


/**
 *
 * @author BEN
 */
public class InsertStudent extends ObjectsInsert 
{
    private final Student student;
    private final String viewName = "studentPersonalData";
    private int studentNum;
    
    public InsertStudent (Connection connection, Object object) throws SQLException {
        super(connection, object);
        student = (Student) super.getObject();
    }   

    @Override
    public boolean insert() throws SQLException
    {
        boolean res = isDuplicate();
        
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
            super.setErrorMsg("Application Error!\nAt InsertStudent.insert");
            return false;
        }
        setStudentNum();
        return true;
    }
    
    private boolean isDuplicate () throws SQLException {
        setStudentNum();
        return studentNum != Integer.MIN_VALUE;
    }
    
    private boolean executeInsert () throws SQLException {
        String query = "INSERT INTO "+viewName+"(studentID, studentName, TeacherNum) VALUES(?,?,?)";
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        setStatement (statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, student.getStudentName()); 
    }
    
    private void setErrorMsg () {
        super.setErrorMsg("You were about to re-register student:\nStudent with Student ID: " +student.getStudentID() + " exists!");
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        int index = uc.setStringsStatement(statement, new String[] { student.getStudentID(), student.getStudentName() } , 1);
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum() throws SQLException {
        UtilityClass uc = super.getUc();
        Connection connection = super.getConnection();
        studentNum = uc.getStudentNum(connection, student);
    }
}
