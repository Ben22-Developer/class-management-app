package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.ExcelFileInsert;

import app.classmanagementappclient.Frontend.ExcelManipulators.ExcelManipulators;
import app.classmanagementappclient.Frontend.ExcelManipulators.Reader.ExcelReader;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarner;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.Warners.ExcelStudentFileWarning;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.DataFilter.ExcelDataFilter;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.SubClasses.ExcelTableDisplayStudentsInsert;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.io.File;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ExcelFileStudentInsert implements ExcelWarner
{
    private final InputWindow inputWindow;

    public ExcelFileStudentInsert (InputWindow inputWindow) {
        this.inputWindow = inputWindow;
    }
    
    public void runProcess () {
        if (canDisplayWarningMsg())
            new ExcelStudentFileWarning(this).displayWarningMsg();
        else
            afterWarnRun();
    }
    
    public void afterWarnRun () {
        File file = ExcelManipulators.getExcelFile();
        if (file != null)
            readExcelFile(file);
    }
    
    private void readExcelFile (File file) 
    {
        if (!ExcelManipulators.isExcelFile(file))
            return;
        
        ExcelReader reader = ExcelReader.readExcelFile(file, 3);
        displayDataToTable(reader);
    }
    
    private void displayDataToTable (ExcelReader reader) {
        
        filterNumericsData(reader.getData());
        Table table = Table.getTable(EditableTableType.IsAddNewStudentsTable, true, true, "Edit", List.of("", "No", "Student ID", "Student Name", "Edit"), List.of(2,3), reader.getData(), 300);
        var uiTable = new ExcelTableDisplayStudentsInsert(table, inputWindow, reader.getReport().toString());
        uiTable.run();
    }
    
    private void filterNumericsData (List<List<String>> data) {
        new ExcelDataFilter().filterNumericsData(data);
    }  
    
    private boolean canDisplayWarningMsg() {
        return FrontEndUtility.canDisplayWarningMsg("ExcelStudentFileFormatWarning");
    }
}
