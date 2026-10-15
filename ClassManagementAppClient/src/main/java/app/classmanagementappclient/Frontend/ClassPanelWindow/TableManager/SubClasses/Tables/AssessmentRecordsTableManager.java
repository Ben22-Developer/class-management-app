package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables;

import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records.RecordLogic;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable.MultipleAssessmentRecordsInsert;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable.AssessmentUpdateDialog;
import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentRecordsTableManager  extends DataEntry1  {
    
    private String assessmentName;
    private String assessmentDate;
    private String totalMarks;
    
    public AssessmentRecordsTableManager(String assessmentName, String totalMarks, String assessmentDate,List<List<String>> data, int asessmentNum, Table table, JPanel tablePanel, String lessonName, Table parentTable, List<Integer> clmIndexToCheck, int selectedRow, DataEntryActions proxy) {
        super(data, asessmentNum, table, tablePanel, lessonName, parentTable, clmIndexToCheck, selectedRow, proxy);
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.totalMarks = totalMarks;
    }

    @Override
    public void edit() {
        
        var thisObj = this;
        
        new SwingWorker<Object, List<String>> () {
            @Override
            protected Object doInBackground() throws Exception {
                //int lessonNum, String assessmentName, String assessmentDate, String totalMarks
                AssessmentsTable table =  (AssessmentsTable) getParentTable();
                var dialog = new AssessmentUpdateDialog (thisObj.getIdentifierNum(), table.getLessonNum(), assessmentName, assessmentDate, totalMarks);
                dialog.drawJDialog();
                
                List<String> data = dialog.getData();
                if (data != null)
                    publish(data);
                return null;
            }

            @Override
            protected void process(List<List<String>> chunks) {
                setDateTitleDetails(chunks.get(0));
                thisObj.getParentTable().setValueAt(thisObj.getSelectedRow(),chunks.get(0));
                setDateTitle();
            }
            
        }.execute();
    }

    @Override
    public void multipleInsertion() {
        var insertHandler = new MultipleAssessmentRecordsInsert(this);
        insertHandler.run();
    }
    
    public void multipleInsertion (List<List<String>> data, String report) {
        
        var thisObj = this;
        
        new SwingWorker<Object, List<String>> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                var tracker = thisObj.getTracker();
                setTracker(tracker);
                
                int i = 0;
                StringBuilder builder = new StringBuilder();
                var recordLogic = new RecordLogic();
                
                if (!report.isBlank())
                    builder.append(report.toString()).append("\n");
                
                for (List<String> dataPiece : data) {
                    
                    System.out.println(dataPiece.get(1) +"\t"+ dataPiece.get(3) +"\t"+ thisObj.getIdentifierNum());
                    
                    boolean res = recordLogic.assessmentRecord_NoMsg(dataPiece.get(1), dataPiece.get(3), thisObj.getIdentifierNum());
                    
                    if (!res) {
                        builder.append(recordLogic.getMsg() + "\n");
                        continue;
                    }
                    
                    publish(dataPiece);
                    i += 1;
                    tracker.setLoadingPercentage(i, data.size());
                }
                
                tracker.endProgress();
                new ShowReport().showReport(builder.toString(), "Student's marks successfully inserted");
                
                return null;
            }  

            @Override
            protected void process(List<List<String>> chunks) {
                
                Table table = thisObj.getTable();
                
                for (List<String> chunk : chunks)
                    table.insertTableARow(table.getjTable().getRowCount(), getFormattedChunk(chunk));
                
                thisObj.setTitle("Total recorded assessments: " + table.getjTable().getRowCount());
            }
            
            private List<Object> getFormattedChunk (List<String> chunk) {
                
                List<Object> formatted = new ArrayList<>();
                
                formatted.add(false);
                chunk.forEach(formatted::add);
                formatted.add("Edit");
                return formatted;
            }
            
            private void setTracker (ProgressTracker tracker) {
                tracker.setTableLoadingPercentage(0);
                tracker.run();
            }
        }.execute();
    }

    @Override
    public String getWindowTitle() {
        return super.getLessonName() + " Assessment Records";
    }
    
    public void setDateTitleDetails (List<String> details) {
        
        assessmentName = String.valueOf(details.get(0));
        assessmentDate = String.valueOf(details.get(1));
        totalMarks = String.valueOf(details.get(2));
        
    }

    @Override
    public void setDateTitle() {
        super.getDateLabel().setText("Asessment: " + assessmentName + "  Assessment Date: " + assessmentDate + " Total Marks: " + totalMarks);
    }
}
