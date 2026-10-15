package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author BEN
 */
public interface DataEntry1Int extends DataEntryActions{

    public boolean isNextRecord();

    public Object[] getNewRecords();
    
    public void setNewRecords(Object[] newRecords);

    public void setNextRecord(boolean nextRecord);
    
    public abstract Lock getLock();

    public abstract Condition getCondition();
}
