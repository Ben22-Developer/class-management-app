package app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DeleteSelected;

import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.Common.SearchableTableDeleteHandler.DeleteSearchableTableSelectedRowsProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.SavedClassesTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete.TreeNodeDeleteMedium;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedClassesDelete extends DeleteSearchableTableSelectedRowsProxy{
    
    public SavedClassesDelete(Table parentTable, ProgressTracker progressTracker, JComponent[] components) {
        super(parentTable, progressTracker, components);
    }
    
    @Override
    public Loop deleteData (Table table, int tableRowIndex) {

        SavedClassesTable table1 = (SavedClassesTable) table;

        tableRowIndex = table1.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;
        
        List<String> rowData = table1.getRowDataAt (tableRowIndex);
        List<String> rowData1 = rowData.stream().filter((e) -> !e.isBlank()).toList();

        if (rowData1.size() <= 4)
            selectPath(table1.getjTree(), rowData1);

        boolean bool = new TreeNodeDeleteMedium(table1.getjTree()).deleteController(rowData1);

        if (!bool) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Unexpected Error!\nContact the developer if it persits!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return Loop.BREAK;
        }    
        return Loop.LOOP;
    }  
    
    private void selectPath (JTree jTree, List<String> rowData) {
        
        if (jTree == null)
            return;
        TreeWindowUtility.setSelectedPath(jTree, rowData);
    }    
    
}
