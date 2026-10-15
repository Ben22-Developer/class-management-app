package app.classmanagementappclient.Medium.ClassTerm.ReadClassTerm;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Nums.ReadAllTermsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Nums.ReadClassYearTermsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Nums.ReadOneClassTermNumProxyV1;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Nums.ReadOneClassTermNumProxyV2;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Strings.ReadClassesTermsDetailsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.ReadClassTermData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassTermReadMedium 
{
    
    public int getOneClassTermNumV1 (String schoolName, String className, int classYear, int classTerm) {
            
        ReadOneClassTermNumProxyV1 proxy = new ReadOneClassTermNumProxyV1(schoolName, className, classYear, classTerm);
        executeReadOperation(proxy);
        return proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
    }      

    public List<Integer> getOneClassTermNumV2 (String schoolName, String className, int classYear, List<Integer> classTerms) {
        var proxy = new ReadOneClassTermNumProxyV2(schoolName, className, classYear, classTerms);
        executeReadOperation(proxy);
        return proxy.getData();
    }    
    
    public List<Integer> getAllTerms() {
        ReadAllTermsProxy proxy = new ReadAllTermsProxy ();
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    public List<List<String>> getClassesDetails (List<Integer> classTermNums) {
        
        ReadClassesTermsDetailsProxy proxy = new ReadClassesTermsDetailsProxy(classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }  
    
    public List<Integer> getClassYearTerms (String schoolName, String className, int year) {
        
        var proxy = new ReadClassYearTermsProxy(schoolName, className, year);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            ReadClassTermData classTermDetails = new ReadClassTermData(proxy);
            classTermDetails.setConnection(connection);
            classTermDetails.executeRead();
        }catch (SQLException ex) {
                System.getLogger(ClassTermReadMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
