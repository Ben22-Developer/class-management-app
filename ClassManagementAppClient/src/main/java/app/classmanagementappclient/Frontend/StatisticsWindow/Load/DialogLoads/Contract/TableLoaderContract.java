package app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Contract;

import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

/**
 *
 * @author BEN
 */
public interface TableLoaderContract {
    
    void setSubmitHandler (Table table, StatisticsWindowDialog window, TableDisplay display);
    Table getTable (StatisticsWindowDialog window);
}
