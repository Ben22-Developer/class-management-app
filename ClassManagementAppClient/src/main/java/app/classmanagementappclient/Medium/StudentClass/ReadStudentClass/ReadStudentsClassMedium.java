package app.classmanagementappclient.Medium.StudentClass.ReadStudentClass;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassStudentNum.ReadStudentClassNumProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassStudentNum.ReadStudentClassNumProxyByAssessmentNum;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassStudentNum.ReadStudentClassNumProxyByAttendanceNum;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassStudentsData;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadClassWithStudents.ReadClassesWithStudentsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentByStudentClassNum.ReadStudentByStudentClassNum;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentClasses.ReadStudentClassesProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentsInClass.ReadClassStudentsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.ReadStudentsInClass.ReadClassStudentsProxyV1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadStudentsClassMedium {

    
    public int getStudentClassNum (int classTermNum, String studentID) {

        var proxy = new ReadStudentClassNumProxy(classTermNum, studentID);;
        executeReadOperation(proxy);
        return proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
    }
    
    public List<String> getStudentetailsByStudentClassNum (StudentClassMedium medium, int studentClassNum) {
        var proxy = new ReadStudentByStudentClassNum (studentClassNum);
        executeReadOperation(proxy);
        List<String> data = proxy.getData().isEmpty() ? null : proxy.getData().get(0);
        if (data == null)
            medium.setMsg("The student you're trying to reach isn't in class!");        
        return data;
    }      

    public int getStudentClassNumByAttendance (StudentClassMedium medium, int attendanceNum, String studentID) {

        var proxy = new ReadStudentClassNumProxyByAttendanceNum(attendanceNum, studentID);
        executeReadOperation(proxy);
        int studentClassNum = proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
        
        if (studentClassNum == Integer.MIN_VALUE)
            medium.setMsg("Student ID: " + studentID + "\nIsn't a part of this class");
        
        return studentClassNum;
    }
    
    public int getStudentClassNumByAssessment (StudentClassMedium medium, int assessmentNum, String studentID) {

        var proxy = new ReadStudentClassNumProxyByAssessmentNum(assessmentNum, studentID);
        executeReadOperation(proxy);
        int studentClassNum = proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
        
        if (studentClassNum == Integer.MIN_VALUE)
            medium.setMsg("Student ID: " + studentID + "\nIsn't a part of this class");
        
        return studentClassNum;
    }    
    
    public List<List<String>> getAllClassStudents (int classTermNum) {
        var proxy = new ReadClassStudentsProxy(classTermNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }  
    
    public List<List<String>> getAllClassStudentsV1 (List<Integer> classTermNums) {
        var proxy = new ReadClassStudentsProxyV1(classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }      
    
    public List<Integer> getAllClassesWithStudents () {
        var proxy = new ReadClassesWithStudentsProxy();
        executeReadOperation(proxy);
        return proxy.getData();
    }  
    
    public List<List<String>> getStudentClasses (String studentID) {
        var proxy = new ReadStudentClassesProxy(studentID);
        executeReadOperation(proxy);
        return proxy.getData();
    } 
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {
            ReadClassStudentsData classStudents = new ReadClassStudentsData(proxy);
            classStudents.setConnection(connection);
            classStudents.executeRead();
        }catch (SQLException ex) {
            System.getLogger(ReadStudentsClassMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }      
}
