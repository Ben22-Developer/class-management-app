package app.classmanagementappclient.Medium.Class.ClassRead;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Names.ReadAllClassesProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Nums.ReadOneClassNumProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Nums.ReadOneClassNumProxyV1;
import app.classmanagementappclient.Backend.Database.Read.ReadClassData.ReadClassData;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassReadMedium 
{
    public int getOneClassNum (Classs classs) 
    {    
        int classNum = 0;
        try (Connection connection = MediumUtility.getConnection()) 
        {
            int schoolNum = classs.getSchool().getSchoolNum();
            String className = classs.getClassName();
            ReadOneClassNumProxy readOneClassNumProxy = new ReadOneClassNumProxy(schoolNum, className);
            ReadClassData readClassData = new ReadClassData(readOneClassNumProxy);
            readClassData.setConnection(connection);
            readClassData.executeRead();
            classNum = readOneClassNumProxy.getData().get(0);
        } 
        catch (SQLException ex) {
            System.getLogger(ClassReadMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return classNum;
    } 
    
    public int getOneClassNumV1 (String schoolName, String className) 
    {    
        int classNum = 0;
        try (Connection connection = MediumUtility.getConnection()) 
        {
            ReadOneClassNumProxyV1 proxy = new ReadOneClassNumProxyV1 (schoolName, className);
            ReadClassData readClassData = new ReadClassData(proxy);
            readClassData.setConnection(connection);
            readClassData.executeRead();
            classNum= proxy.getData().isEmpty() ? Integer.MIN_VALUE :  proxy.getData().get(0);
        } 
        catch (SQLException ex) {
            System.getLogger(ClassReadMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return classNum;
    } 

    public List<String> getAllClasses () {
   
        List<String> classes = null;
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            ReadAllClassesProxy proxy = new ReadAllClassesProxy ();
            ReadClassData classData = new ReadClassData(proxy);
            classData.setConnection(connection);
            classData.executeRead();
            classes = proxy.getData();
        } catch (SQLException ex) {
            System.getLogger(ClassReadMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return classes;
    }    
}
