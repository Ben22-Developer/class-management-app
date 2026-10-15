package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassStudentsTableDelete extends DeleteTableDataProxy {
    
    public ClassStudentsTableDelete(ClassStudentsTable table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);

    }
    
    
    @Override
    public Loop deleteData(Table table, int tableRowIndex) {

        ClassStudentsTable classStudentsTable = (ClassStudentsTable) table;
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);

        StudentClassMedium medium = new StudentClassMedium();
        int studentClassNum = medium.getStudentClassNum(classStudentsTable.getClassTermNum(), rowData.get(0));
        boolean res = medium.deleteStudentClass(studentClassNum);
        
        if (!res) 
            return Loop.BREAK;
        
        return Loop.LOOP;        
    }
}
