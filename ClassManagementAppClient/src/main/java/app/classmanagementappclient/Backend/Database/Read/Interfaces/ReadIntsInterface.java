package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadIntsInterface extends ReadDataInterface{
    @Override
    public List<Integer> getData();
}
