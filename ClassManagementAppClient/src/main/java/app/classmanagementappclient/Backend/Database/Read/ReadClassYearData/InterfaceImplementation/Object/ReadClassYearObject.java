package app.classmanagementappclient.Backend.Database.Read.ReadClassYearData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadClassYearObject extends ReadDataInterface {
    @Override
    public List<ClassYear> getData();
}
