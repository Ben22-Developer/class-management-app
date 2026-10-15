package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.DisplayStudentClasses;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.SavedStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class DisplayStudentClasses extends DataDisplay{

    public DisplayStudentClasses(Table table) {
        super(table);
    }

    @Override
    public void setStudentIDs() {
        var savedStudentsTable = (SavedStudentsTable) super.getTable();
        super.setStudentDetails(savedStudentsTable.getOnViewCheckedRowsData());
    }

    @Override
    public void collectData() {
        for (List<String> details: super.getStudentDetails()) {
            String studentID = details.get(0);
            List<List<String>> classes = new StudentClassMedium().getStudentClasses(studentID);
            Table table = Table.getTable(EditableTableType.IsJustViewTable, false, false, "", List.of("Num", "Schools", "Classes", "Years", "Terms"), null, classes, 300);
            super.getClassTables().add(table);
        }
    }

    @Override
    public void populateDataToUI() {
        
        TableDisplay tableDisplay = new TableDisplay();
        JButton back = new JButton("Back");
        JButton next = new JButton("Next");
        
        addNextListener(next, tableDisplay);
        addBackListener(back, tableDisplay);
        setTableDisplay(tableDisplay, next, back);
    }
    
    private void addNextListener ( JButton next, TableDisplay tableDisplay ) {
        
        next.addActionListener(l -> {
            
            if (super.getCurrIndex() >= super.getStudentDetails().size() - 1)
                return;
            

            super.setCurrIndex(super.getCurrIndex() + 1);
            toDisplayController(tableDisplay, super.getStudentDetails().get(super.getCurrIndex()).toString(), super.getClassTables().get(super.getCurrIndex()));
        });
    }

    private void addBackListener ( JButton back, TableDisplay tableDisplay ) {
        
        back.addActionListener(l -> {
            
            if (super.getCurrIndex() <= 0)
                return;
            
            
            super.setCurrIndex(super.getCurrIndex() - 1);
            toDisplayController(tableDisplay, super.getStudentDetails().get(super.getCurrIndex()).toString(), super.getClassTables().get(super.getCurrIndex()));
        });
    }
    
    private void toDisplayController (TableDisplay tableDisplay , String titleTxt, Table table) {
        
        SwingUtilities.invokeLater(() -> 
        {
            tableDisplay.getTitleLabel().setText(titleTxt);
            var container = tableDisplay.getScrollPaneContainer();
            cleanScrollPane(container);
            container.setViewportView(table.getjTable());
        });
    }
    
    private void cleanScrollPane (JScrollPane container) {

        for(Component component : container.getComponents()) {
            if (component instanceof JTable) {
                container.remove(component);
                break;
            }
        }
        container.revalidate();
        container.repaint();        
    }
    
    private void setTableDisplay (TableDisplay tableDisplay, JButton next, JButton back) {
        
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { back, next });
        
        toDisplayController(tableDisplay, super.getStudentDetails().get(super.getCurrIndex()).toString(), super.getClassTables().get(super.getCurrIndex()));
        
        FrontEndUtility.addJComponentToPanel(tableDisplay.getSouthPanel(), new JComponent[] { back, next });
        
        tableDisplay.setSize(new Dimension(500,400));
                
        tableDisplay.useJDialog(super.getClassTables().get(super.getCurrIndex()), true);
    }
}
