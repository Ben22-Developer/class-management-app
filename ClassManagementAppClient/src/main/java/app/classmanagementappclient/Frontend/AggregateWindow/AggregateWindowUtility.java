package app.classmanagementappclient.Frontend.AggregateWindow;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTermsDialog.AssessmentTermsDialog;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.GenerateReport.GenerateAssessmentReport;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.RestartAssessmentWindow.RestartAssessmentWindow;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.AttendanceTermsDialog.AttendanceTermsDialog;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.ViewReport.ViewReportHandler;
import app.classmanagementappclient.Frontend.AggregateWindow.WindowLoad.WindowLoad;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class AggregateWindowUtility {
    
    public static void loadWindow () {
        new WindowLoad().loadWindow();
    }
    
    public static void attendanceButtonActionPerformed (JPanel aggregatePanel) {
        new WindowLoad().showAttendancePanel(aggregatePanel, (CardLayout) aggregatePanel.getLayout());
    }    
    
    public static void assessmentButtonActionPerformed (JPanel aggregatePanel) {
        new WindowLoad().showAssessmentPanel(aggregatePanel, (CardLayout) aggregatePanel.getLayout());
    }
    
    public static void selectTermButtonActionPerformedOnAttendance (AggregateWindow aggregateWindow) {
        
        String schoolName = String.valueOf( aggregateWindow.getRecordedSchoolNewStudentComboBox_AttendancePanel().getSelectedItem() ); 
        String className = String.valueOf( aggregateWindow.getRecordedClassNewStudentComboBox_AttendancePanel().getSelectedItem() );
        int year = Integer.parseInt( String.valueOf(aggregateWindow.getRecordedYearNewStudentComboBox_AttendancePanel().getSelectedItem()) );
        
        
        new AttendanceTermsDialog(schoolName, className, year, aggregateWindow).classTermDialog();
    }   
    
    public static void attendanceReportViewButtonActionPerformed (AggregateWindow aggregateWindow) {
        new ViewReportHandler(aggregateWindow).run();
    }
    
    public static void selectTermButtonActionPerformedOnAssessment(AggregateWindow aggregateWindow) {
        
        String schoolName = String.valueOf( aggregateWindow.getRecordedSchoolNewStudentComboBox_AssessmentPanel().getSelectedItem() ); 
        String className = String.valueOf( aggregateWindow.getRecordedClassNewStudentComboBox_AssessmentPanel().getSelectedItem() );
        int year = Integer.parseInt( String.valueOf(aggregateWindow.getRecordedYearNewStudentComboBox_AssessmentPanel().getSelectedItem()) );
        new AssessmentTermsDialog(schoolName, className, year, aggregateWindow).classTermDialog();
    }     
    
    public static void generateAssessmentReportActionPerformed (AggregateWindow aggregateWindow) {
        new GenerateAssessmentReport(aggregateWindow).run();
    }

    static void processRestartButtonActionPerformed(AggregateWindow aggregateWindow) {
        new RestartAssessmentWindow(aggregateWindow).run();
    }
}
