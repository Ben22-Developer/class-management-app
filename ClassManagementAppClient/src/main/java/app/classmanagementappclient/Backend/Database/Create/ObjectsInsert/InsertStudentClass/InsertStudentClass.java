package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudentClass;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudent.InsertStudent;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertStudentClass extends ObjectsInsert
{
    private final StudentClass studentClass;
    private final String viewName = "studentClassData";
    private int studentClassNum;
    
    public InsertStudentClass (Connection connection, Object object) throws SQLException {
        super(connection, object);
        this.studentClass = (StudentClass) object;
    }

    @Override
    public boolean insert() throws SQLException 
    {
        boolean res = isStudentInserted();
        if (!res) {
            res = insertNewStudent();
            if (!res) 
                return false;
        }
        
        res = isStudentClassInserted();
        if (res) {
            setErrMsg();
            return false;
        }
        
        try {
            res = executeInsert();
        }catch (SQLIntegrityConstraintViolationException e) {
            setErrMsg();
            return false;
        } 
        
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertStudentClass.insert");
            return false;
        }
        
        setStudentClassNum();
        return true;        
    }
    
    private boolean executeInsert () throws SQLException {
        String query = "INSERT INTO "+viewName+"(studentNum,classTermNum) VALUES(?,?)";
        PreparedStatement statement = super.getConnection().prepareStatement(query);
        setStatement (statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, studentClass.toString());
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        uc.setIntsStatement(statement, new Integer[] { studentClass.getStudentNum(), studentClass.getClassTerNum() }, 1);
    }
    
    private boolean isStudentInserted () throws SQLException {
        UtilityClass uc = super.getUc();
        studentClass.setStudentNum( uc.getStudentNum(super.getConnection(), studentClass.getStudent()) );
        return Integer.MIN_VALUE != studentClass.getStudentNum();
    }
    
    private boolean insertNewStudent () throws SQLException {
        InsertStudent insertStudent = new InsertStudent(super.getConnection(), studentClass.getStudent());
        boolean res = insertStudent.insert();
        studentClass.setStudentNum( insertStudent.getStudentNum() ); 
        return res;
    }

    private boolean isStudentClassInserted () throws SQLException {
        setStudentClassNum();
        return Integer.MIN_VALUE != studentClassNum;
    }
    
    public int getStudentClassNum() {
        return studentClassNum;
    }

    public void setStudentClassNum() throws SQLException {
        UtilityClass uc = super.getUc();
        studentClassNum = uc.getStudentClassNum(super.getConnection(), studentClass.getStudentNum(), studentClass.getClassTerNum());
    }
    
    public void setErrMsg () {
        super.setErrorMsg("StudentID: " + studentClass.getStudent().getStudentID() + "\nIs alredy inserted at classTerm: " + studentClass.getClassTerNum());
    }
}
