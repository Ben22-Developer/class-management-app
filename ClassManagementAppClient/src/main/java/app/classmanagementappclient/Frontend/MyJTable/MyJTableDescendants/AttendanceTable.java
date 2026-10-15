package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendanceTable extends Table implements SearchableTable {
    
    private final int lessonNum;

    public AttendanceTable (int lessonNum) {
        this.lessonNum = lessonNum;
    }

    public int getLessonNum() {
        return lessonNum;
    }
    
    public static AttendanceTable getAttendanceTable (boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int lessonNum, int...clmWidth) {
    
        AttendanceTable table = new AttendanceTable (lessonNum);
        table.setup(table, EditableTableType.IsAttendanceTable, makeCheckBoxClm, makeBtnClm, btnClmText, clmHeaders, clmsToCheck, rowsData, clmWidth);
        return table;
    }        
    
}
