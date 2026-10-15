package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.OneByOneInsert;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.ClassOptions.ClassOptions;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.StudentInsertParent;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class OneByOneStudentInsert extends StudentInsertParent
{
    private final Table table;
    private final TableDisplay tableDisplay = new TableDisplay();
    private final JButton selectAllBtn = new JButton("Select All");
    private final JButton selectNoneBtn = new JButton("Select None");
    private final JButton removeSelectedBtn = new JButton("Remove Selected");    
    private final JButton classOptionsBtn = new JButton("Class Options");    
    private final JButton addStudentBtn = new JButton("Add Student");    
    private final JButton submitBtn = new JButton("Submit");    
    
    public OneByOneStudentInsert (InputWindow inputWindow) {
        super (inputWindow);
        table = Table.getTable(EditableTableType.IsAddNewStudentsTable, true, true, "Edit", List.of("","Student ID", "Student Name", "Edit"), List.of(1,2), new ArrayList<>(), 500);
    }
    
    public void run () {
        setTableDisplay();
        setCenterPanel();
        setSouthPanel();
        addEvents();
        finalTableDisplaySetup();
    }
    
    public void setTableDisplay () {
        super.setTableDisplayTitle(tableDisplay);
    }
    
    private void setCenterPanel () {
        tableDisplay.getScrollPaneContainer().setViewportView(table.getjTable());
    }
    
    private void setSouthPanel () {
        setBtns();
        FrontEndUtility.addJComponentToPanel (tableDisplay.getSouthPanel(), new JComponent[] { selectAllBtn, selectNoneBtn, removeSelectedBtn, addStudentBtn, classOptionsBtn, submitBtn });
    }
    
    private void setBtns () {
        FrontEndUtility.setJComponentDimension(submitBtn, new Dimension(80, 40));
        FrontEndUtility.setJComponentDimension(selectAllBtn, new Dimension(100, 40));
        FrontEndUtility.setJComponentDimension(selectNoneBtn, new Dimension(120, 40));
        FrontEndUtility.setJComponentDimension(removeSelectedBtn, new Dimension(155, 40));
        FrontEndUtility.setJComponentDimension(addStudentBtn , new Dimension(120, 40));
        FrontEndUtility.setJComponentDimension(classOptionsBtn, new Dimension(130, 40));
        FrontEndUtility.setJComponentsDefaultFont(new JButton[] { selectAllBtn, selectNoneBtn, removeSelectedBtn, addStudentBtn, classOptionsBtn,  submitBtn });          
    }
    
    private void addEvents () {
        
        Table.setCommonEventHandlers(new JButton[] { selectAllBtn, selectNoneBtn }, table);
        
        removeSelectedBtn.addActionListener((l) -> {
            table.removeAllTableCheckedRows();
        }); 
        
        addStudentBtn.addActionListener((l) -> {
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    new InsertOneStudentDialog(table).run();
                    return null;
                }
            }.execute();            
        });
        
        classOptionsBtn.addActionListener((l) -> {
            
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    new ClassOptions( getInputWindow() ).run();
                    return null;
                }
                
                @Override
                public void done () {
                    setTableDisplay();
                }
            }.execute();
        });
        
        submitBtn.addActionListener((l) -> {
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    
                    for (JComponent comp : new JComponent[] { selectAllBtn, selectNoneBtn, removeSelectedBtn, classOptionsBtn, addStudentBtn, submitBtn, table.getjTable() }) 
                        comp.setEnabled(false);
                    submitData();
                    return null;
                }
                
                @Override
                public void done () {
                    for (JComponent comp : new JComponent[] { selectAllBtn, selectNoneBtn, removeSelectedBtn, classOptionsBtn, addStudentBtn, submitBtn, table.getjTable() }) 
                        comp.setEnabled(true);
                }
            }.execute();
            
        });        
    }
    
    private void finalTableDisplaySetup () {
        tableDisplay.setCustomTableDisplayProperties(150, 60, new Dimension(1020,480));
        tableDisplay.useJDialog(table);
        tableDisplay.getDialog().setTitle("Students Input");
    } 
    
    private void submitData() {
        List<List<String>> allData = table.getDataInRows();
        super.submitData(allData, table,  new int[] {0,1});
    }    
}
