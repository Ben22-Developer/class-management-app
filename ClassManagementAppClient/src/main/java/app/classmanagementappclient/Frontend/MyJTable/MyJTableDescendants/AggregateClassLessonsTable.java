package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.RowIdentifierRemoval.RowIdentifierRemoval;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AggregateClassLessonsTable extends Table {
    
    private final List<Integer> lessonNums = new ArrayList<>();
    private final  AggregateWindow window;

    public AggregateClassLessonsTable(AggregateWindow window) {
        this.window = window;
    }
    public List<Integer> getLessonNums() {
        return lessonNums;
    }

    public AggregateWindow getAggregateWindow() {
        return window;
    }
    
    public static AggregateClassLessonsTable getAssessmentRecordsTable (List<List<String>> rowsData, AggregateWindow window, int...clmWidth) {

        AggregateClassLessonsTable table = new AggregateClassLessonsTable (window);
        new RowIdentifierRemoval().populateIdentifierNums(rowsData, table.getLessonNums(), rowsData.get(0).size() - 1);
        table.setup(table, EditableTableType.IsAggregateClassLessonsTable, false, true, "Select", List.of("No", "Term", "Lesson Name", "Selected", "Select"), List.of(2), rowsData, clmWidth);
        table.getjTable().setRowSorter(null);
        return table;
    }
}
