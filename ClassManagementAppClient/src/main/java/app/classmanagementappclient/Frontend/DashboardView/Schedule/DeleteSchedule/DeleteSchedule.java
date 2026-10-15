/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.DeleteSchedule;

import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.Common.SearchableTableDeleteHandler.DeleteSearchableTableSelectedRowsProxy;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class DeleteSchedule extends DeleteSearchableTableSelectedRowsProxy {

    public DeleteSchedule(Table table, ProgressTracker progressTracker, JComponent[] components) {
        super(table, progressTracker, components);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {
        
        
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;
   
        return Loop.LOOP;           
    }
    
    
}
