package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadStringInterface extends ReadDataInterface {
    @Override
    public List<String> getData();
}
