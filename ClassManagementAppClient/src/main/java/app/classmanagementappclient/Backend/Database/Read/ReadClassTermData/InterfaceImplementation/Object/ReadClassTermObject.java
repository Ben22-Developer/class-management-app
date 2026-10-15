package app.classmanagementappclient.Backend.Database.Read.ReadClassTermData.InterfaceImplementation.Object;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadClassTermObject extends ReadDataInterface {
    @Override
    public List<ClassTerm> getData();
}
