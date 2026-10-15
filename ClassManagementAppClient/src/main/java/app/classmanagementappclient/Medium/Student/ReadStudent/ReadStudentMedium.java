package app.classmanagementappclient.Medium.Student.ReadStudent;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.ReadStudentInfo.Details.ReadAllStudentProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadStudentInfo.ReadStudent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadStudentMedium {
 
//    public Student getStudent (StudentMedium medium, int studentNum) {
//        
//        Student student = null;
//        
//        try (Connection connection = MediumUtility.getConnection()) {
//            
//            var studentDetails = new ReadStudentByStudentClassNum(studentNum);
//            student = studentDetails.getStudent(connection);
//        } catch (SQLException ex) {
//            System.getLogger(ReadStudentMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//        }
//        if (student == null)
//            medium.setMsg("Student not found!");
//        return student;
//    }
    
    public List<List<String>> getAllStudents () {
        
        List<List<String>> data = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
            var proxy = new ReadAllStudentProxy();
            var readStudent = new ReadStudent(proxy);
            readStudent.setConnection(connection);
            readStudent.executeRead();
            data = proxy.getData();
        } catch (SQLException ex) {
            System.getLogger(ReadStudentMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return data;
    }    
}
