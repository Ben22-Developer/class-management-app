package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertSchool;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertSchool extends ObjectsInsert
{
    private final School school;
    private int schoolNum;

    public InsertSchool(Connection connection, Object object) throws SQLException {
        super(connection, object);
        school = (School) object;
    }

    @Override
    public boolean insert() throws SQLException 
    {
        if (isSchoolInserted()) {
            setErrorMessage();
            return false;
        }
        boolean res = false;
        try {
            res = executeInsert();
        }catch (SQLIntegrityConstraintViolationException e) {
            setErrorMessage();
            return false;
        }
        
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertClass.insert");
            return false;
        }
        
        setSchoolNum();
        return res;
    }
    
    private boolean executeInsert() throws SQLException {
        Connection connection = super.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO schoolData(schoolName, teacherNum) VALUES (?, ?)");
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement(statement, "School " + school.getSchoolName());
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, school.getSchoolName(), 1);
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
    }
    
    private boolean isSchoolInserted () throws SQLException {
        setSchoolNum();
        return getSchoolNum() != Integer.MIN_VALUE;
    }

    public int getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum() throws SQLException {
        UtilityClass uc = super.getUc();
        schoolNum = uc.getSchoolNum(super.getConnection(), school);
    }
    
    public void setErrorMessage () {
        super.setErrorMsg("\nTeacher: " + LoggedInTeacher.getLoggedInTeacher().getName() + " Num: " + LoggedInTeacher.getLoggedInTeacher().getTeacherNum() + 
                          "\nAlready teaches at: " + school.getSchoolName() +" SchoolNum: " + getSchoolNum());    
    }
}
