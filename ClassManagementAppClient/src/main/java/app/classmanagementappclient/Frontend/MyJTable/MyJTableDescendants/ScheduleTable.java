/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class ScheduleTable extends Table implements SearchableTable {

    private final JLabel connectionLabel;
    
    public ScheduleTable(JLabel connectionLabel) {
        this.connectionLabel = connectionLabel;
    }

    public JLabel getConnectionLabel() {
        return connectionLabel;
    }
    
    public static ScheduleTable getScheduleTable (List<List<String>> rowsData, JLabel connectionLabel, int...clmWidth) {
    
        ScheduleTable table = new ScheduleTable (connectionLabel);
        table.setup(table, EditableTableType.IsScheduleTable, true, true, "View", List.of("", "No", "Title", "Description", "Date", "View"), List.of(2,3,4), rowsData, clmWidth); // if u change the clm headers check if it fits with UpdateScheduleTable
        return table;
    }       
}
