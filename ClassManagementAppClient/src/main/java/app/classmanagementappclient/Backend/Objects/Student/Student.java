package app.classmanagementappclient.Backend.Objects.Student;
/**
 *
 * @author BEN
 */
public class Student 
{    
    private final String studentName;
    private final String studentID;

    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public String getStudentName () {
        return studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    @Override
    public String toString() {
        return "Student{" + "studentName=" + studentName + ", studentId=" + studentID + '}';
    }
}
