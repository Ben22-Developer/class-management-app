package app.classmanagementappclient.Backend.Database.Read.ReadSchoolData.InterfacesImplementation.Object;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Objects.School.School;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadSchoolObject extends ReadDataInterface {
    @Override
    public List<School> getData();
}
