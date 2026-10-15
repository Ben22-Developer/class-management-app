package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAssessmentRecordsTable;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class UpdateAssessmentRecordsTable {
    
    private final List<String> currRecordsData;
    private final List<String> newRecordsData;
    private final Table table;
    private final int row;
    
    public UpdateAssessmentRecordsTable(Table table, int row, List<String> currRecordsData, List<String> newRecordsData) {
        this.currRecordsData = currRecordsData;
        this.newRecordsData = newRecordsData;
        this.table = table;
        this.row = row;
    }
    
    public boolean run () {
        
        var thiss = this;
        SwingWorker sw = new SwingWorker<Boolean, String>() 
        {
            private final AssessmentRecordsTable table = (AssessmentRecordsTable) thiss.table;
            
            @Override
            protected Boolean doInBackground() throws Exception {
                
               int assessmentNum = table.getAssessmentNum();
               int studentClassNum = new StudentClassMedium().getStudentClassNumByAssessment(assessmentNum, currRecordsData.get(0));
               
               if (studentClassNum == Integer.MIN_VALUE)
                   return false;
               
               boolean res = new AssessmentRecordMedium().updateAssessmentRecord(assessmentNum, studentClassNum, currRecordsData.get(1), newRecordsData.get(1));
               if (res)
                   publish (newRecordsData.get(1));
               return res;
            }

            @Override
            protected void process(List<String> chunks) { 
                table.getjTable().getModel().setValueAt(chunks.get(0), row, 4);
            }
        };
        
        sw.execute();
        Boolean bool = null;
        try {
            bool = (Boolean) sw.get();
        } catch (Exception ex) {} 
        
        return bool;
    }
}
