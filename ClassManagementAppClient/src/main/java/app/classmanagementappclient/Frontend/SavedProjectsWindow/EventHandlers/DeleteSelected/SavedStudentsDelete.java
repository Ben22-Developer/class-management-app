package app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DeleteSelected;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.Common.SearchableTableDeleteHandler.DeleteSearchableTableSelectedRowsProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.SavedStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class SavedStudentsDelete extends DeleteSearchableTableSelectedRowsProxy{

    public SavedStudentsDelete(Table parentTable, ProgressTracker progressTracker, JComponent[] components) {
        super(parentTable, progressTracker, components);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {
        
        SavedStudentsTable table1 = (SavedStudentsTable) table;

        tableRowIndex = table1.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;
        
        String studentId = table1.getRowDataAt (tableRowIndex).get(0);

        boolean bool = new StudentMedium().deleteStudent(studentId);

        if (!bool) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Unexpected Error!\nContact the developer if it persits!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return Loop.BREAK;
        }    
        return Loop.LOOP;       
    }
    
}
