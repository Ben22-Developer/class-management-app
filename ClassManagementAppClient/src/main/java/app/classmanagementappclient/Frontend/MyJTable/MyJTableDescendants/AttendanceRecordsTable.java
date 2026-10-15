package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceRecordsTable extends Table implements SearchableTable {

    private final int attendanceNum;

    public AttendanceRecordsTable (int attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public int getAttendanceNum() {
        return attendanceNum;
    }
    
    public static AttendanceRecordsTable getAttendanceRecordsTable (List<List<String>> rowsData, int attendanceNum, int...clmWidth) {
    
        AttendanceRecordsTable table = new AttendanceRecordsTable (attendanceNum);
        table.setup(table, EditableTableType.IsAttendanceRecordsTable, true, false, "", List.of("","No", "Student ID", "Student Name"), List.of(2,3), rowsData, clmWidth);
        return table;
    }       
}
