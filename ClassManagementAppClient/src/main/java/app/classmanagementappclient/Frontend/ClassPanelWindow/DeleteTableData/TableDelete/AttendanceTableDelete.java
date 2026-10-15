package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceTableDelete extends DeleteTableDataProxy {

    public AttendanceTableDelete(AttendanceTable table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {
        
        AttendanceTable classAttendanceTable = (AttendanceTable) table;
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);
        
        AttendanceMedium medium = new AttendanceMedium();
        
        int lessonNum = classAttendanceTable.getLessonNum();
        
        java.sql.Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate (rowData.get(0));
        Timestamp timestamp = FrontEndUtility.getAppFormatTimestampToSQLFormatTimestamp(rowData.get(1));
        
        int attendanceNum = medium.getAttendanceNum(lessonNum, date, timestamp);
        
        boolean res = medium.deleteAttendanceMedium(attendanceNum);
        
        return res ? Loop.LOOP : Loop.BREAK;   
    }
}
