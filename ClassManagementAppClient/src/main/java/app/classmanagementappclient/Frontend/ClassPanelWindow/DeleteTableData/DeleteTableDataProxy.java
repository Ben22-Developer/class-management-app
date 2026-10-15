package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteTableRows;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class DeleteTableDataProxy implements DeleteTableRows {

    private final Table table;
    private final ProgressTracker progressTracker;
    private final TableManager tableManager;
    private final List<Integer> published = new ArrayList<>();
    private volatile boolean doInBgProcessEnded;
    
    public DeleteTableDataProxy (Table table, TableManager tableManager, ProgressTracker progressTracker) {
        this.table = table;
        this.progressTracker = progressTracker;
        this.tableManager = tableManager;
    }    
    @Override
    public void doInBackGround() {
        
        tableManager.setComponentsBoolean(false);
        
        Iterator<Integer> checkedRowsIterator =  table.getSortedCheckedRowsList().iterator();
        int i = 0;
        int total = table.getCheckedRows().size();
        
        progressTracker.run();

        while (checkedRowsIterator.hasNext()) 
        {
            int tableRowIndex = table.getTableRowSorter().convertRowIndexToView( checkedRowsIterator.next() );

            progressTracker.setLoadingPercentage(i, total);

            Loop res = deleteData(table, tableRowIndex);
            
            if (res.equals(Loop.CONTINUE))
                continue;
            else if (res.equals(Loop.BREAK))
                break;

            published.add(  table.getjTable().convertRowIndexToModel(tableRowIndex) );
            
            i++;          
        }
        progressTracker.endProgress();
        setDoInBgProcessEnded(true);        
    }

    @Override
    public void process(List<Integer> list) {
        for (int i : list)
            table.removeTableRow(i);
    }

    @Override
    public void done() {
        tableManager.setComponentsBoolean(true);
        table.emptifyCheckedRowSet();
        tableManager.setTitle("Total records: " + table.getjTable().getRowCount());
    }

    @Override
    public boolean isDoInBgProcessEnded() {
        return doInBgProcessEnded;
    }

    @Override
    public List<Integer> getPublishedData() {
        return published;
    }

    @Override
    public Table getTable() {
        return table;
    }
    
    public void setDoInBgProcessEnded(boolean doInBgProcessEnded) {
        this.doInBgProcessEnded = doInBgProcessEnded;
    } 

    public abstract Loop deleteData(Table table, int tableRowIndex);
}