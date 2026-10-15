package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentsTable extends Table implements SearchableTable {
    
    private final int lessonNum;

    public AssessmentsTable (int lessonNum) {
        this.lessonNum = lessonNum;
    }

    public int getLessonNum() {
        return lessonNum;
    }
    
    public static AssessmentsTable getAssessmentsTable (List<List<String>> rowsData, int lessonNum, int...clmWidth) {
    
        AssessmentsTable table = new AssessmentsTable (lessonNum);
        table.setup(table, EditableTableType.IsAssessmentTable, true, true, "View", List.of("", "No", "Assessment Name", "Assessment Date", "Total Marks", "Records"), List.of(2,3,4), rowsData, clmWidth);
        return table;
    }         
}
