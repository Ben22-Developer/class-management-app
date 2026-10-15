package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AttendanceRecordsTable;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData.DateChooserDialog;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables.AttendanceRecordsTableManager;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceUpdateDialog extends DateChooserDialog {
    
    private final Date currDate;
    private final Timestamp recordedAt;
    private final int row;
    private final int attendanceNum;
    private final Table parentTable;
    private final AttendanceRecordsTableManager tableManager;

    public AttendanceUpdateDialog(Date currDate, Timestamp recordedAt, int attendanceNum, int row, Table table, AttendanceRecordsTableManager tableManager) {
        
        this.row = row;
        this.currDate = currDate;
        this.parentTable = table;
        this.attendanceNum = attendanceNum;
        this.recordedAt = recordedAt;
        this.tableManager = tableManager;
    }
    
    @Override
    public void drawJDialog() {
        
        super.setTitleLable("Current Date " + FrontEndUtility.getAppFormatDate(currDate));
        super.setWindowTitle("Edit Attendance");
        setLocationPoint(new Point (650, 250));
        super.getDateChooser().setDate(currDate);
        super.drawJDialog();
    }
    
    @Override
    protected void addCenterPanel() {
        getDialog().add(super.getDateChooser(), BorderLayout.CENTER);
    }
    
    @Override
    public boolean submitHandler () {
        
        Date newDate = super.getDate();
        boolean res = new AttendanceMedium().updateAttendance(attendanceNum, newDate, currDate, recordedAt);
        
        if (!res)
            return false;
        
        UIUpdates(newDate);
        super.setSubmitted(true);
        notifySleepers();
        return true;        
    }    
    
    private void UIUpdates (Date newDate) {
        
        String appDateFormat = FrontEndUtility.getAppFormatDate(newDate);
        String recordedAtFormat = FrontEndUtility.getRecordedAtTimeInAppFormat(LocalDateTime.ofInstant(recordedAt.toInstant(), ZoneId.systemDefault()));
                
        parentTable.setValueAt(row, List.of(appDateFormat, recordedAtFormat));
        tableManager.setAttendanceDateStr(appDateFormat);
        tableManager.setDateTitle();
    }

    @Override
    public synchronized void notifySleepers() {
        notifyAll();
    }
    
    public synchronized void run () {
        try {
            wait();
        } catch (Exception ex) {}
    }
}
