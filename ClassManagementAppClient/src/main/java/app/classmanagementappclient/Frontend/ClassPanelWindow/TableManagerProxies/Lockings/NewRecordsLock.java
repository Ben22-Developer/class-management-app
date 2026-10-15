package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.Lockings;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1Int;

/**
 *
 * @author BEN
 */
public class NewRecordsLock {
    
    
    public Object[] getNewRecords (DataEntry1Int manager) {
        
        manager.getLock().lock();
        try {
            while (manager.isNextRecord()) {
                manager.getCondition().await();
            }
        }
        catch (InterruptedException ex) {System.out.println("Interrupted");}
        finally {
            manager.getLock().unlock();
        }
        return manager.getNewRecords();
    }    
}
