package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.AttendanceTermsDialog;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.AggregateTableMaker.AggregateAttendanceClassLessonsTableMaker;
import app.classmanagementappclient.Frontend.AggregateWindow.TermsDialog.TermsDialog;
import app.classmanagementappclient.Frontend.Common.TermsDialogSubmitHandler;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class AttendanceTermsDialog extends TermsDialog {

    public AttendanceTermsDialog(String schoolName, String className, int year, TermsDialogSubmitHandler aggregateWindow) {
        super(schoolName, className, year, aggregateWindow);
    }

    @Override
    public String setClassLessonsTable(ConcurrentHashMap<Integer, Integer> termNumsToTerm) {
        return new AggregateAttendanceClassLessonsTableMaker (termNumsToTerm, super.getAggregateWindow()).drawClassLessonsTable();
    }

    @Override
    public void classTermDialog() {
        cleanClassAttendancePanel();
        emptifyAttendanceDSAs();
        super.classTermDialog(); 
    }
    
    private void cleanClassAttendancePanel () {
        
        JViewport viewport = super.getAggregateWindow().getAttendanceScrollPane().getViewport();
        Component component = viewport.getView();
        
        if (component == null)
            return;
        
        viewport.setView(null);
        FrontEndUtility.componentValidate( super.getAggregateWindow().getAttendanceTablePanel() );
    }
    
    private void emptifyAttendanceDSAs () {
        super.getAggregateWindow().emptifyAttendanceDSAs();
    }
}
