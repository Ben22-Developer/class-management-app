package app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DataReLoad;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SavedProjectsWindowUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedProjectsDataReload {
    
    private final SavedProjectsWindowUtility utility;
    private final ProgressTracker tracker;
    
    public SavedProjectsDataReload (SavedProjectsWindowUtility utility,ProgressTracker tracker) {
        this.utility = utility;
        this.tracker = tracker;
    }
    
    public void reload () {
        
        Table table = utility.getTable();
        
        JComponent[] components = utility.getComponents();
        
        final int total = table.getjTable().getRowCount() * 2;
        
        int[] arr = {0,total};
        
         SwingWorker<Object, List<String>> sw = new SwingWorker<Object, List<String>> () {
            
            @Override
            public Object doInBackground () { 
                
                List<List<String>> data = utility.dataLoad();
                
                data.forEach(e -> {
                    publish(e);
                });

                return null;
            }

            @Override
            protected void process (List<List<String>> chunks) {
                
                for (List<String> chunk : chunks) {
                    
                    utility.insertData(chunk);
                    
                    tracker.setLoadingPercentage(arr[0], total);
                    arr[0] += 1;                    
                }
            }

            @Override
            public void done () {
                FrontEndUtility.enableJComponents(components, true);
                utility.setTitleLabelTxt();
                tracker.endProgress();
            }
        };        
         
        new SwingWorker<Object, Integer> () {
            
            @Override
            public Object doInBackground () {
                
                utility.processInit(components);
                
                SearchableTable.cleanSearchInTable(table);
                
                utility.getSearchTextField().setText("");
                
                publish(table.getjTable().getRowCount());
                
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                Table.removeTableRowsWithProgressTracker(table, tracker, chunks.get(0) - 1, arr, total);
            }

            @Override
            protected void done() {
                sw.execute();
            }
        }.execute();        
    }
}
