package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.NewLessonsTable;

import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.InputWindowParentTable;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class NewLessonsTableHandler extends InputWindowParentTable
{

    public void run (InputWindow inputWindow) 
    {
        TableDisplay tableDisplay = new TableDisplay();
        JTextField textField = new JTextField();
        JButton[] buttons = new JButton[] {new JButton("Select All"), new JButton("Unselect All"), new JButton("Submit"), new JButton("Remove Selected")};
        setNewLessonTable(inputWindow);
        setTableDisplayComponentsProperties(buttons, new Dimension(120,40));
        setTableDisplayComponentsProperties(new JComponent[] { buttons[3]}, new Dimension(170,40));
        setTableDisplayTextFieldProp(textField, inputWindow);
        setTableDisplayForNewLesson(tableDisplay, textField, buttons, inputWindow.getNewLessonsTable());
        setNewLessonsTableBtnsListeners(inputWindow, tableDisplay, textField, buttons);
        addCustomTableDisplayProperties(tableDisplay, inputWindow);
        //addTableDisplayEventListener(inputWindow.getAddLessonsButton(), tableDisplay);
    }
    
   
    private void setNewLessonTable (InputWindow inputWindow) 
    {
        if (!inputWindow.isMakeNewLessonTable()) {
            return;
        }
        List<Integer> clmsToCheck = new ArrayList<>();
        setClmsToCheck(clmsToCheck);
        Table table = Table.getTable(EditableTableType.IsAddNewLessonTable ,true, true, "Edit", List.of("", "Lesson Names", "Edit"), clmsToCheck, new ArrayList<>(), 700);
        inputWindow.setNewLessonsTable(table);
        inputWindow.setCellEditor(table.getBtnEditCellEditor());
        inputWindow.setMakeNewLessonTable(false);
    }
    
    private void setClmsToCheck ( List<Integer> clmsToCheck ) {
        clmsToCheck.add(1);
    }    
    
    private void setTableDisplayForNewLesson (TableDisplay tableDisplay, JTextField textField, JButton[] buttons, Table table) 
    {
        tableDisplay.getTitleLabel().setText("Add new lesson");
        tableDisplay.getScrollPaneContainer().setViewportView(table.getjTable());
        
        JPanel panel = tableDisplay.getSouthPanel();
        panel.setLayout(new FlowLayout(SwingConstants.CENTER, 30, 10));
        panel.add(buttons[0]);
        panel.add(buttons[1]);
        panel.add(textField);
        panel.add(buttons[2]);
        panel.add(buttons[3]);
    }
        
    
    private void setNewLessonsTableBtnsListeners (InputWindow inputWindow, TableDisplay tableDisplay, JTextField textField, JButton[] btns) 
    {
        Table table = inputWindow.getNewLessonsTable();
        JTable jTable = table.getjTable();
        MyDefaultTableModel tableModel = (MyDefaultTableModel) jTable.getModel();
        
        Table.setCommonEventHandlers(new JButton[] { btns[0], btns[1] }, table);
        
        
        btns[2].addActionListener((ActionEvent e) -> {
            newLessonSubmitHandler(inputWindow, textField);
        });

        btns[3].addActionListener((ActionEvent e) -> {            
            
            removeTextAreaContent(inputWindow,  inputWindow.getNewLessonsTable());
        });        
        
        textField.addCaretListener((CaretEvent listener) -> {
            String txt = textField.getText();
            inputWindow.setNewLessonTxtFieldContent(txt);
            btns[2].setEnabled(!txt.isBlank());
        });
        
        textField.addActionListener((ActionEvent e) -> {
            newLessonSubmitHandler(inputWindow, textField);
        });
        
        tableModel.addTableModelListener((TableModelEvent e) -> 
        {   
            int row = e.getFirstRow();
            int clm = e.getColumn();
            
            if (clm != 1)
                return;
            
            Object oldValue = tableModel.getPrevObject();
            String newValue = (String) tableModel.getValueAt(row, clm);
            
            if (oldValue != null && !String.valueOf(oldValue).equals(newValue)) 
                super.updateTextAreaContent(newValue, (String) oldValue, inputWindow);
        });
        
        
    }  
    
    private void newLessonSubmitHandler (InputWindow inputWindow, JTextField textField) 
    {
        JTable jTable = inputWindow.getNewLessonsTable().getjTable();
        String lessonName = inputWindow.getNewLessonTxtFieldContent();
        Validation validation = new Validation();
        boolean isValid = validation.isLessonNameValid(lessonName);
        
        if (isValid) {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.addRow(new Object[] { Boolean.TRUE, lessonName, "Edit" });
            model.fireTableCellUpdated(jTable.getRowCount() - 1, 0);
            inputWindow.setNewLessonTxtFieldContent("");
            textField.setText("");
            appendTextAreaContent(inputWindow, inputWindow.getNewLessonsTable());
        }  
        else
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(validation.getErrorMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void removeTextAreaContent (Table table, Set<Integer> selectedRows, Set<String> textAreaContent) 
    {
        final int clm = 1;
        JTable jTable = table.getjTable();
        List<Integer> list = table.getSortedCheckedRowsList();
        table.setCheckedRowsToRemoveSynch(0);
        
        System.out.println("Size: " + list.size());
        
        for(int row : list) {   
            
            String lesson = (String) jTable.getValueAt(row, clm);
            
            if (textAreaContent.contains(lesson)) 
                textAreaContent.remove(lesson);
            
            table.calculateCheckedRowToRemoveSynch(1);
        }        
    }
}
