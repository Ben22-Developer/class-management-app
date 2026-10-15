package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.AttendanceRecord.Read.ReadRecordedAttendanceMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records.RecordLogic;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ViewMultipleStudentsAttendenceRecords extends ViewTables {

    private final int attendanceNum;
    private final TableManager tableManager;
    private TableDisplay tableDisplay;

    public ViewMultipleStudentsAttendenceRecords(int attendanceNum, TableManager tableManager) {
        this.attendanceNum = attendanceNum;
        this.tableManager = tableManager;
    }
    
    @Override
    protected int getIndentifier() {
        var table = (AttendanceRecordsTable) this.tableManager.getTable();
        return table.getAttendanceNum();
    }
    
    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("This class has no students!", "Informational Message", JOptionPane.INFORMATION_MESSAGE);    
    }

    @Override
    public List<List<String>> getData(int identifier) {
        return new ReadRecordedAttendanceMedium().getUnRecordedStudentsOnSpecificAttendance(attendanceNum);
    }

    @Override
    public void display(List<List<String>> data) {
        
        if (data.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no student left unattended for this attendance", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableDisplay = new TableDisplay();
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("","No", "Student ID", "Student Name"), List.of(2,3), data);
        JButton submitBtn = getSubmitBtn(table);
        JButton selectAllBtn =  new JButton("Select All");
        JButton selectNone =  new JButton("Select None");
        JButton removeSelectedBtn = getRemoveSelectedBtn(table);
        JComponent[] components = new JComponent[] { selectAllBtn, selectNone, removeSelectedBtn, submitBtn };
        String title = "Unrecorded Students " + data.size();

        
        commonPropertiesSetup(new JButton[] { selectAllBtn, selectNone }, components, tableDisplay, table, title);
    }

    @Override
    protected void setJDialogProp() {
        super.setTitleLable("Attendance Record");
        super.setJDialogProp();
    }
    
    

    private JButton getSubmitBtn(Table table) {
        
        JButton submitButton = new JButton("Submit");
        
        submitButton.addActionListener(l -> {
            handleSubmitEvent(table);
        });
        
        return submitButton;
    }
    
    private void handleSubmitEvent (Table table) {
        
        new SwingWorker<Object, Integer> () {
            @Override
            protected Object doInBackground() throws Exception {
            
                List<Integer> checkedRowList = table.getSortedCheckedRowsList();
                
                if (checkedRowList.isEmpty()) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There is no selected row!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                
                for (int checked : checkedRowList) {
                    boolean res = insertData(table.getRowDataAt(checked));
                    if (res)
                        publish(checked);
                }
                
                table.emptifyCheckedRowSet();
                return null;
            }
            
            private boolean insertData (List<String> data) {
                RecordLogic recordLogic = new RecordLogic();
                return recordLogic.attendanceRecord(data.get(0), attendanceNum);
            }

            @Override
            protected void process(List<Integer> chunks) {
                
                for (int chunk : chunks) {
                    updateAttendancesRecordTable(table.getRowDataAt(chunk));
                    table.removeTableRow(table.getjTable().convertRowIndexToModel(chunk));
                }
            }
            
            private void updateAttendancesRecordTable (List<String> data) {
                Table table = tableManager.getTable();
                int rowCount = table.getjTable().getRowCount();
                table.insertTableARow(rowCount, List.of(false, rowCount + 1, data.get(0), data.get(1)));
                tableManager.setTitle("Total records: " + (rowCount + 1));
            }
        }.execute();
    }
}
