package app.classmanagementappclient.Frontend.StatisticsWindow;

import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.ChartUtility;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Implement.LessonTableLoader;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Implement.StudentTableLoader;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.TableLoaderUtility;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.WindowLoad.StatisticsWindowUILoad;
import app.classmanagementappclient.Frontend.StatisticsWindow.Report.StatisticsDataReport;
import app.classmanagementappclient.Frontend.StatisticsWindow.TermDialog.StatisticsTermDialog;

/**
 *
 * @author BEN
 */
public class StatisticsWindowUtility {
    
    public static void loadWindow () {
        StatisticsWindowUILoad.loadWindow();
    }
    
    public static void loadTermsDialog (StatisticsWindowDialog window) {
        
        var termDialog = new StatisticsTermDialog( 
                window.getSchoolBox().getSelectedItem().toString(), 
                window.getClassBox().getSelectedItem().toString(), 
                Integer.parseInt( window.getAcademicYearBox().getSelectedItem().toString() ), window);
        
        termDialog.classTermDialog();
    }
    
    public static void loadLessonsDialog (StatisticsWindowDialog window) {

        var contract = new LessonTableLoader();
        new TableLoaderUtility(window, "Class Lessons", contract).loadTable();
    }
    
    public static void loadStudentsDialog (StatisticsWindowDialog window) {

        var contract = new StudentTableLoader();
        new TableLoaderUtility(window, "Class Students", contract).loadTable();
    }    
    
    public static void loadChart (StatisticsWindowDialog window) {
        new ChartUtility(window).run();
    }
    
    public static void showReport (StatisticsWindowDialog window) {
        StatisticsDataReport.renderReport(window);
    }
}
