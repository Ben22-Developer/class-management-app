package app.classmanagementappclient.Frontend.AggregateWindow.WindowLoad;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.Common.AllDataComboBoxFiller;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class WindowLoad {
    
    private final String aggregateWindowCurrentPanelPropertyKey = "AggregateWindowCurrentPanel";
    
    public void loadWindow () {
        AggregateWindow aggregateWindow = new AggregateWindow();
        SwingUtilities.invokeLater(() -> {
            setComboBoxes(aggregateWindow);
            setAggregatePanelDisplayCard (aggregateWindow);
            aggregateWindow.setVisible(true);
        });
    }
    
    private void setAggregatePanelDisplayCard (AggregateWindow aggregateWindow) {
        
        var aggregatePanel = aggregateWindow.getAggregatePanel();
        var layout = (CardLayout) aggregatePanel.getLayout();
        layout.show(aggregatePanel, getDisplayingCardKey(getDisplayingCardIndex()));
    }
    
    public void showAttendancePanel (JPanel reportsPanel, CardLayout layout) {
        layout.show(reportsPanel,  "card2");
        storeDisplayingCard("card2");
    }
    
    public void showAssessmentPanel (JPanel reportsPanel, CardLayout layout) {
        layout.show(reportsPanel,  "card3");
        storeDisplayingCard("card3");
    }
 
    private void setComboBoxes (AggregateWindow aggregateWindow) {
        
        List<String> schoolNames = new SchoolMedium().getAllSchools();
        List<String> classNames = new ClassMedium().getAllClasses();
        List<Integer> classYearNames = new ClassYearMedium().getAllYears();
        
        AllDataComboBoxFiller.setComboBoxes
        (aggregateWindow.getRecordedSchoolNewStudentComboBox_AttendancePanel(),
         aggregateWindow.getRecordedClassNewStudentComboBox_AttendancePanel(),
         aggregateWindow.getRecordedYearNewStudentComboBox_AttendancePanel(),
         schoolNames, classNames, classYearNames);
        
        AllDataComboBoxFiller.setComboBoxes
        (aggregateWindow.getRecordedSchoolNewStudentComboBox_AssessmentPanel(),
         aggregateWindow.getRecordedClassNewStudentComboBox_AssessmentPanel(),
         aggregateWindow.getRecordedYearNewStudentComboBox_AssessmentPanel(),
         schoolNames, classNames, classYearNames);       
    }
    
    private int getDisplayingCardIndex () {
        return FrontEndUtility.getWindowPanelToDisplay(aggregateWindowCurrentPanelPropertyKey);
    }
    
    private String getDisplayingCardKey (int index) {
        return switch(index) {
            case 0 -> "card2";
            case 1 -> "card3";
            default -> throw new UnsupportedOperationException("Can't find a card to display at Frontend.AggregateWindow.WindowLoad.getDisplayingCard(int index)");
        };
    }
    
    private void storeDisplayingCard (String key) {
        switch(key) {
            case "card2" -> FrontEndUtility.storeWindowPanelToDisplay(aggregateWindowCurrentPanelPropertyKey, 0);
            case "card3" -> FrontEndUtility.storeWindowPanelToDisplay(aggregateWindowCurrentPanelPropertyKey, 1);
            default -> throw new UnsupportedOperationException("Can't find a card to store at Frontend.AggregateWindow.WindowLoad.storeDisplayingCard(String key)");
        }
    }    
}
