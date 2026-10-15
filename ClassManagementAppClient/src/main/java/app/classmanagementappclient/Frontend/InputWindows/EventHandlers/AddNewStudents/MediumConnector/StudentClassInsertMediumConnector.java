package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.MediumConnector;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;

/**
 *
 * @author BEN
 */
public class StudentClassInsertMediumConnector {
    
    private final int classTermNum;
    private final String studentName;
    private final String studentID;
    private String msg;
    
    public StudentClassInsertMediumConnector (int classTermNum, String studentID, String studentName) {
        this.classTermNum = classTermNum;
        this.studentName = studentName;
        this.studentID = studentID;
    }    
    
    public boolean insert () {
        Student student = new Student(studentID, studentName);
        StudentClass studentClass = new StudentClass(student, classTermNum);
        StudentClassMedium studentClassMedium = new StudentClassMedium(studentClass);
        boolean res = studentClassMedium.insertStudentToClass();
        if (!res)
            msg = studentClassMedium.getMsg();
        return res;
    }
    
    public String getMsg() {
        return msg;
    }
}
