package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.AddNewStudent;

import app.classmanagementappclient.Frontend.DoubleClickables.DoubleClickable;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarner;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.Warners.ExcelStudentFileWarning;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.AddStudentViaExcel.AddExcelStudentsData;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.SavedStudentsUtility;

/**
 *
 * @author BEN
 */
public class AddNewStudent implements DoubleClickable, ExcelWarner {

    private final SavedStudentsUtility utility;

    public AddNewStudent(SavedStudentsUtility utility) {
        this.utility = utility;
    }
    
    @Override
    public void clickHandler() {
        var dialog = new AddNewStudentDialog (utility);
        dialog.drawJDialog();
    }

    @Override
    public void doubleClickHandler() {
        
        if (FrontEndUtility.canDisplayWarningMsg("ExcelStudentFileFormatWarning")) 
            new ExcelStudentFileWarning(this).displayWarningMsg();
        else 
            afterWarnRun();
        
    }

    @Override
    public void afterWarnRun() {
        new AddExcelStudentsData(utility).execute();
    }
    
}
