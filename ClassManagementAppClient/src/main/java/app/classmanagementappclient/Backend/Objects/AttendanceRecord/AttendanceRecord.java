package app.classmanagementappclient.Backend.Objects.AttendanceRecord;

/**
 *
 * @author BEN
 */
public class AttendanceRecord {
    private final int attendanceNum;
    private final int studentClassNum;

    public AttendanceRecord(int attendanceNum, int studentClassNum) {
        this.attendanceNum = attendanceNum;
        this.studentClassNum = studentClassNum;
    }

    public int getAttendanceNum() {
        return attendanceNum;
    }

    public int getStudentClassNum() {
        return studentClassNum;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" + "attendanceNum=" + attendanceNum + ", studentClassNum=" + studentClassNum + '}';
    }    
}
