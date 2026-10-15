package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables.AssessmentRecordsTableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable.AssessmentRecordsTableManagerProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ViewAssessmentRecordsDialog extends ViewTables {

    private String lessonName;
    private int assessmentNum;
    private String assessmentDate;
    private String assessmentName;
    private String totalMarks;
    
    @Override
    protected int getIndentifier() {
        AssessmentsTable assessmentTable = (AssessmentsTable) super.getTable();
        lessonName = new ClassLessonMedium().getLessonName(assessmentTable.getLessonNum());
        return lessonName.isBlank() ? Integer.MIN_VALUE : assessmentTable.getLessonNum();
    }
    
    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("This class might have been deleted\nPlease consider re-loading the tables for getting fresh data", "Informational Message", JOptionPane.INFORMATION_MESSAGE);    
    }

    @Override
    public List<List<String>> getData(int identifier) {
        
        List<String> data = super.getSelectedRowData();
        assessmentName = data.get(0);
        assessmentDate = data.get(1);
        totalMarks = data.get(2);
        
        Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate (assessmentDate);
        
        setAssessmentNum(identifier, date);
        List<List<String>> attendedStudent = getRecordedStudentsOnSpecificAssessment();
        
        return attendedStudent;
    }
    
    private void setAssessmentNum (int lessonNum, Date date) {
        assessmentNum = new AssessmentMedium().getAssessmentNum(lessonNum, assessmentName, date);
    }
    
    private List<List<String>> getRecordedStudentsOnSpecificAssessment () {
        return new AssessmentRecordMedium().getRecordedStudentsOnSpecificAssessment(assessmentNum);
    }

    @Override
    public void display(List<List<String>> data) {
        
        int selecedRow = super.getRow();
        Table table = super.getTable();
        List<Integer> clmIndexToCheck = super.getClmIndexToCheck();
        AssessmentRecordsTable recordsTable = AssessmentRecordsTable.getAssessmentRecordsTable(data, assessmentNum, 300);
        
//        System.out.println("assessment Num: " + assessmentNum);
        
        DataEntry1 manager = new AssessmentRecordsTableManager(assessmentName, totalMarks, assessmentDate, data, assessmentNum, recordsTable, new JPanel(new BorderLayout()), lessonName, table, clmIndexToCheck, selecedRow, new AssessmentRecordsTableManagerProxy());
        manager.run();
    }
}
