package app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadClassObject extends ReadDataInterface {
    @Override
    public List<Classs> getData();
}
