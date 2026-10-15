package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.ReportClms.EventHandlers;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class AddLessonHandler {

    
    private final AggregateWindow aggregateWindow;
    private final TableDisplay tableDisplay = new TableDisplay();

    public AddLessonHandler(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    public void run () {
        
        JTable jt = aggregateWindow.getTermAssessmentsTable();
        List<List<String>> lessonsList = getLessonsList(jt);
        setTable(lessonsList);
    }
    
    private List<List<String>> getLessonsList (JTable jTable) {
        
        Set<String> lessonsSet = new HashSet<>();
        List<List<String>> lessonList = new ArrayList();
        setDefinedLessons(lessonsSet);
        
        for (int i = 0; i < jTable.getRowCount(); i++) {
            
            String lessonName = (String) jTable.getValueAt(i,2);
            
            if (!lessonsSet.contains(lessonName))
                lessonList.add(List.of(lessonName));
            
            lessonsSet.add(lessonName);
        }
        return lessonList;
    }
    
    private void setDefinedLessons (Set<String> lessonsSet) {
        
        JTable jt = aggregateWindow.getReportStructureTable();
        for (int i = 0; i < jt.getRowCount(); i++)
            lessonsSet.add((String) jt.getValueAt(i, 1));
        
    }
    
    private void setTable (List<List<String>> lessonList) {
        
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("", "Lesson Names"), List.of(1),lessonList);
        tableDisplay.getTitleLabel().setText("Lessons");
        tableDisplay.getScrollPaneContainer().setViewportView(table.getjTable());
        FrontEndUtility.addJComponentToPanel(tableDisplay.getSouthPanel(), getSouthComponents(table));
        tableDisplay.setCustomTableDisplayProperties(400, 90, new Dimension(700, 500));
        tableDisplay.useJDialog(table);
    }
    
    private JComponent[] getSouthComponents (Table table) {
        
        JButton selectAll = new JButton("Select All");
        JButton selectNone = new JButton("Select None");
        JButton removeSelected = new JButton("Remove Selected");
        JButton submit = new JButton("Submit");
        
        Table.setCommonEventHandlers(new JButton[] { selectAll, selectNone }, table);
        removeSelectedListener(table, removeSelected);
        submitListener(table, submit);
        
        JComponent[] components = { selectAll, selectNone, removeSelected, submit };
        FrontEndUtility.setJComponentsDefaultFont(components);
        
        JPanel panel = new JPanel();
        FrontEndUtility.addJComponentToPanel(panel, components);
        return components;
    }
    
    private void removeSelectedListener (Table table, JButton removeSelected) {
        removeSelected.addActionListener(l -> {
            table.removeAllTableCheckedRows();
        });
    }
    
    private void submitListener (Table table, JButton submit) {
        submit.addActionListener(l -> {
            submitHandler(table);
        });
    }
    
    private void submitHandler (Table table) {
        
        if (table.getCheckedRows().isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("No lesson selected", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JTable jt = aggregateWindow.getReportStructureTable();
        addCheckedLessons(jt, table.getCheckedRowsData());
    }
    
    private void addCheckedLessons (JTable jt, List<List<String>> lessons) {
        
        new SwingWorker<Object, String> () {
            
            private int startingIndex = 2;
            
            @Override
            protected Object doInBackground() throws Exception {
                
                for (List<String> lesson : lessons)
                    publish(lesson.get(0));
                
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                
                for (String chunk : chunks) {
                    
                    int rowIndex = createReportStructureRow();
                    List<Object> row = new ArrayList<>();
                    row.add(Boolean.FALSE);
                    row.add(chunk);
                    
                    for (int i = startingIndex; i < jt.getColumnCount(); i++) {
                        row.add("View");
                        populateReportStructureRow(rowIndex);
                    }
                    
                    MyDefaultTableModel model = (MyDefaultTableModel) jt.getModel();
                    model.addRow(row.toArray());
                }
            }

            @Override
            protected void done() {
                FrontEndUtility.componentValidate(jt);
                tableDisplay.getDialog().dispose();
            }
        }.execute();
    }
    
    private int createReportStructureRow () {
        int row = aggregateWindow.getStudentReportStructure().size();
        aggregateWindow.createStudentReportRow();
        return row;
    }
    
    private void populateReportStructureRow (int row) {
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("","Term", "Assessment Name", "Assessment Date", "Total Marks"), List.of(1,2,3,4), new ArrayList<>(), 300);
        aggregateWindow.populateTablesInRows(table, row);
    } 
}
