package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData.InsertAssessmentDetailsDialog;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete.AssessmentTableDelete;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentTableManagerProxy implements DataEntryActions {

    /**
     *Some changes are needed here!, This method isn't universal to all table manager proxy, including this one
     * @param data
     */
    @Override
    public void addNoToData(List<List<String>> data) {}

    @Override
    public List<List<String>> getData(int lessonNum) {
        
        List<List<Object>> data = new AssessmentMedium().getAllClassAssessments(lessonNum);
        List<List<String>> dataStrings = new ArrayList<>();
        int i = 1;

        for (List<Object> rowObj : data) {
            
            List<String> rowStr = new ArrayList<>();
            
            rowStr.add(String.valueOf(i));
            rowStr.add(String.valueOf(rowObj.get(0)));
            rowStr.add(FrontEndUtility.getAppFormatDate(rowObj.get(1))); 
            rowStr.add(String.valueOf(rowObj.get(2)));
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
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Reloading failed!\nYou have no assessments recorder in this class", "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void deleteHandler(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        DeleteTableDataProxy proxy = new AssessmentTableDelete((AssessmentsTable) table, tableManager, progressTracker);
        new DeleteSelectedTableRows(proxy).deleteRows();
    }

    @Override
    public Object[] addNewData(Table table, int lessonNum) {
        var insertAssessmentDetails = new InsertAssessmentDetailsDialog(lessonNum, table.getjTable().getRowCount());
        insertAssessmentDetails.drawJDialog();
        return insertAssessmentDetails.getObjects();
    }

    @Override
    public void insertTableRow(Table table, List<String> row) {
        table.insertTableARow(table.getjTable().getRowCount(), List.of(false, row.get(0), row.get(1), row.get(2), row.get(3), "View"));
    }

    @Override
    public void setTitle(TableManager tableManager, int dataNum) {
        tableManager.setTitle("Total assessments: " + dataNum);
    }   
}