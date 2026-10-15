package app.classmanagementappclient.Backend.Database.Read.ReadClassStudentInfo.Students;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadClassStudentsInterface extends ReadDataInterface {
    @Override
    public List<List<String>>getData ();
}
