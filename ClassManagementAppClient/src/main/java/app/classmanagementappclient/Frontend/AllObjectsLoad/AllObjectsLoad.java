package app.classmanagementappclient.Frontend.AllObjectsLoad;

import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.ReadAllProjects.ReadAllProject;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.ReadAllProjects.ReadAllProjectsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadData;
import app.classmanagementappclient.Backend.Database.Read.Readables;
import app.classmanagementappclient.Frontend.TreeWindow.TreeLoading.TreeLoader;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AllObjectsLoad {
    
    public List<List<String>> load (String query) {
        ReadAllProjectsProxy proxy = new ReadAllProjectsProxy(query);
        ReadAllProject data = new ReadAllProject(proxy);
        DBRead(data);
        return proxy.getData();
    }
    
    private void DBRead (Readables readable) {
        ReadData readData;
        try {
            readData = new ReadData(readable);
            readData.execute();
        } catch (SQLException ex) {
            System.getLogger(TreeLoader.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }     
}
