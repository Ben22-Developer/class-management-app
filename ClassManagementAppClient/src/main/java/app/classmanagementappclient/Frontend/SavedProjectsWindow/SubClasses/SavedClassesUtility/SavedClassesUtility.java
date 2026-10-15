package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedClassesUtility;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.SavedClassesTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DeleteSelected.SavedClassesDelete;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SavedProjectsWindowUtility;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.WindowLoad.SavedClassesLoader;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedClassesUtility extends SavedProjectsWindowUtility {

    private final JTree tree;
    private SavedClassesTable table;

    public SavedClassesUtility(JTree tree) {
        this.tree = tree;
    }
    
    @Override
    public List<List<String>> dataLoad() {
        return new SavedClassesLoader().dataCollection (6);
    }

    @Override
    public void insertData(List<String> data) {
        
        SwingUtilities.invokeLater(() -> 
        {
            table.insertTableARow (table.getjTable().getRowCount() , List.of (
                            false,table.getjTable().getRowCount() + 1, data.get(1), data.get(2), data.get(3),
                            data.get(4), data.get(5)));   
        });
    }
    
    
    
    @Override
    public void setTable (List<List<String>> data) {
        table = SavedClassesTable.getTable(data, tree, 200);
    }    

    @Override
    public void setTitleLabelTxt() {
        
        
        new SwingWorker<Object, String> () {
            @Override
            protected String doInBackground() throws Exception {
                
                setTotalData(table.getjTable().getRowCount());
                publish(getTotalData() + " Saved Classes");
                return null;
            }
            
            @Override
            protected void process(List<String> chunks) {
                getTableDisplay().getTitleLabel().setText( getTotalData() + " Saved Classes" );
            }
            
        }.execute();
//        SwingUtilities.invokeLater(() -> {
//            setTotalData(table.getjTable().getRowCount());
//            super.getTableDisplay().getTitleLabel().setText( getTotalData() + " Saved Classes" );
//        });
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public void deleteHandler() {

        if (table.getCheckedRows().isEmpty())
            return;
        
        var proxy = new SavedClassesDelete (table, table.getProgressTracker(), super.getComponents());
        FrontEndUtility.enableJComponents(super.getComponents(), false);
        var deleteSelectedTableRows = new DeleteSelectedTableRows(proxy);
        deleteSelectedTableRows.deleteRows();
        setTitleLabelTxt();
        
    }

    @Override
    public JComponent getProgressBar() {
        return table.getProgressBar();
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return table.getProgressTracker();
    }  
}
