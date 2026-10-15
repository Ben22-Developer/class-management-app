package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AttendanceRecordsTable.AttendanceUpdateDialog;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses.ViewMultipleStudentsAttendenceRecords;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceRecordsTableManager extends DataEntry1 {
    
    private String attendanceDateStr;
    private final String recordedAtStr;

    public AttendanceRecordsTableManager(String attendanceDateStr, String recordedAtStr, List<List<String>> data, int attendanceNum, Table table, JPanel tablePanel, String lessonName, Table parentTable, List<Integer> clmIndexToCheck, int selectedRow, DataEntryActions proxy) {
        
        super(data, attendanceNum, table, tablePanel, lessonName, parentTable, clmIndexToCheck, selectedRow, proxy);
        this.attendanceDateStr = attendanceDateStr;
        this.recordedAtStr = recordedAtStr;
    }
    
    @Override
    public void edit() {
        java.sql.Date currDate = FrontEndUtility.getAppFormatDateToSQLFormatDate(attendanceDateStr);
        Timestamp recordedAt = FrontEndUtility.getAppFormatTimestampToSQLFormatTimestamp(recordedAtStr);
        var dialog = new AttendanceUpdateDialog (currDate, recordedAt, super.getIdentifierNum(), super.getSelectedRow(), super.getParentTable() ,this);
        dialog.drawJDialog();
        dialog.run();
    }

    @Override
    public void multipleInsertion() {
        
        var table = (AttendanceRecordsTable) super.getTable();
        var multipleRecords = new ViewMultipleStudentsAttendenceRecords(table.getAttendanceNum(), this);
        multipleRecords.drawJDialog();    
    }

    @Override
    public String getWindowTitle() {
        return super.getLessonName() + " Attendance Records";
    }

    @Override
    public void setDateTitle () {
        super.getDateLabel().setText("Attendance Date: " + attendanceDateStr + "   Recorded At: " + recordedAtStr);
    }    

    public String getAttendanceDateStr() {
        return attendanceDateStr;
    }

    public void setAttendanceDateStr (String attendanceDateStr) {
        this.attendanceDateStr = attendanceDateStr;
    }
}
