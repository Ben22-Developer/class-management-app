package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadStringOfStringInterface extends ReadDataInterface {
    @Override
        public List<List<String>> getData();
}
