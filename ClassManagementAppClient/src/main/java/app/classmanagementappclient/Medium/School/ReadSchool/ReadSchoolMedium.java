package app.classmanagementappclient.Medium.School.ReadSchool;

import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Names.ReadAllSchoolNamesProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Nums.ReadOneSchoolNum;
import app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.ReadSchoolData;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadSchoolMedium {
    public int getSchoolNum (String schoolName) {
        
        ReadOneSchoolNum proxy = new ReadOneSchoolNum(schoolName);
        executeOperation(proxy);
        return proxy.getData().isEmpty() ? Integer.MIN_VALUE :  proxy.getData().get(0);
    }
    
    public List<String> getAllSchools () {
        
        ReadAllSchoolNamesProxy proxy = new ReadAllSchoolNamesProxy();
        executeOperation(proxy);
        return proxy.getData();
    }    
    
    private void executeOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {    
            ReadSchoolData schoolData = new ReadSchoolData(proxy);
            schoolData.setConnection(connection);
            schoolData.executeRead();        
        }
        catch (Exception ex) {
            System.getLogger(ReadSchoolMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
