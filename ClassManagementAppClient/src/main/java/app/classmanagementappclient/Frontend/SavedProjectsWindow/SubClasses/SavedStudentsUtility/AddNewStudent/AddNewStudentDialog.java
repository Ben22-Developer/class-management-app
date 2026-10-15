package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.AddNewStudent;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.SavedStudentsUtility;

import java.util.List;

/**
 *
 * @author BEN
 */
public class AddNewStudentDialog extends ADialog {
    
    private final SavedStudentsUtility utility;

    public AddNewStudentDialog(SavedStudentsUtility utility) {
        this.utility = utility;
    }
    
    @Override
    public void drawJDialog() {
        setHeaders(List.of("Student ID", "Student Name"));
        setValues(List.of("",""));
        setTitleLable("Add");
        setWindowTitle("Add Student");
        super.drawJDialog();
    }
    
    @Override
    public boolean handler() {
        
        List<String> userInputs = getUserInputs();
        
        String studentID = userInputs.get(0);
        String studentName = userInputs.get(1);
        
        boolean res = new StudentMedium(new Student(studentID, studentName)).insertStudent();
        if (res)
            addRow(userInputs);
        
        return res;
    }
    
    private void addRow (List<String> userInputs) {
        utility.populateUserInputs(userInputs);
    }
}
