package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentByStudentClassNum;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadStudentByStudentClassNum implements ReadStringOfStringInterface {

    private final int studentClassNum;
    private final List<List<String>> data = new ArrayList<>();

    public ReadStudentByStudentClassNum (int studentClassNum) {
        this.studentClassNum = studentClassNum;
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
                SELECT studentPersonalData.studentID, studentPersonalData.studentName FROM studentPersonalData
                JOIN studentClassData ON studentPersonalData.studentNum = studentClassData.studentNum
                WHERE studentClassData.studentClassNum = ?
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        
        setStatement(statement, uc);
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, studentClassNum, 1);
    }    
    
}