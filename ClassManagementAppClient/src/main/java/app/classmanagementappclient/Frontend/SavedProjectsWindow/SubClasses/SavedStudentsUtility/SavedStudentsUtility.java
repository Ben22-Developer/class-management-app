package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility;

import app.classmanagementappclient.Medium.Student.ReadStudent.ReadStudentMedium;
import app.classmanagementappclient.Frontend.DoubleClickables.TimerHandler;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.SavedStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DeleteSelected.SavedStudentsDelete;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SavedProjectsWindowUtility;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.AddNewStudent.AddNewStudent;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.DisplayStudentClasses.DataDisplay;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.DisplayStudentClasses.DisplayStudentClasses;
import app.classmanagementappclient.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedStudentsUtility extends SavedProjectsWindowUtility {
    
    private SavedStudentsTable table;
    private final JButton addNewBtn = new JButton("+");
    private final JButton displayStudentClasses = new JButton (new ImageIcon(Main.getApplicationFilesPaths().getProperty("EyeIcon")));
    
    @Override
    protected void setComponentsSize(ArrayList<int[]> componentsSize) {
        super.setComponentsSize(componentsSize);
        FrontEndUtility.setJComponentCustomFont(addNewBtn, new Font("Arial", Font.BOLD, 40));
        FrontEndUtility.setJComponentDimension(addNewBtn, new Dimension(60,40));
    }    

    @Override
    protected void setSouthPanel(JComponent[] components) {
        JComponent[]components1 = { components[0], components[1], addNewBtn, displayStudentClasses, components[2], components[3], components[4], components[5]};
        JPanel panel = super.getTableDisplay().getSouthPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 5));
        FrontEndUtility.addJComponentToPanel(panel, components1);
    }

    @Override
    protected void addGUIEventListeners() {
     
        super.addGUIEventListeners();
        
        AddNewStudent addNewStudent = new AddNewStudent(this);
        TimerHandler timerHandler = new TimerHandler(addNewStudent);
        addNewBtn.addMouseListener (timerHandler);
        
        displayStudentClasses.addActionListener(l -> {
            DataDisplay dataDisplay = new DisplayStudentClasses(table);
            dataDisplay.run();
        });
    }
    
    
    @Override
    public List<List<String>> dataLoad() {
        return new ReadStudentMedium().getAllStudents();
    }
    
    @Override
    public void insertData(List<String> data) {
        SwingUtilities.invokeLater(() -> {
            table.insertTableARow (table.getjTable().getRowCount() , List.of (false, data.get(0), data.get(1), data.get(2), "Edit")); 
            setTitleLabelTxt();
        });  
    }
    
    public void populateUserInputs (List<String> userInputs) {
        insertData(List.of(String.valueOf(getTable().getjTable().getRowCount()), userInputs.get(0), userInputs.get(1)));
    }
    
    @Override
    public void setTable(List<List<String>> data) {
        table = SavedStudentsTable.getTable(data, 300);
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public void setTitleLabelTxt() {
        setTotalData(table.getjTable().getRowCount());
        super.getTableDisplay().getTitleLabel().setText( getTotalData() + " Saved Students" );
    }

    @Override
    public void deleteHandler() {
        var deleteStudent = new SavedStudentsDelete(getTable(), getProgressTracker(), super.getComponents());
        var selectedTableRows = new DeleteSelectedTableRows(deleteStudent);
        selectedTableRows.deleteRows();
    }

    @Override
    public JComponent getProgressBar() {
        return table.getProgressBar();
    }

    @Override
    public ProgressTracker getProgressTracker() {
        return table.getProgressTracker();
    }    
    
    @Override
    public JComponent[] getComponents () {
        return new JComponent[] { super.getSelectAllBtn(), super.getSelectNoneBtn(), super.getDeleteSelectedBtn(), super.getReloadDataBtn(), super.getSearchTextField(), addNewBtn, displayStudentClasses, getTable().getjTable() };
    }      
}
