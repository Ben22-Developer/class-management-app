package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertAttendanceDetailsDialog extends DateChooserDialog {
    
    private Object[] row;
    private final int lessonNum;
    private final int rowCount;

    public InsertAttendanceDetailsDialog(int lessonNum, int rowCount) {
        this.lessonNum = lessonNum;
        this.rowCount = rowCount;
    } 
    
    @Override
    public void drawJDialog() {
        
        setTitleLable("Select attendance date");
        setWindowTitle("Add new attendance");
        setLocationPoint(new Point (650, 250));
        super.drawJDialog();
    }
    
    @Override
    protected void addCenterPanel() {
        getDialog().add(super.getDateChooser(), BorderLayout.CENTER);
    }
    
    public boolean submitHandler() {
        
        Attendance attendance = new Attendance(super.getDate(), lessonNum);
        AttendanceMedium medium = new AttendanceMedium(attendance);
        
        int attendanceNum = medium.insertAttendance();
        if (attendanceNum == Integer.MIN_VALUE)
            return false;
        
        List<Object> rowList = medium.getAttendanceDetails(attendanceNum);
        
        Object[] row = List.of( false, rowCount + 1, FrontEndUtility.getAppFormatDate(rowList.get(0)), FrontEndUtility.getRecordedAtTimeInAppFormat(rowList.get(1)), "View" ).toArray();
        
        setRow(row);
        super.setSubmitted(true);
        notifySleepers();
        return true;
    }
    
    public synchronized Object[] getObjects () {
        
        while (!super.isSubmitted()) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        System.out.println("end");
        return row;
    }
    
    @Override
    public synchronized void notifySleepers () {
        notifyAll();
    }

    public void setRow(Object[] row) {
        this.row = row;
    }
}
