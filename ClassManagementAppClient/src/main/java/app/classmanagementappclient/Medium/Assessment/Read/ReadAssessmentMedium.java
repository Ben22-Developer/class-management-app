package app.classmanagementappclient.Medium.Assessment.Read;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadAggregates.ReadAggregateAssessment.ReadAggregateAssessmentProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Details.ReadAllTermAssessmentDetailsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Details.ReadLessonAssessmentDetailsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Details.ReadTermsAssessmentDetailsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Nums.ReadAssessmentNumProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.InterfaceImplementation.Nums.ReadAssessmentNumsProxyV1;
import app.classmanagementappclient.Backend.Database.Read.ReadAssessment.ReadAssessmentData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAssessmentMedium {
    
    public int getAssessmentNum (int lessonNum, String lessonName, Date date) {
        var proxy = new ReadAssessmentNumProxy(lessonNum, lessonName, date);
        executeReadOperation(proxy);
        return proxy.getData().get(0);
    }    

    public int getAssessmentNums (String schoolName, String className, int year, int term, String lessonName, String assessmentName, Date date) {
        var proxy = new ReadAssessmentNumsProxyV1(schoolName, className, year, term, lessonName, assessmentName, date);
        executeReadOperation(proxy);
        return proxy.getData().get(0);
    }     
    
    public List<List<Object>> getAllAssessmentsInTerm (int classTermNum) {
        var proxy = new ReadAllTermAssessmentDetailsProxy(classTermNum);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    public List<List<Object>> getAllClassAssessments (int lessonNum) {
        var proxy = new ReadLessonAssessmentDetailsProxy(lessonNum);
        executeReadOperation(proxy);
        return proxy.getData();
    } 

    public List<List<String>> getAssessmentsInTerms (List<Integer> classTermNums) {
        var proxy = new ReadTermsAssessmentDetailsProxy(classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    } 
    
    public List<List<String>> getReportRow (List<List<Integer>> assessmentNums, List<Integer> classTermNums) {
        var proxy = new ReadAggregateAssessmentProxy(assessmentNums, classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }     
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            var attendanceData = new ReadAssessmentData(proxy);
            attendanceData.setConnection(connection);
            attendanceData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(ReadAssessmentMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }    
}
