package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassStudentsTable extends Table implements SearchableTable {

    private final int classTermNum;

    public ClassStudentsTable(int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public int getClassTermNum() {
        return classTermNum;
    }
    
    public static ClassStudentsTable getClassStudentsTable (List<List<String>> rowsData, int classTermNum, int...clmWidth) 
    {
        ClassStudentsTable table = new ClassStudentsTable (classTermNum);
        table.setup(table, EditableTableType.IsClassStudentsTable, true, false, "", List.of("", "No", "Student ID", "Student Name"), List.of(2,3), rowsData, clmWidth);
        return table;
    }    
}
