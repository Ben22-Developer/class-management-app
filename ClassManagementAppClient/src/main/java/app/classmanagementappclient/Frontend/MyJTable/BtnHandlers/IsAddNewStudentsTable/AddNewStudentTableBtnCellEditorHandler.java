package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAddNewStudentsTable;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AddNewStudentTableBtnCellEditorHandler 
{
    private final Table table;
    private final int row;
    private final List<Integer> clmsToCheck;
    private final List<String> newData;

    public AddNewStudentTableBtnCellEditorHandler(Table table, int row, List<Integer> clmsToCheck, List<String> newData) {
        this.table = table;
        this.row = row;
        this.clmsToCheck = clmsToCheck;
        this.newData = newData;
    }
    
    public boolean validate () 
    {
        Student student = new Student(newData.getFirst(), newData.getLast());
        StudentMedium medium = new StudentMedium(student);
        boolean isValid = medium.validation();
        
        if (isValid) 
            table.setValueAt(row, newData);
        else
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(medium.getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
        return isValid;
    }
    
    
}
