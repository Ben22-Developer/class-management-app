package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records.OneByOneAssessmentRecord;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete.AssessmentRecordTableDelete;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1Int;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author BEN
 */
public class AssessmentRecordsTableManagerProxy  implements DataEntryActions, DataEntry1Int {

    private Object[] newRecords;
    private boolean nextRecord;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();     
    
    @Override
    public void addNoToData(List<List<String>> data) {}

    @Override
    public List<List<String>> getData(int num) {
        return new AssessmentRecordMedium().getRecordedStudentsOnSpecificAssessment(num);
    }

    @Override
    public void handleNoDataReload(TableManager tableManager, ClassPanel panel, int classTermNum) {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Reloading failed!\nYou have no attendances recorder in this class", "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void deleteHandler(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        var proxy = new AssessmentRecordTableDelete(table, tableManager, progressTracker);
        new DeleteSelectedTableRows(proxy).deleteRows();
    }

    @Override
    public Object[] addNewData(Table table, int num) {
        OneByOneAssessmentRecord assessmentRecord = new OneByOneAssessmentRecord(this, table.getjTable().getRowCount(), num);
        setNextRecord(true);
        assessmentRecord.drawJDialog();
        return null;
    }

    @Override
    public void insertTableRow(Table table, List<String> row) {
        table.insertTableARow(table.getjTable().getRowCount(), List.of(false, row.get(0), row.get(1), row.get(2), row.get(3), "Edit"));
    }

    @Override
    public void setTitle(TableManager tableManager, int dataNum) {
        tableManager.setTitle("Total recorded assessments: " + dataNum);
    }

    @Override
    public boolean isNextRecord() {
        return nextRecord;
    }

    @Override
    public Object[] getNewRecords() {
        
        lock.lock();
        try {
            while (isNextRecord()) {
                condition.await();
            }
        }
        catch (InterruptedException ex) {System.out.println("Interrupted");}
        finally {
            lock.unlock();
        }
        return newRecords;
    }

    @Override
    public void setNextRecord(boolean nextRecord) {
        this.nextRecord = nextRecord;
    }

    @Override
    public void setNewRecords(Object[] newRecords) {
        this.newRecords = newRecords;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
