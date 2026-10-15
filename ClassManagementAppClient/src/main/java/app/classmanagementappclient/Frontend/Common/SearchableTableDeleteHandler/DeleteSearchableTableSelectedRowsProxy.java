package app.classmanagementappclient.Frontend.Common.SearchableTableDeleteHandler;

import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteTableRows;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author BEN
 */
abstract public class DeleteSearchableTableSelectedRowsProxy implements DeleteTableRows
{   
    private final Table table;
    private final List<Integer> published = new ArrayList<>();
    private volatile boolean doInBgProcessEnded;
    private final JComponent[] components;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final ProgressTracker progressTracker;
    
    public DeleteSearchableTableSelectedRowsProxy(Table parentTable, ProgressTracker progressTracker, JComponent[] components) {
        this.components = components;
        this.table = parentTable;
        this.progressTracker = progressTracker;
    }
    
    @Override
    public void doInBackGround () {
        
            Iterator<Integer> checkedRowsIterator =  table.getSortedCheckedRowsList().iterator();
            int i = 0;
            int total = table.getCheckedRows().size();

            progressTracker.run();

            while (checkedRowsIterator.hasNext()) 
            {
                int tableRowIndex = checkedRowsIterator.next();

                progressTracker.setLoadingPercentage(i, total);

                Loop res = deleteData(table, tableRowIndex);

                if (res == Loop.CONTINUE)
                    continue;
                else if (res == Loop.BREAK)
                    break;

                published.add(  table.getTableRowSorter().convertRowIndexToModel(table.getTableRowSorter().convertRowIndexToView(tableRowIndex))   );
                i++;          
            }
            progressTracker.endProgress();        
            setDoInBgProcessEnded(true);
    }

    @Override
    public void process (List<Integer> list) {
        for (int num : list)
            table.removeTableRow(num);
    }

    @Override
    public void done() {
        table.emptifyCheckedRowSet();
        FrontEndUtility.enableJComponents(components, true);
    }    

    @Override
    public List<Integer> getPublishedData() {
        return published;
    }

    @Override
    public boolean isDoInBgProcessEnded() {
        return doInBgProcessEnded;
    }

    public void setDoInBgProcessEnded(boolean doInBgProcessEnded) {
        this.doInBgProcessEnded = doInBgProcessEnded;
    }

    @Override
    public Table getTable() {
        return table;
    }
    
    public abstract Loop deleteData (Table table, int tableRowIndex);
}
