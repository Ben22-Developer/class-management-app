package app.classmanagementappclient.Medium.ClassYear.ReadClassYear;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Nums.ReadAllYearsProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Nums.ReadOneClassYearNumProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Nums.ReadOneClassYearNumProxyV1;
import app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.ReadClassYearData;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassYearMedium 
{
        public int getOneClassYearNum (ClassYear classYear) {
        
        int classYearNum = 0;
        try (Connection connection = MediumUtility.getConnection()) 
        {
            int classNum = classYear.getClassNum();
            int year = classYear.getYear();
            ReadOneClassYearNumProxy proxy = new ReadOneClassYearNumProxy(year, classNum);
            ReadClassYearData classYearData = new ReadClassYearData(proxy);
            classYearData.setConnection(connection);
            classYearData.executeRead();
            classYearNum = proxy.getData().get(0);
        } 
        catch (SQLException ex) {
            System.getLogger(ReadClassYearMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return classYearNum;
    }
        
        public int getOneClassYearNumV1 (String schoolName, String className, int classYear) {
        
        int classYearNum = 0;
        try (Connection connection = MediumUtility.getConnection()) 
        {   
            ReadOneClassYearNumProxyV1 proxy = new ReadOneClassYearNumProxyV1(schoolName, className, classYear);
            ReadClassYearData classYearData = new ReadClassYearData(proxy);
            classYearData.setConnection(connection);
            classYearData.executeRead();
            classYearNum = proxy.getData().isEmpty() ? Integer.MIN_VALUE :  proxy.getData().get(0);
        } 
        catch (SQLException ex) {
            System.getLogger(ReadClassYearMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return classYearNum;
    }        
        

    public List<Integer> getAllYears () {
   
        List<Integer> years = null;
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            ReadAllYearsProxy proxy = new ReadAllYearsProxy ();
            ReadClassYearData classYearData = new ReadClassYearData(proxy);
            classYearData.setConnection(connection);
            classYearData.executeRead();
            years = proxy.getData();
        } catch (SQLException ex) {
            System.getLogger(ReadClassYearMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return years;
    }            
}
