package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.AttendanceRecord.AttendanceRecordMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceRecordTableDelete extends DeleteTableDataProxy {
    
    public AttendanceRecordTableDelete(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {
    
        AttendanceRecordsTable attendanceRecordsTable = (AttendanceRecordsTable) table;
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);
        
        int attendanceNum = attendanceRecordsTable.getAttendanceNum();
        int studentClassNum = new StudentClassMedium().getStudentClassNumByAttendance(attendanceNum, rowData.get(0));
        
        boolean res = new AttendanceRecordMedium().deleteAttendanceRecord(attendanceNum, studentClassNum);
        if (!res) 
            return Loop.BREAK;        
        return Loop.LOOP;
    }
}
