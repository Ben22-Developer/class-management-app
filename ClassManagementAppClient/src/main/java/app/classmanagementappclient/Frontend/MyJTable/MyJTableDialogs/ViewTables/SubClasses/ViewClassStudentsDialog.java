package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.ByOtherClassInsert.StudentInsertFromOtherClass;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassesWithStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ViewClassStudentsDialog extends ViewTables {

    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("This class might have been deleted or students in it have been deleted or edited\nPlease consider re-loading the table", "Informational Message", JOptionPane.INFORMATION_MESSAGE);    
    }
    
    @Override
    public List<List<String>> getData (int classTermNum) {
        
        List<List<String>> classStudents = new StudentClassMedium().getAllClassStudents(classTermNum);
        for (int i = 0; i < classStudents.size(); i++)
            classStudents.get(i).add(0, String.valueOf(i + 1));
        return classStudents;
    }    
    
    @Override
    public void display (List<List<String>> classStudents) 
    {
        TableDisplay tableDisplay = new TableDisplay();
        
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("","No", "Student ID", "Student Name"), List.of (2,3), classStudents);
        JButton submitBtn = getSubmitBtn(table);
        JButton selectAllBtn =  new JButton("Select All");
        JButton selectNone =  new JButton("Select None");
        JButton removeSelectedBtn = getRemoveSelectedBtn(table);
        JComponent[] components = new JComponent[] { selectAllBtn, selectNone, removeSelectedBtn, submitBtn };        
        List<String> values = super.getSelectedRowData();
        String title = values.get(0) + "/ " + values.get(1) + "/ Year " + values.get(2) + "/ Term " + values.get(3) + ", Students";        
        
        commonPropertiesSetup(new JButton[] { selectAllBtn, selectNone }, components, tableDisplay, table, title);
    }       
    
    private JButton getSubmitBtn (Table table) {
        
        JButton button = new JButton ("Submit");
        
        button.addActionListener(l -> {
            
            ClassesWithStudentsTable classStudentsTable = (ClassesWithStudentsTable) super.getTable();
            List<List<String>> allData = table.getOnViewCheckedRowsData();
            new StudentInsertFromOtherClass ( classStudentsTable.getClassTermNum() ).submitData(allData, table, new int []{ 0,1 });
            
        });
        return button;
    }
}
