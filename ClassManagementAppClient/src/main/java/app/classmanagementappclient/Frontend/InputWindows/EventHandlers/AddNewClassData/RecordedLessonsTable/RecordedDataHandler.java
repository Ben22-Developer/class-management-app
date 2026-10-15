package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.RecordedLessonsTable;

import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.InputWindowParentTable;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class RecordedDataHandler extends InputWindowParentTable
{
    public void run (InputWindow inputWindow)  
    {
        TableDisplay tableDisplay = new TableDisplay();
        setRecordedLessonsTable(inputWindow);
        JButton[] tableDisplayBtns = { new JButton("Select All"), new JButton("Unselect All"), new JButton("Add Selected"), new JButton("Remove Selected") };
        setTableDisplayForRecordedLesson("Recorded Lessons", inputWindow.getRecordedLessonsTable().getjTable(), tableDisplayBtns, tableDisplay);
        setTableDisplayComponentsProperties(tableDisplayBtns, new Dimension(150,40));
        setRecordedLessonsTableBtnsListeners(tableDisplayBtns, inputWindow);
//        addTableDisplayEventListener(inputWindow.getViewRecentLessonsBtn(), tableDisplay);
        addCustomTableDisplayProperties(tableDisplay, inputWindow);
    }
    
    private void setRecordedLessonsTable (InputWindow inputWindow) 
    {
        if (!inputWindow.isMakeRecordedLessonTable())
            return;
        
        List<String> classLessons = inputWindow.getClassLessons();
        List<List<String>> recordedLessonsList = new ArrayList<>();
        List<Integer> clmsToCheck = new ArrayList<>();
        setClmsToCheck(clmsToCheck);
        setRecordedLessonsList(recordedLessonsList, classLessons);
        inputWindow.setRecordedLessonsTable(Table.getTable(null, true, false, "", List.of("","Lessons"), clmsToCheck,recordedLessonsList, 700) );
        inputWindow.setMakeRecordedLessonTable(false);
    }
    
    private void setClmsToCheck ( List<Integer> clmsToCheck ) {
        clmsToCheck.add(1);
    }
    
    private void setRecordedLessonsList (List<List<String>> recordedLessonsList, List<String> recordedLessonsSet) {
        recordedLessonsSet.forEach((String e) -> {
            recordedLessonsList.add(List.of(e));
        });        
    }
    
    private void setTableDisplayForRecordedLesson (String title, JTable table, JButton[] btns, TableDisplay tableDisplay) 
    {
        JScrollPane pane = tableDisplay.getScrollPaneContainer();
        JPanel southPanel = tableDisplay.getSouthPanel();
        JLabel titleLabel = tableDisplay.getTitleLabel();
        
        pane.setViewportView(table);
        for (JButton btn : btns) {
            southPanel.add(btn);
        }
        titleLabel.setText(title);
    }
    
    private void setRecordedLessonsTableBtnsListeners (JButton[] tableDisplayBtns, InputWindow inputWindow) 
    {
        Table table = inputWindow.getRecordedLessonsTable();
        
        tableDisplayBtns[0].addActionListener( (ActionEvent e) -> {
            table.setTableCheckedRows(true);
        });
        
        tableDisplayBtns[1].addActionListener( (ActionEvent e) -> {
             table.setTableCheckedRows(false);
        });
        
        tableDisplayBtns[2].addActionListener( (ActionEvent e) -> {
            appendTextAreaContent(inputWindow,  inputWindow.getRecordedLessonsTable());
        });

        tableDisplayBtns[3].addActionListener( (ActionEvent e) -> 
        {
            if (!areRowsSelected(table)) 
                return;           
            
            removeTextAreaContent(inputWindow,  inputWindow.getRecordedLessonsTable());
        });        
    }

    
    @Override
    public void removeTextAreaContent (Table table, Set<Integer> selectedRows, Set<String> textAreaContent) 
    {
        final int clm = 1;
        JTable jTable = table.getjTable();
        Iterator<Integer> iterator = selectedRows.stream().toList().iterator();
        int i = 0;
        
        while(iterator.hasNext()) 
        {
            int row = iterator.next();
            String lesson = (String) jTable.getValueAt(row, clm);
            
            if (textAreaContent.contains(lesson)) 
                textAreaContent.remove(lesson);
            i ++;
        }        
    }      
}
