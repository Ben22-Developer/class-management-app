package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.ClassStudentTable;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.InsertOneStudentFromClassStudentPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete.ClassStudentsTableDelete;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.StudentPanelLoadProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassStudentTableManagerProxy implements DataEntryActions {

    @Override
    public void setTitle(TableManager tableManager, int dataNum) {
        tableManager.setTitle("Total Students: " + dataNum);
    }

    @Override
    public List<List<String>> getData(int classTermNum) {
        return new StudentClassMedium().getAllClassStudents(classTermNum);
    }

    @Override
    public void handleNoDataReload(TableManager tableManager, ClassPanel classPanel, int classTermNum) {
        tableManager.getMyPanel().removeAll();
        tableManager.panelReValidation();
        new PanelLoad(classPanel, classTermNum).loadPanel(new StudentPanelLoadProxy());        
    }

    @Override
    public void deleteHandler(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        DeleteTableDataProxy proxy = new ClassStudentsTableDelete((ClassStudentsTable) table, tableManager, progressTracker);
        new DeleteSelectedTableRows(proxy).deleteRows();
    }

    @Override
    public Object[] addNewData(Table table, int classTermNum) {
        
        var dialog = new InsertOneStudentFromClassStudentPanel(table, classTermNum);
        dialog.run();
        Object[] objects = dialog.getInsertedData();
        return objects;
    }    

    @Override
    public void addNoToData(List<List<String>> data) {
        
        int i = 0;
        for (List<String> list : data) {
            list.add(0, String.valueOf(i + 1));
            i++;
        } 
    }

    @Override
    public void insertTableRow(Table table, List<String> row) {
        table.insertTableARow( table.getjTable().getRowCount() , List.of (false, table.getjTable().getRowCount() + 1, row.get(0), row.get(1), "Edit") );
    }
}
