package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentRecordTableDelete extends DeleteTableDataProxy {
    
    public AssessmentRecordTableDelete (Table table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);
    }

    @Override
    public Loop deleteData(Table table, int tableRowIndex) {
    
        AssessmentRecordsTable assessmentRecordsTable = (AssessmentRecordsTable) table;
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);
        String studentID = rowData.get(0);
        int assessmentNum = assessmentRecordsTable.getAssessmentNum();
        int studentClassNum = new StudentClassMedium().getStudentClassNumByAssessment(assessmentNum, studentID);
  
        boolean res = new AssessmentRecordMedium().deleteLessonAssessment(assessmentNum, studentClassNum);
        if (!res) 
            return Loop.BREAK;        
        return Loop.LOOP;
    }
}
