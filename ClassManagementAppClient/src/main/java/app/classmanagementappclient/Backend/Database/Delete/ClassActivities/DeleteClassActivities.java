package app.classmanagementappclient.Backend.Database.Delete.ClassActivities;

import app.classmanagementappclient.Backend.Database.Delete.DeleteData;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
abstract public class DeleteClassActivities extends DeleteData
{
    private int classID;
    private ArrayList<String> studentIDs;
    private ArrayList<LocalDate> dates;
            
    public DeleteClassActivities() throws SQLException {
        super();
    }
    
    @Override
    public void setClmNamesToCheck() {
        getClmNamesToCheck().add("classid");
    }    
    
    public void setStudentIDs(ArrayList<String> studentIDs) {
        this.studentIDs = studentIDs;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    public int getClassID() {
        return classID;
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }
    
    public ArrayList<String> getStudentIDs() {
        return studentIDs;
    }

    public int getClassIDs() {
        return classID;
    }
}
