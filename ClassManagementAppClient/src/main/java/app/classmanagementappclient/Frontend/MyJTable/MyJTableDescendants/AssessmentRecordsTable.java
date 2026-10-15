package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentRecordsTable extends Table implements SearchableTable {

    private final int assessmentNum;

    public AssessmentRecordsTable (int assessmentNum) {
        this.assessmentNum = assessmentNum;
    }

    public int getAssessmentNum() {
        return assessmentNum;
    }
    
    public static AssessmentRecordsTable getAssessmentRecordsTable (List<List<String>> rowsData, int assessmentNum, int...clmWidth) {
        
        AssessmentRecordsTable table = new AssessmentRecordsTable (assessmentNum);
        table.setup(table, EditableTableType.IsAssessmentRecordsTable, true, true, "Edit", List.of("","No", "Student ID", "Student Name", "Student Marks", "Edit"), List.of(2,4), rowsData, clmWidth);
        return table;
    }       
}
