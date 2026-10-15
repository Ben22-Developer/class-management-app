package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassLessonsTable extends Table implements SearchableTable {

    private final int classTermNum;

    public ClassLessonsTable (int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public int getClassTermNum() {
        return classTermNum;
    }  
    
    
    public static ClassLessonsTable getClassLessonsTable (boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int classTermNum, int...clmWidth) {
        
        ClassLessonsTable table = new ClassLessonsTable (classTermNum);
        table.setup(table, EditableTableType.IsClassLessonsTable, makeCheckBoxClm, makeBtnClm, btnClmText, clmHeaders, clmsToCheck, rowsData, clmWidth);
        return table;
    }      
}
