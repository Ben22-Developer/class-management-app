package app.classmanagementappclient.Frontend.MyJTable.TableStructure;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author BEN
 */
public interface SearchableTable {
    public default void searchInTable (String txt, Table table) {   
        TableRowSorter<TableModel> tableRowSorter = table.getTableRowSorter();
        
        if (txt == null) {
            
            if (table.getjTable().getRowCount() == 0)
                return;
            
            tableRowSorter.setRowFilter(null);
        }
        else
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+txt));
    }
    
    public static void cleanSearchInTable (Table table) {
        if (table instanceof SearchableTable searchableTable) 
            searchableTable.searchInTable(null, (Table)table);
    }
}
