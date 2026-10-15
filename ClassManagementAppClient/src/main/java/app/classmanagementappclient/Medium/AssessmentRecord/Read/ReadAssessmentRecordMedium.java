package app.classmanagementappclient.Medium.AssessmentRecord.Read;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.InterfaceImplementation.Details.ReadStudentsOnSpecificAssessmentRecord;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.InterfaceImplementation.Details.ReadUnRecordedStudents;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.InterfaceImplementation.ReadAllRecordedAssessment.ReadAllStudentAssessmentRecord;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.ReadAssessmentRecordData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAssessmentRecordMedium {
    
    public List<List<String>> getAllStudentAssessmentRecord () {
        var proxy = new ReadAllStudentAssessmentRecord();
        executeOperation(proxy);
        return proxy.getData();
    }  
    
    public List<List<String>> getRecordedStudentsOnSpecificAssessment (int assessmentNum) {
        var proxy = new ReadStudentsOnSpecificAssessmentRecord(assessmentNum);
        executeOperation(proxy);
        return proxy.getData();
    }    
    
    public List<List<String>> getUnRecordedStudentsOnSpecificAssessment (int assessmentNum) {
        var proxy = new ReadUnRecordedStudents(assessmentNum);
        executeOperation(proxy);
        return proxy.getData();
    } 
    
    public void executeOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {
            var assessmentRecordData = new ReadAssessmentRecordData(proxy);
            assessmentRecordData.setConnection(connection);
            assessmentRecordData.executeRead();
        } 
        catch (SQLException ex) {
            System.getLogger(ReadAssessmentRecordMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
