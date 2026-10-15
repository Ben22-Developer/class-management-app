package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Medium.AttendanceRecord.AttendanceRecordMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables.AttendanceRecordsTableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AttendanceRecordsTable.AttendanceRecordsTableManagerProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ViewAttendanceRecordsDialog extends ViewTables {

    private String lessonName;
    private int attendanceNum;
    private String recordedAt;
    private String attendanceDate;
    
    @Override
    protected int getIndentifier() {
        AttendanceTable attendanceTable = (AttendanceTable) super.getTable();
        lessonName = new ClassLessonMedium().getLessonName(attendanceTable.getLessonNum());
        return lessonName.isBlank() ? Integer.MIN_VALUE : attendanceTable.getLessonNum();
    }
    
    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("This class might have been deleted\nPlease consider re-loading the tables for getting fresh data", "Informational Message", JOptionPane.INFORMATION_MESSAGE);    
    }

    @Override
    public List<List<String>> getData(int identifier) {
        
        List<String> data = super.getSelectedRowData();
        attendanceDate = data.get(0);
        recordedAt = data.get(1);
        Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate (attendanceDate);
        Timestamp timestamp = FrontEndUtility.getAppFormatTimestampToSQLFormatTimestamp (recordedAt);
        
        setAttendanceNum(identifier, date, timestamp);
        List<List<String>> attendedStudent = getRecordedStudentsOnSpecificAttendance(attendanceNum);
        
        return attendedStudent;
    }
    
    private void setAttendanceNum (int lessonNum, Date date, Timestamp timestamp) {
        attendanceNum = new AttendanceMedium().getAttendanceNum(lessonNum, date, timestamp);
    }
    
    private List<List<String>> getRecordedStudentsOnSpecificAttendance (int attendanceNum) {
        return new AttendanceRecordMedium().getRecordedStudentsOnSpecificAttendance(attendanceNum);
    }

    @Override
    public void display(List<List<String>> data) {
        
        int selecedRow = super.getRow();
        Table table = super.getTable();
        List<Integer> clmIndexToCheck = super.getClmIndexToCheck();
        AttendanceRecordsTable recordsTable = AttendanceRecordsTable.getAttendanceRecordsTable(data, attendanceNum, 400);
        DataEntry1 manager = new AttendanceRecordsTableManager(attendanceDate, recordedAt, data, attendanceNum, recordsTable, new JPanel(new BorderLayout()), lessonName, table, clmIndexToCheck, selecedRow , new AttendanceRecordsTableManagerProxy());
        manager.run();
    }
}
