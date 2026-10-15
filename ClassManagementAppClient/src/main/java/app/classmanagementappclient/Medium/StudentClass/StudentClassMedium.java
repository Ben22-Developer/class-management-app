package app.classmanagementappclient.Medium.StudentClass;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Medium.StudentClass.DeleteStudentClass.DeleteStudentClassMedium;
import app.classmanagementappclient.Medium.StudentClass.InsertStudentClass.InsertStudentClassMedium;
import app.classmanagementappclient.Medium.StudentClass.ReadStudentClass.ReadStudentsClassMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class StudentClassMedium 
{
    private final Validation validation = new Validation();
    private final StudentClass studentClass;
    private String msg;

    public StudentClassMedium(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

    public StudentClassMedium() {
        this(null);
    }    
    
    public boolean insertStudentToClass () {
        
        boolean res = validateStudentClass(studentClass);
        
        if (!res)
            return false;
        
        InsertStudentClassMedium insertStudentClass = new InsertStudentClassMedium();
        res = insertStudentClass.insertStudentToClass(this, studentClass);
        
        return res;        
    }
    
    public List<List<String>> getAllClassStudents (int classTermNum) {
        return new ReadStudentsClassMedium().getAllClassStudents(classTermNum);
    }

    public List<List<String>> getAllClassStudentsV1 (List<Integer> classTermNums) {
        return new ReadStudentsClassMedium().getAllClassStudentsV1(classTermNums);
    }    
    
    public List<Integer> getAllClassWithStudents () {
        return new ReadStudentsClassMedium().getAllClassesWithStudents();
    }

    public List<String> getStudentetailsByStudentClassNum (int studentClassNum) {
        List<String> data = new ReadStudentsClassMedium().getStudentetailsByStudentClassNum(this, studentClassNum);
        if (data == null)
            showErrMsg();
        return data;
    }    
    
    public List<List<String>> getStudentClasses (String studentID) {
        return new ReadStudentsClassMedium().getStudentClasses(studentID);
    }
    
    public int getStudentClassNum (int classTermNum, String studentID) {
        return new ReadStudentsClassMedium().getStudentClassNum(classTermNum, studentID);
    }

    public int getStudentClassNumByAttendance (int attendanceNum, String studentID) {
        
        if (!studentIDValidate(studentID)) {
            showErrMsg();
            return Integer.MIN_VALUE;
        }
        
        int res = new ReadStudentsClassMedium().getStudentClassNumByAttendance(this, attendanceNum, studentID);
        if (res == Integer.MIN_VALUE)
            showErrMsg();
        return res;
    }    
    
    public int getStudentClassNumByAssessment (int assessmentNum, String studentID) {
        
        if (!studentIDValidate(studentID)) {
            showErrMsg();
            return Integer.MIN_VALUE;
        }
        
        int res = new ReadStudentsClassMedium().getStudentClassNumByAssessment(this, assessmentNum, studentID);
        if (res == Integer.MIN_VALUE)
            showErrMsg();
        return res;
    }    
    
    public int getStudentClassNumByAssessment_NoShowMsg (int assessmentNum, String studentID) {
        
        if (!studentIDValidate(studentID)) 
            return Integer.MIN_VALUE;
        
        return new ReadStudentsClassMedium().getStudentClassNumByAssessment(this, assessmentNum, studentID);
    }       
    
    public boolean deleteStudentClass (int studentClassNum) {
        boolean bool = new DeleteStudentClassMedium().deleteStudentClass(this, studentClassNum);
        if (!bool)
            showErrMsg();
        return bool;
    }

    private boolean studentIDValidate (String studentID) {
        
        StudentMedium studentMedium = new StudentMedium();
        boolean res = studentMedium.validation(studentID);
        if (!res)
            setMsg( studentMedium.getMsg() );
        return res;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    private boolean validateStudentClass (StudentClass studentClass) {
        
        Validation validation = new Validation();
        
        boolean res = validation.isNameValid(studentClass.getStudent().getStudentName());
        if (!res) {
            setMsg(validation.getErrorMsg());
            return false;
        }
        
        res = validation.isStudentIDValid(studentClass.getStudent().getStudentID());
        if (!res) {
            setMsg(validation.getErrorMsg());
            return false;
        }
        return true;
    }    
    
    public void showErrMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}
