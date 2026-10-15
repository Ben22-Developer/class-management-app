package app.classmanagementappclient.Frontend.StatisticsWindow.Load.WindowLoad;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Frontend.Common.AllDataComboBoxFiller;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class StatisticsWindowUILoad {
    
    public static void loadWindow () {
        var statisticsDialog = new StatisticsWindowDialog();
        SwingUtilities.invokeLater(() -> {
            loadWindow(statisticsDialog);
        });
    }
    
    private static void loadWindow (StatisticsWindowDialog statisticsDialog) {
        setComboBoxData(statisticsDialog);
        statisticsDialog.setVisible(true);
    }
    
    private static void setComboBoxData (StatisticsWindowDialog statisticsDialog) 
    {
        AllDataComboBoxFiller.setComboBoxes
        (statisticsDialog.getSchoolBox(),
         statisticsDialog.getClassBox(),
         statisticsDialog.getAcademicYearBox(),
         new SchoolMedium().getAllSchools(), new ClassMedium().getAllClasses(), new ClassYearMedium().getAllYears());        
    }
}
