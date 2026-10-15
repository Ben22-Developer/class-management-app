package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.StudentInsertParent;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
abstract public class TableDisplayHelperClass extends StudentInsertParent
{
    
    private final JButton selectAllBtn = new JButton("Select All");
    private final JButton selectNoneBtn =  new JButton("Select None");
    private final JButton removeSelectedBtn =  new JButton("Remove Selected");
    private final JButton submitBtn =  new JButton("Submit");
    private final TableDisplay tableDisplay = new TableDisplay();
    
    private final Table table;

    public TableDisplayHelperClass(Table table, InputWindow inputWindow) {
        super(inputWindow);
        this.table = table;
    }
    
    public void run () {
        
        setGUIComponentsProp();
        setEventListeners();
        setTableDisplay();
    }
    
    protected void setEventListeners () 
    {
        Table.setCommonEventHandlers(new JButton[] { selectAllBtn, selectNoneBtn }, table);
        
        removeSelectedBtn.addActionListener((l) -> {
            table.removeAllTableCheckedRows();
        });
        
        submitBtn.addActionListener((l) -> {
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    enableComponents(getComponents(), false);
                    submitData();
                    return null;
                }
                
                @Override
                public void done () {
                    enableComponents(getComponents(), true);
                }
            }.execute();
            
        });
    }
    
    private void setTableDisplay () {
        
        setTableDisplayTitle();
        setTableDisplayCenterPanel();
        setTableDisplaySouthPanel();
        finalTableDisplaySetup();
    }
    
    protected void setGUIComponentsProp () 
    {
        FrontEndUtility.setJComponentDimension(submitBtn, new Dimension(80, 40));
        FrontEndUtility.setJComponentDimension(selectAllBtn, new Dimension(100, 40));
        FrontEndUtility.setJComponentDimension(selectNoneBtn, new Dimension(120, 40));
        FrontEndUtility.setJComponentDimension(removeSelectedBtn, new Dimension(160, 40));
        FrontEndUtility.setJComponentsDefaultFont(new JButton[] { selectAllBtn, selectNoneBtn, removeSelectedBtn,  submitBtn });
    }
    
    public void setTableDisplayTitle () {
        super.setTableDisplayTitle(tableDisplay);
    }
    
    private void setTableDisplayCenterPanel () {
        tableDisplay.getScrollPaneContainer().setViewportView(table.getjTable());
    }
    
    
    public void setTableDisplaySouthPanel() {
        FrontEndUtility.addJComponentToPanel(tableDisplay.getSouthPanel(), getComponents());
    }
    
    private void finalTableDisplaySetup () {
        tableDisplay.setCustomTableDisplayProperties(150, 60, new Dimension(1000,480));
        tableDisplay.useJDialog(table);
        tableDisplay.getDialog().setTitle("Students Input");
    }
    
    protected boolean isOnViewCheckedRowsEmpty () {
        
        var list = table.getOnViewCheckedRowsData();
        
        if (list.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no in view checked rows!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
        

    public JButton getSelectAllBtn() {
        return selectAllBtn;
    }

    public JButton getSelectNoneBtn() {
        return selectNoneBtn;
    }

    public JButton getRemoveSelectedBtn() {
        return removeSelectedBtn;
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public TableDisplay getTableDisplay() {
        return tableDisplay;
    }

    public Table getTable() {
        return table;
    }
    
    public void enableComponents (JComponent[] components, boolean enable) {
        for (JComponent comp : components)
            comp.setEnabled(enable);
    }
    
    public JComponent[] getComponents() {
        return new JComponent[] { selectAllBtn,selectNoneBtn,removeSelectedBtn,submitBtn };
    }
    
    public abstract void submitData();
}
