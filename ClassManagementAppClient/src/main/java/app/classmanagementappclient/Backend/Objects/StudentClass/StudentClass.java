package app.classmanagementappclient.Backend.Objects.StudentClass;

import app.classmanagementappclient.Backend.Objects.Student.Student;

/**
 *
 * @author BEN
 */
public class StudentClass 
{
    private final Student student;
    private final int classTerNum;
    private int studentNum;

    public StudentClass(Student student, int classTerNum) {
        this.student = student;
        this.classTerNum = classTerNum;
    }

    public Student getStudent() {
        return student;
    }

    public int getClassTerNum() {
        return classTerNum;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }
    
    @Override
    public String toString() {
        return "StudentClass{" + "student=" + student + ", classTerNum=" + classTerNum + ", studentNum=" + studentNum + '}';
    }    
}
