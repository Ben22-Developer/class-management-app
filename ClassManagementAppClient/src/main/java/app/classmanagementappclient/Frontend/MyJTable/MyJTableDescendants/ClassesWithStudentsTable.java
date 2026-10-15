package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassesWithStudentsTable extends Table {

    private final int classTermNum;

    public ClassesWithStudentsTable (int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public int getClassTermNum() {
        return classTermNum;
    }
    
    public static ClassesWithStudentsTable getClassWithStudentsTable (boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int classTermNum, int...clmWidth) 
    {
        ClassesWithStudentsTable table = new ClassesWithStudentsTable (classTermNum);
        table.setup(table, EditableTableType.IsShowingClassWithStudentsTable, makeCheckBoxClm, makeBtnClm, btnClmText, clmHeaders, clmsToCheck, rowsData, clmWidth);
        return table;
    }       
}
