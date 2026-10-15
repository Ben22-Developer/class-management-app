package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadDoublesInterface extends ReadDataInterface {
    @Override
    public List<Double> getData();
}
