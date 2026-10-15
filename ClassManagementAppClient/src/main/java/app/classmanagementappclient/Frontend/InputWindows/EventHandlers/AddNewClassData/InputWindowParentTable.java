package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author BEN
 */
abstract public class InputWindowParentTable
{
    protected void appendTextAreaContent (InputWindow inputWindow, Table table) 
    {
        Runnable runnable = () -> {
            JTextArea lessonsTextArea = inputWindow.getAddedLessonsTextArea();
            Set<Integer> checkedRows = table.getCheckedRows();
            JTable jTable = table.getjTable();
            Set<String> textAreaContent = inputWindow.getTextAreaContent();
            
            if (checkedRows.isEmpty())
                return;
            
            addTableSelectedContentToSet(jTable, checkedRows, textAreaContent);
            setTextAreaContent(lessonsTextArea, textAreaContent);
        };
        runExecutorService(runnable);
    }  
    
    private void addTableSelectedContentToSet (JTable jTable, Set<Integer> selectedRows,Set<String> textAreaContent)
    {
        final int clm = 1;
        
        Iterator<Integer> iterator = selectedRows.iterator();
        
        while(iterator.hasNext()) {
            int row = iterator.next();
            String lesson = (String) jTable.getValueAt(row, clm);
            textAreaContent.add(lesson);
        }
    }
    
    protected void removeTextAreaContent (InputWindow inputWindow, Table table) 
    {
        Runnable runnable = () -> 
        {
            Set<String> textAreaContent = inputWindow.getTextAreaContent();
            Set<Integer> checkedRows = table.getCheckedRows();
                        
            if (checkedRows.isEmpty() || textAreaContent.isEmpty()) 
                return;
           
            removeTextAreaContent (table, checkedRows, textAreaContent);
            setTextAreaContent(inputWindow.getAddedLessonsTextArea(), textAreaContent);
            table.removeAllTableCheckedRows();
        };
        runExecutorService(runnable);
    }  
    
    protected void updateTextAreaContent (String newValue, String oldValue, InputWindow inputWindow) 
    {
        Set<String> textAreaContent = inputWindow.getTextAreaContent();
        textAreaContent.remove(oldValue);
        textAreaContent.add(newValue);
        setTextAreaContent (inputWindow.getAddedLessonsTextArea(), textAreaContent);
    }
    
    private void setTextAreaContent (JTextArea lessonsTextArea, Set<String> textAreaContent) {
        String toDisplay = textAreaContent.isEmpty() ? "" : textAreaContent.toString();
        lessonsTextArea.setText(toDisplay);
    }    
    
    protected boolean areRowsSelected (Table table) 
    {
        boolean areChecked = !table.getCheckedRows().isEmpty();
        
        if (!areChecked)
            FrontEndUtility.showMsgJOptionPane("There's no selected row!", "Infromation Message", JOptionPane.INFORMATION_MESSAGE);
        
        return areChecked;
    }    
    
    protected void setTableDisplayTextFieldProp (JTextField textField, InputWindow inputWindow) 
    {
        setTableDisplayComponentsProperties(new JComponent[] { textField }, new Dimension(200,40));
        String txtFieldContent = inputWindow.getNewLessonTxtFieldContent();
        if (!txtFieldContent.isBlank()) {
            textField.setText(txtFieldContent);
        }
    }
    
    protected void setTableDisplayComponentsProperties (JComponent[] components, Dimension dimension) {
        FrontEndUtility.setJComponentsDefaultFont(components);
        FrontEndUtility.setJComponentsDimension(components, dimension);
    }
    
    
    protected void addTableDisplayEventListener (JButton toDisableBtn, TableDisplay tableDisplay) 
    {
        toDisableBtn.setEnabled(false);
        tableDisplay.addWindowListener(new WindowAdapter () {
            @Override
            public void windowClosed (WindowEvent e) {
                toDisableBtn.setEnabled(true);
            }
        });
    }  
    

    
    protected void addCustomTableDisplayProperties (TableDisplay tableDisplay, InputWindow inputWindow) 
    {
        tableDisplay.setCustomTableDisplayProperties(250, 60, new Dimension(900,480));
        tableDisplay.useJDialog(inputWindow.getNewLessonsTable());
    }        
    
    
    private void runExecutorService (Runnable runnable) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.execute(runnable);
        es.shutdown();
    }  
    
    public abstract void removeTextAreaContent (Table table, Set<Integer> checkedRows, Set<String > textAreaContent);
}
