package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ArchiveScheduleTable extends Table implements SearchableTable {
    
    private final JLabel connectionLabel;
    
    public ArchiveScheduleTable(JLabel connectionLabel) {
        this.connectionLabel = connectionLabel;
    }

    public JLabel getConnectionLabel() {
        return connectionLabel;
    }
    
    public static ArchiveScheduleTable getArchivedScheduleTable (List<List<String>> rowsData, JLabel connectionLabel, int...clmWidth)
    {
        var table = new ArchiveScheduleTable (connectionLabel);
        table.setup(table, EditableTableType.IsArchivedScheduleTable, true, true, "View", List.of("", "No", "Title", "Description", "Date", "View"), List.of(2,3,4), rowsData, clmWidth); // if u change the clm headers check if it fits with UpdateScheduleTable
        return table;
    }     
}
