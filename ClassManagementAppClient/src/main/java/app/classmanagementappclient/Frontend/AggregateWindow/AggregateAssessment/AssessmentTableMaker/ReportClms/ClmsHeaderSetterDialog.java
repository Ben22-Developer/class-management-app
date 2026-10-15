package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.ReportClms;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.ReportClms.EventHandlers.AddLessonHandler;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ReportStructureTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author BEN
 */
public class ClmsHeaderSetterDialog {
    
    private final ReportClmsHandler reportClmsHandler;
    private final MyJDialog dialog = new MyJDialog("Edit Report Columns Names");
    private final List<JTextField> textFields = new ArrayList<>();;
    private final int dialogWidth = 500;

    public ClmsHeaderSetterDialog(ReportClmsHandler reportClmsHandler) {
        this.reportClmsHandler = reportClmsHandler;
    }   
    
    public void run () {
        
        if (reportClmsHandler.getTable() == null) 
            if(!tableInit())
                return;
        
        showDialog();
    } 
    
    private boolean tableInit () {
        int resInt = getClmsNbr();
        if (resInt == Integer.MIN_VALUE)
            return false;
        addTableToContainer(resInt);
        return true;
    }
    
    private void addTableToContainer (int resInt) {
        
        JPanel containerPanel = getTableContainerPanel();
        reportClmsHandler.setTable( ReportStructureTable.getReportStructureTable(reportClmsHandler.getAggregateWindow(), resInt) );
        containerPanel.add(new JScrollPane(reportClmsHandler.getTable().getjTable()), BorderLayout.CENTER);
        containerPanel.add(getComponetsPanel(), BorderLayout.SOUTH);
        FrontEndUtility.componentValidate(containerPanel);
    }
    
