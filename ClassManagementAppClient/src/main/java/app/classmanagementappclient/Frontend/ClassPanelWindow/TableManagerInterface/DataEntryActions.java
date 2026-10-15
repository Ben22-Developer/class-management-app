package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface DataEntryActions {
    
    public void addNoToData (List<List<String>> data);
    public List<List<String>> getData (int num);
    public void handleNoDataReload (TableManager tableManager, ClassPanel panel, int classTermNum);
    public void deleteHandler (Table table,TableManager tableManager, ProgressTracker progressTracker);
    public Object[] addNewData (Table table, int classTermNum);
    public void insertTableRow (Table table, List<String> row);
    public void setTitle (TableManager tableManager, int dataNum);
    
    public default int getDataSize (List<List<String>> data ) {
        return data.size();
    }    
}
