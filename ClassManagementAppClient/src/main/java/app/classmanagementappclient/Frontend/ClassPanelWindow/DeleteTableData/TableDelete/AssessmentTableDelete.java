package app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.Common.Enum.Loop;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentTableDelete extends DeleteTableDataProxy {
    
    public AssessmentTableDelete (Table table, TableManager tableManager, ProgressTracker progressTracker) {
        super(table, tableManager, progressTracker);
    }

    @Override
    public Loop deleteData (Table table, int tableRowIndex) {
    
        AssessmentsTable assessmentTable = (AssessmentsTable) table;
        tableRowIndex = table.getTableRowSorter().convertRowIndexToView(tableRowIndex);

        if (tableRowIndex == -1) // kwa kundi ya tickinga ariko ya searchinga ikava muri view
            return Loop.CONTINUE;

        List<String> rowData = table.getRowDataAt(tableRowIndex);
        
        var medium = new AssessmentMedium();
        
        int lessonNum = assessmentTable.getLessonNum();
        String lessonName = rowData.get(0);
        java.sql.Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate (rowData.get(1));
        
        int assessmentNum = medium.getAssessmentNum(lessonNum, lessonName, date);
        boolean res = medium.deleteLessonAssessment(assessmentNum);
        
        return res ? Loop.LOOP : Loop.BREAK;  
    }
}
