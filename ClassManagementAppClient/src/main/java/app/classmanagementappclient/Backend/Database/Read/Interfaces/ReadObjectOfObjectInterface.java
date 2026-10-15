package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadObjectOfObjectInterface extends ReadDataInterface{
    @Override
        public List<List<Object>> getData();    
}
