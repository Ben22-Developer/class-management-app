package app.classmanagementappclient.Medium.Student;

import app.classmanagementappclient.Medium.Student.CreateStudent.InsertStudentMedium;
import app.classmanagementappclient.Medium.Student.DeleteStudent.DeleteStudentMedium;
import app.classmanagementappclient.Medium.Student.EditStudent.StudentUpdateMedium;
import app.classmanagementappclient.Medium.Student.ReadStudent.ReadStudentMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class StudentMedium 
{
    private String msg;
    private final Validation validation = new Validation();
    private final Student student;

    public StudentMedium(Student student) {
        this.student = student;
    }
 
    public StudentMedium() {
        this(null);
    }
    
    public boolean insertStudent () 
    {
        if (!StudentMedium.this.validation()) {
            showMsg();
            return false;
        }
        
        boolean inserted = new InsertStudentMedium().insertStudent(this, student);
        
        if (!inserted)
            showMsg();
        
        return inserted;
    }
    
    public boolean insertStudentNoShowMsg () 
    {
        if (!StudentMedium.this.validation())
            return false;
        
        boolean inserted = new InsertStudentMedium().insertStudent(this, student);
        
        return inserted;
    }    
    
    public boolean updateStudent (Student currStudentData, Student newStudentData) {
        
        boolean res = StudentMedium.this.validation(newStudentData);
        
        if (!res) {
            showMsg();
            return false;
        }
        
        res = new StudentUpdateMedium().updateStudentData(this, currStudentData, newStudentData);
        
        if (!res) 
            showMsg();
        return res;
    }
    
    public boolean deleteStudent (String studentID) {
        
        boolean res = new DeleteStudentMedium().deleteStudentClass(this, studentID);
        if (!res) 
            showMsg();
        return res;
    }    
    
//    public Student readStudent (int studentNum) {
//        
//        Student student = new ReadStudentMedium().getStudent(this, studentNum);
//        if (student == null)
//            showMsg();
//        return student;
//    }
    
    public List<List<String>> getAllStudents () {
        return new ReadStudentMedium().getAllStudents();
    }
    
    public boolean validation () 
    {
        if (!validation.isNameValid(student.getStudentName()) || !validation.isStudentIDValid(student.getStudentID())) {
            msg = validation.getErrorMsg();
            return false;
        }
        return true;
    }

    public boolean validation (Student student) 
    {
        if (!validation.isNameValid(student.getStudentName()) || !validation.isStudentIDValid(student.getStudentID())) {
            msg = validation.getErrorMsg();
            return false;
        }
        return true;
    }

    public boolean validation (String studentID) 
    {
        if (!validation.isStudentIDValid(studentID)) {
            msg = validation.getErrorMsg();
            return false;
        }
        return true;
    }
    
    public void showMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