    private int getClmsNbr () {
        
        String res = FrontEndUtility.getUserInput("Enter the number of columns of the report", "Set Report Columns");
        
        if (res == null || res.isBlank())
            return Integer.MIN_VALUE;
        
        int resInt;
        try {
            resInt = Integer.parseInt(res);
            if (resInt == 0)
                throw new RuntimeException();
        }
        catch (Exception e) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Only numbers greater than 0 are allowed!", "Error Message", JOptionPane.ERROR_MESSAGE);
            resInt = Integer.MIN_VALUE;
        }
        return resInt;
    }
    
    private JPanel getTableContainerPanel () {
        
        JPanel topPanel = (JPanel) reportClmsHandler.getAggregateWindow().getSplitPane().getTopComponent();
        for (Component component : topPanel.getComponents()) 
            if (component instanceof JPanel panel)
                return panel;
        return null;
    }
    
    private JPanel getComponetsPanel () {
        
        JButton selectAll = new JButton("Select All");  
        JButton selectNone = new JButton("Select None");  
        JButton removeSelected = new JButton ("Remove Selected");
        JButton addLesson = new JButton("Add Lesson");
        JButton restartAll = new JButton("Restart");  
        
        addLessonListener(addLesson);
        removeSelectedListener(removeSelected);
        restartAllSelectedListener(restartAll);
        
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { selectAll,selectNone, removeSelected, restartAll, addLesson });
        Table.setCommonEventHandlers(new JButton[] { selectAll,selectNone }, reportClmsHandler.getTable());
        
        return addBtnToPanel( new JComponent[] { selectAll,selectNone, removeSelected, restartAll, addLesson } );
    }  
    
    private void addLessonListener (JButton addLesson) {
        
        addLesson.addActionListener(l -> {
            new AddLessonHandler(reportClmsHandler.getAggregateWindow()).run();
        });        
    }
    
    private void removeSelectedListener (JButton removeSelected) {
        removeSelected.addActionListener(l -> {
            SwingUtilities.invokeLater(() -> {
                removeSelectedHandler();
            });
        });          
    }
    
    private void addBackDataToTermAssessmentTable (List<Integer> checkedRowList) {
        
        AggregateWindow aggregateWindow = reportClmsHandler.getAggregateWindow();
        Table table = reportClmsHandler.getTable();
        
        JTable jt = aggregateWindow.getTermAssessmentsTable();
        MyDefaultTableModel termAssessmentModel = (MyDefaultTableModel) jt.getModel();
        
        for (int checkedRow : checkedRowList) {
            
            List<Table> tables = aggregateWindow.getReportStructureRow(checkedRow); // Column Components ('Titled') Tables in a row
            
            String lessonName = table.getRowDataAt(checkedRow).get(0);
            
            for (Table table1 : tables) { //1 table of the Column Components ('Titled') Tables 
                
                for (int i = 0; i < table1.getjTable().getRowCount(); i++) { // looping in 1 table rows
                    
                    List<String> rowData = table1.getRowDataAt(i); // getting term, ass name, ass date, tot marks
                    
                    int rowNbr = Integer.parseInt((String)termAssessmentModel.getValueAt(termAssessmentModel.getRowCount()-1 ,0)) + 1;

                    Vector<String> assessmentTableRow = new Vector<>();
                    
                    assessmentTableRow.add(String.valueOf(rowNbr));
                    
                    for (int j = 0; j < rowData.size(); j++) {
                        
                        if (j == 1)
                            assessmentTableRow.add(lessonName);
                        
                        assessmentTableRow.add(rowData.get(j));
                    }
                    assessmentTableRow.add("Set");
                    termAssessmentModel.addRow(assessmentTableRow);
                }
            }
        }
    }
    
    private void restartAllSelectedListener (JButton restartAll) {
        restartAll.addActionListener(l -> {
            removeReportStructureTableRows();
            removeTablePanel();
        });          
    } 
    
    private void removeReportStructureTableRows () {
        
        if (reportClmsHandler.getTable().getDataInRows().isEmpty())
            return;
        
        autoSelectJTableRows();
        removeSelectedHandler();
    }
    
    private void removeTablePanel() {
        JPanel tablePanel = getTableContainerPanel();
        BorderLayout layout = (BorderLayout) tablePanel.getLayout();
        tablePanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        tablePanel.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
        FrontEndUtility.componentValidate(tablePanel);
        reportClmsHandler.setTable(null);
    }
    
    private void removeSelectedHandler () {
        
        AggregateWindow aggregateWindow = reportClmsHandler.getAggregateWindow();
        Table table = reportClmsHandler.getTable();        
        
        addBackDataToTermAssessmentTable(table.getSortedCheckedRowsList());
        aggregateWindow.removeReportStructureRow(table.getSortedCheckedRowsList());
        table.removeAllTableCheckedRows();
    }
    
    private void autoSelectJTableRows () {
        
        JTable jt = reportClmsHandler.getTable().getjTable();
        for (int i = 0; i < jt.getRowCount(); i++)
            jt.setValueAt(true, i, 0);
    }

    private JPanel addBtnToPanel (JComponent[] components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        FrontEndUtility.addJComponentToPanel(panel, components);
        return panel;
    } 
    
    private void showDialog () {
        
        dialog.add(getNorthPanel(), BorderLayout.NORTH);
        dialog.add(getCenterPanel(), BorderLayout.CENTER);
        dialog.add(getSouthPanel(), BorderLayout.SOUTH);
        setDialogProperties();
    }
    
    private JPanel getNorthPanel () {
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Edit Column Headers");
        FrontEndUtility.setJComponentCustomFont(label, new Font("Arial", Font.BOLD, 17));
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { label });
        return panel;
    }
    
    private JScrollPane getCenterPanel () {
        
        ReportStructureTable table = reportClmsHandler.getTable();   
        JPanel mainPanel = new JPanel (new FlowLayout(FlowLayout.LEADING, 0,10));
        
        int i = table.getOtherHelperClms();
        int clmCount = table.getjTable().getColumnCount();
        int height = 0;
        
        while (i < clmCount) {
            
            JTextField textField = new JTextField((String) table.getjTable().getTableHeader().getColumnModel().getColumn(i).getHeaderValue());
            textField.setPreferredSize(new Dimension(450, 40));
            textFields.add(textField);
            mainPanel.add(textField);
            height += 50;// 50 as for 40 (textField height) + 10 (FlowLayout.Vertical_Space)
            i += 1;
            textField.addActionListener(l -> {
                clmHeadersSubmitListener();
            });
        }
        mainPanel.setPreferredSize(new Dimension(dialogWidth, height));
        
        var pane = new JScrollPane(mainPanel);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return pane;
    }
    
   private JPanel getSouthPanel () {
        JPanel panel = new JPanel();
        JButton button = new JButton("Submit");
        button.addActionListener(l -> {
            clmHeadersSubmitListener();
        });
        FrontEndUtility.setJComponentCustomFont(button, new Font("Arial", Font.PLAIN, 16));
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { button });
        return panel;
   }
   
   private void clmHeadersSubmitListener () {
       
       ReportStructureTable table = reportClmsHandler.getTable();   
       JTable jt = table.getjTable();
       
       new SwingWorker<Object, List<Object>> () {
           @Override
           protected Object doInBackground() throws Exception {
               
               int clmIndex = table.getOtherHelperClms();
               int i = 0;
               int totalClms = jt.getColumnCount();               
               
                while (clmIndex < totalClms) {
                    
                    String textFieldValue = textFields.get(i).getText();
                    
                    if (textFieldValue.isBlank()) {
                        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("A column header must not be empty!", "Error Message", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    
                    if (!jt.getColumnName(clmIndex).equals(textFieldValue))
                        publish(List.of(clmIndex, textFields.get(i)));
                    
                    clmIndex += 1;
                    i += 1;
                }               
               
                dialog.dispose();
               
               return null;
           }

           @Override
           protected void process(List<List<Object>> chunks) {
               
               for (List<Object> chunk : chunks) {
                   
                   int clmIndex = (int) chunk.get(0);
                   JTextField textField = (JTextField) chunk.get(1);
                   JTableHeader tableHeader = jt.getTableHeader();
                   tableHeader.getColumnModel().getColumn(clmIndex).setHeaderValue(textField.getText());
                   FrontEndUtility.componentValidate(tableHeader);
               }
           }
       }.execute();
   }
   
   private void setDialogProperties () {
       dialog.setSize(dialogWidth, 300);
       dialog.setLocation(500, 150);
       dialog.setVisible(true);
   }
}
