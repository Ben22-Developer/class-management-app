package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AttendanceTable;

import app.classmanagementappclient.Medium.Attendance.AttendanceRead.ReadAttendanceMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData.InsertAttendanceDetailsDialog;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete.AttendanceTableDelete;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author BEN
 */
public class AttendanceTableManagerProxy implements DataEntryActions {

    /**
     *Some changes are needed here!
     * @param data
     */
    @Override
    public void addNoToData(List<List<String>> data) {}

    @Override
    public List<List<String>> getData(int lessonNum) {
        
        List<List<Object>> data = new ReadAttendanceMedium().getLessonAttendances(lessonNum);
        List<List<String>> dataStrings = new ArrayList<>();
        int i = 1;

        for (List<Object> rowObj : data) {
            
            List<String> rowStr = new ArrayList<>();
            
            rowStr.add(String.valueOf(i));
            rowStr.add(FrontEndUtility.getAppFormatDate(rowObj.get(0))); 
            rowStr.add(FrontEndUtility.getRecordedAtTimeInAppFormat(rowObj.get(1)));
            dataStrings.add(rowStr);
            i += 1;
        }
        
        return dataStrings;
    }
    
    /**
     *This method isn't universal to all table manager proxy, including this one
     * @param tableManager
     * @param panel
     * @param lessonNum
     */
    @Override
    public void handleNoDataReload(TableManager tableManager, ClassPanel panel, int lessonNum) {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Reloading failed!\nYou have no attendances recorder in this class", "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void deleteHandler(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        DeleteTableDataProxy proxy = new AttendanceTableDelete((AttendanceTable) table, tableManager, progressTracker);
        new DeleteSelectedTableRows(proxy).deleteRows();
    }

    @Override
    public Object[] addNewData(Table table, int lessonNum) {
        InsertAttendanceDetailsDialog lessonAttendance = new InsertAttendanceDetailsDialog(lessonNum, table.getjTable().getRowCount());
        lessonAttendance.drawJDialog();
        Object[] objects = lessonAttendance.getObjects();
        return objects;
    }

    @Override
    public void insertTableRow(Table table, List<String> row) {
        table.insertTableARow(table.getjTable().getRowCount(), List.of(false, row.get(0), row.get(1), row.get(2), "View"));
    }

    @Override
    public void setTitle(TableManager tableManager, int dataNum) {
        tableManager.setTitle("Total attendances: " + dataNum);
    }   
}
