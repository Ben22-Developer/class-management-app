package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ClassExistanceChecker 
{
    private final String schoolName;
    private final String className;
    private final int year;
    private final int term;
    private int classTermNum;

    public ClassExistanceChecker (String schoolName, String className, int year, int term) {
        
        this.schoolName = schoolName;
        this.className = className;
        this.year = year;
        this.term = term;
    }
    
    public void checkClassExistance (ShowMsgInterface proxy) 
    {
        if (!areBoxesFilled())
            return;
        
        int num = new ClassTermMedium().getOneClassTermNumV1(schoolName, className, year, term);
        
        setClassTermNum(num);
        
        proxy.showMsg(this);
    }
    
    private boolean areBoxesFilled () {
        
        boolean isEmpty = schoolName.isBlank() || className.isBlank() || year == 0 || term == 0;
        
        if (isEmpty) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("The data must be filled in all boxes!","Error Message", JOptionPane.ERROR_MESSAGE);
        }
        
        return !isEmpty;
    }

    public int getClassTermNum() {
        return classTermNum;
    }

    public void setClassTermNum(int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getClassName() {
        return className;
    }

    public int getYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }    
    
    public static ClassExistanceChecker getClassExistanceChecker (InputWindow inputWindow) {
        
        String schoolName = String.valueOf( inputWindow.getRecordedSchoolNewStudentComboBox().getSelectedItem() );
        String className = String.valueOf( inputWindow.getRecordedClassNewStudentComboBox().getSelectedItem() );
        int year = Integer.parseInt( String.valueOf( inputWindow.getRecordedYearNewStudentComboBox().getSelectedItem() ));
        int term = Integer.parseInt( String.valueOf( inputWindow.getRecordedTermsNewStudentComboBox().getSelectedItem() ));
        ClassExistanceChecker checker = new ClassExistanceChecker(schoolName, className, year, term);
        return checker;
    }
}
