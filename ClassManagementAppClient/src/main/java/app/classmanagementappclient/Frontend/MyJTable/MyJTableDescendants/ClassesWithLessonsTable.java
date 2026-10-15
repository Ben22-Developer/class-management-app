package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassesWithLessonsTable  extends Table implements SearchableTable {

    private final int classTermNum;

    public ClassesWithLessonsTable (int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public int getClassTermNum() {
        return classTermNum;
    }
    
    public static ClassesWithLessonsTable getClassWithLessonsTable (boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int classTermNum, int...clmWidth) 
    {
        ClassesWithLessonsTable table = new ClassesWithLessonsTable (classTermNum);
        table.setup(table, EditableTableType.IsShowingClassWithLessonsTable, makeCheckBoxClm, makeBtnClm, btnClmText, clmHeaders, clmsToCheck, rowsData, clmWidth);
        return table;
    }     
}