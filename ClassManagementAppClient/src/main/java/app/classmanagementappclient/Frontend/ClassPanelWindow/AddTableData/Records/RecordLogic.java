package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Medium.AttendanceRecord.AttendanceRecordMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;

/**
 *
 * @author BEN
 */
public class RecordLogic {
    
    private int studentClassNum;
    private String msg;
    
    public boolean attendanceRecord (String studentID, int attendanceNum) {
        
        studentClassNum = new StudentClassMedium().getStudentClassNumByAttendance(attendanceNum, studentID);
        
        if (studentClassNum == Integer.MIN_VALUE)
            return false;
        
        AttendanceRecord record = new AttendanceRecord(attendanceNum, studentClassNum);
        return new AttendanceRecordMedium(record).insertAttendanceRecord();
    }

    public boolean assessmentRecord (String studentID, String studentMarks, int assessmentNum) {
        
        var studentClassMedium = new StudentClassMedium();
        studentClassNum = studentClassMedium.getStudentClassNumByAssessment(assessmentNum, studentID);
        
        if (studentClassNum == Integer.MIN_VALUE)    
            return false;
        
        return new AssessmentRecordMedium().insertAssessmentRecord(assessmentNum, studentClassNum, studentMarks);
    }
    
   public boolean assessmentRecord_NoMsg (String studentID, String studentMarks, int assessmentNum) {
        
        var studentClassMedium = new StudentClassMedium();
        studentClassNum = studentClassMedium.getStudentClassNumByAssessment_NoShowMsg(assessmentNum, studentID);
        
        if (studentClassNum == Integer.MIN_VALUE) {
            setMsg(studentClassMedium.getMsg());
            return false;
        }
        
        var assessmentRecordMedium = new AssessmentRecordMedium();
        boolean res = assessmentRecordMedium.insertAssessmentRecord_NoShowMsg(assessmentNum, studentClassNum, studentMarks);
        
        if (!res)
            setMsg (assessmentRecordMedium.getMsg());
        
        return res;
    }    
    
    public int getStudentClassNum() {
        return studentClassNum;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getMsg() {
        return msg;
    }
}
