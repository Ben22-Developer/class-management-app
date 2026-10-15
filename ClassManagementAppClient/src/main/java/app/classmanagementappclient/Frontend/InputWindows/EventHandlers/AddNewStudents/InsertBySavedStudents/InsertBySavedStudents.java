package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.InsertBySavedStudents;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.SubClasses.SavedStudentTableDisplayInsert;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.TableDisplayHelperClass;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertBySavedStudents {

    private final InputWindow inputWindow;
    
    public InsertBySavedStudents(InputWindow inputWindow) {
        this.inputWindow = inputWindow;
    }
    
    public void runProcess() {
        
        var allStudentDetails = new StudentMedium().getAllStudents();
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("","No","Student ID", "Student Name"), List.of(2,3), allStudentDetails, 400);
        TableDisplayHelperClass manipulator = new SavedStudentTableDisplayInsert(table, inputWindow);
        manipulator.run();
    }
}
