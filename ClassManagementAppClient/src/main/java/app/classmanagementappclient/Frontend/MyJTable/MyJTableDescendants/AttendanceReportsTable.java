package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceReportsTable extends Table implements SearchableTable {
    
    public static AttendanceReportsTable getAttendanceReportsTable (List<List<String>> data, List<String> headers, int...clmWidth) {
        
        AttendanceReportsTable table = new AttendanceReportsTable();
        table.setup(table, EditableTableType.IsAttendanceReportTable, true, false, "", headers, null, data, clmWidth);
        return table;
    }
}
