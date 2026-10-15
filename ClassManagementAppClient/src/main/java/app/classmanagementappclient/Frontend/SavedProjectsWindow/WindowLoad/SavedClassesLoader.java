package app.classmanagementappclient.Frontend.SavedProjectsWindow.WindowLoad;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.ReadSavedProjects.ReadSavedProjects;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedClassesLoader {
    
    public List<List<String>> dataCollection (int totalClms) {
        
//        System.out.println("\n");
        List<List<String>> data = dataLoad();
//        System.out.println("Saved project count at Frontend.SavedProjectsWindow.WindowLoad.dataCollection " + data.size());
//        System.out.println("\n");
        for (List<String> d : data)
            while (d.size() < totalClms)
                d.add("");
       return data;
    }      
    
    private List<List<String>> dataLoad () {
        
        ReadSavedProjects projects = null;
        List<List<String>> data = null;
        try (Connection connection = MediumUtility.getConnection()) {
            projects = new ReadSavedProjects(connection);
            data = projects.getData();
        } catch (SQLException ex) {
            System.getLogger(SavedClassesLoader.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return data;
    }
}
