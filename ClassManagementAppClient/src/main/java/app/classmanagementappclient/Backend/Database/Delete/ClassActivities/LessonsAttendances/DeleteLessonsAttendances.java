package app.classmanagementappclient.Backend.Database.Delete.ClassActivities.LessonsAttendances;

import app.classmanagementappclient.Backend.Database.Delete.ClassActivities.DeleteClassActivities;
import app.classmanagementappclient.Backend.Database.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author BEN
 */
public class DeleteLessonsAttendances extends DeleteClassActivities
{
    private final ArrayList<String> clmNamesToDelete = new ArrayList<>();
    
    public DeleteLessonsAttendances() throws SQLException {
        super();
    }    
    
    @Override
    public boolean deleteData() {
        return super.deleteData("lessonsattendances");
    }

    @Override
    public void setClmNamesToCheck() {
        clmNamesToDelete.add("classid");
    }
    
    @Override
    public boolean executeQuery(String query) throws SQLException
    {
        ArrayList<String> studentIDs = super.getStudentIDs();
        
        if (studentIDs.isEmpty())
            return deleteClassActivity(query);
        else
            return deleteStudentClassAttendance(query);
    }
    
    protected boolean deleteClassActivity (String query) throws SQLException
    {
        UtilityClass uc = super.getUc();
        query = uc.getWildCardsFormattedQuery(query, super.getDates().size(), " AND dates IN (");
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            setDeleteClassAttendanceStatement(statement, uc);
            return uc.executeDMLStament(statement);
        }
    }    
    
    private void setDeleteClassAttendanceStatement (PreparedStatement statement, UtilityClass uc) throws SQLException 
    {
        int index = uc.setIntStatement(statement, super.getClassID(), 1);
        uc.setDatesStatement(statement, super.getDates().toArray(),index);
    }    
    
    private boolean deleteStudentClassAttendance (String query) throws SQLException
    {
        if (super.getDates().size() != super.getStudentIDs().size()) { //a defence to avoid unexpected outputs
            setErrMsgs();
            return false;
        }
        
        UtilityClass uc = super.getUc();  
        boolean bool = false;
        query = query + " AND studentid = ? AND dates = ?";
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            int classID = super.getClassID();
            int index = uc.setIntStatement(statement, classID, 1);
            int i;
            
            for (i = 0; i < super.getDates().size(); i++) 
            {
                setDeleteStudentClassAttendanceStatement(statement, uc, i, index);
                bool = uc.executeDMLStament(statement);
                if (!bool)
                    break;
            }
            
            if (i != super.getDates().size())
                super.setErrMsg("Unexpected error happened while removing student from class attendance!");
        }
        return bool;
    }
    
    private void setDeleteStudentClassAttendanceStatement (PreparedStatement statement, UtilityClass uc, int arrIndex, int statementIndex) throws SQLException
    {
        LocalDate date = super.getDates().get(arrIndex);
        String studentID = super.getStudentIDs().get(arrIndex);
        
        statementIndex = uc.setStringStatement(statement, studentID, statementIndex);
        uc.setDateStatement(statement, java.sql.Date.valueOf(date), statementIndex);
    }
    
    private void setErrMsgs () {
        Logger.getLogger(DeleteLessonsAttendances.class.getName()).info("Application Error location");
        setErrMsg("Dates's size is supposed to be equal to the studentID's suize but it's not the case!");
    }
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToDelete;
    }    
}
