package app.classmanagementappclient.Backend.Database.Delete.ClassActivities.LessonsAssessments;

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
public class DeleteLessonsAssessments extends DeleteClassActivities
{
    private final ArrayList<String> clmNamesToDelete = new ArrayList<>();
    private ArrayList<String> assessmentNames;
    
    public DeleteLessonsAssessments() throws SQLException {
        super();
    }    

    @Override
    public boolean deleteData() {
        return super.deleteData("lessonsassessments");        
    }

    @Override
    public boolean executeQuery(String query) throws SQLException 
    {
        ArrayList<String> studentIDs = super.getStudentIDs();
        
        if (studentIDs.isEmpty())
            return deleteClassAssessment(query);
        else
            return deleteStudentAssessment(query);
    }
    
    private boolean deleteClassAssessment (String query) throws SQLException 
    {
        UtilityClass uc = super.getUc();
        ArrayList<LocalDate> dates = super.getDates();
        query = uc.getWildCardsFormattedQuery(query + " AND assessmentname = ? AND", dates.size(), " dates IN (");
        boolean bool = false;
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            setStatement(statement, uc, dates);
            
            for (String assessmentName : assessmentNames) 
            {
                uc.setStringStatement (statement, assessmentName, 2);
                bool = uc.executeDMLStament(statement);
                if (!bool)
                    break;
            }
        }
        
        if (!bool) {
            Logger.getLogger(DeleteLessonsAssessments.class.getName()).info("Yoo Ben check the error here");
            super.setErrMsg("Application Error!");
        }
        return bool;
    }
    
    private boolean deleteStudentAssessment (String query) throws SQLException
    {
        if (super.getDates().size() != super.getStudentIDs().size()) { //a defence to avoid unexpected outputs
            setErrMsgs();
            return false;
        }
        
        UtilityClass uc = super.getUc();  
        boolean bool = false;
        query = query + " AND studentid = ? AND assessmentname = ? AND dates = ?";
        
        try (PreparedStatement statement = super.getPreparedStatement(query))
        {
            int classID = super.getClassID();
            int index = uc.setIntStatement(statement, classID, 1);
            int i;
            
            for (i = 0; i < super.getDates().size(); i++) 
            {
                setStatement(statement, uc, i, index);
                bool = uc.executeDMLStament(statement);
                if (!bool)
                    break;
            }
            
            if (i != super.getDates().size())
                super.setErrMsg("Unexpected error happened while removing student from class attendance!");            
        }
        return bool;
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc, ArrayList<LocalDate> dates) throws SQLException
    {
        int index = uc.setIntStatement(statement, super.getClassID(), 1);
        index += 1;
        uc.setDatesStatement(statement, dates.toArray(),index);
    }
    
    private void setStatement (PreparedStatement statement, UtilityClass uc, int arrIndex, int statementIndex) throws SQLException
    {
        //  AND studentid = ? AND assessmentname = ? AND dates = ?
        String studentID = super.getStudentIDs().get(arrIndex);
        String assessmentName = assessmentNames.get(arrIndex);
        LocalDate date = super.getDates().get(arrIndex);
        
        statementIndex = uc.setStringStatement(statement, studentID, statementIndex);
        statementIndex = uc.setStringStatement(statement, assessmentName, statementIndex);
        uc.setDateStatement(statement, java.sql.Date.valueOf(date), statementIndex);
    }    
    
    public void setAssessmentNames(ArrayList<String> assessmentNames) {
        this.assessmentNames = assessmentNames;
    }
    
    private void setErrMsgs () {
        Logger.getLogger(DeleteLessonsAssessments.class.getName()).info("Yoo Ben get the Application Error location bro");
        setErrMsg("Dates's size is supposed to be equal to the studentID's suize but it's not the case!");
    }    
    
    @Override
    public ArrayList<String> getClmNamesToCheck() {
        return clmNamesToDelete;
    }    
}
