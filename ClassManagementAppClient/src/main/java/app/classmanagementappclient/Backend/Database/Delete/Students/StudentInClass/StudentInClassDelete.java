package app.classmanagementappclient.Backend.Database.Delete.Students.StudentInClass;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Student.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class StudentInClassDelete extends DeleteData
{
    private final ArrayList<String> clmNamesToDelete = new ArrayList<>();
    private ArrayList<Integer> classIDs;
    private Student student;
    
    public StudentInClassDelete () throws SQLException {
        super();
    }
    
    @Override
    public boolean deleteData() {
        return super.deleteData ("teachers_students");
    }
    
    @Override
    public void setClmNamesToCheck() {
        clmNamesToDelete.add("studentid");
    }

    @Override
    public boolean executeQuery(String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        query = getFormattedQuery(query, uc);
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            setStatement(statement, uc);
            return uc.executeDMLStament(statement);
        }
    }
    
    private String getFormattedQuery (String query, UtilityClass uc) {
        return uc.getWildCardsFormattedQuery(query, classIDs.size(), "AND classid IN (");
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc) throws SQLException 
    {
        int index = uc.setStringStatement(statement, student.getStudentID(), 1);
        uc.setIntsStatement(statement, classIDs.toArray(), index);
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToDelete;
    }    

    public void setClassIDs(ArrayList<Integer> classIDs) {
        this.classIDs = classIDs;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
