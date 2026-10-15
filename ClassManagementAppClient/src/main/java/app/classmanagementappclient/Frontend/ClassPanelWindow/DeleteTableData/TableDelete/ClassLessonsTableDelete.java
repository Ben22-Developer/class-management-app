package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassLessonsTableDelete extends DeleteTableDataProxy {
    
    public ClassLessonsTableDelete(ClassLessonsTable table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {

        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);
        ClassLessonsTable classLessonsTable = (ClassLessonsTable) table;
        int classTermNum = classLessonsTable.getClassTermNum();
        String lessonName = rowData.get(0);

        ClassLessonMedium medium = new ClassLessonMedium();
        int classLessonNum = medium.getOneClassLessonNum(classTermNum, lessonName);
        boolean res = medium.deleteClassLesson(classLessonNum);
        if (!res) {
            return Loop.BREAK;
        }
        return Loop.LOOP;
    }
}
