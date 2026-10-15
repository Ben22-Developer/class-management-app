package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TermAssessmentsTableDialog extends TableEditDialog {
    
    private final AggregateWindow aggregateWindow;
    private final int invalidNum = -1;

    public TermAssessmentsTableDialog(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }

    @Override
    public void drawJDialog() {
        
        var thisObj = this;
        
        new SwingWorker<Object, Table> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                JTable reportStructureTable = aggregateWindow.getReportStructureTable();
                
                if (reportStructureTable == null) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("You must first create Report Table Structure", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
                
                String lessonName = (String) thisObj.getTable().getRowDataAt(thisObj.getRow()).get(1);
                
                int lessonRow = getLessonRowAtReportTableStructure(reportStructureTable, lessonName);
                
                if (lessonRow == invalidNum) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Please register this lesson in Report Table Structure!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
                getUserClm(lessonRow, thisObj);
                return null;
            }
        }.execute();
    }
    
    private void handlerSubmit (int lessonRow, int lessonClm, TermAssessmentsTableDialog dialog) {
        
        new SwingWorker<Object, Table> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                addToStudentReportStructure(lessonRow, lessonClm);
                publish(dialog.getTable());
                return null;
            }

            @Override
            protected void process(List<Table> chunks) {
                Table table = chunks.get(0);
                table.removeTableRow(table.getTableRowSorter().convertRowIndexToModel(dialog.getRow()));
            }
            
        }.execute();
        
    }
    
    private int getLessonRowAtReportTableStructure (JTable reportStructureTable, String lessonName) {
        
        for (int i = 0; i < reportStructureTable.getRowCount(); i++) {
            String lessonName1 = (String) reportStructureTable.getValueAt(i, 1);
            if (lessonName.equals(lessonName1)) 
                return i;
        }
        return invalidNum;
    }
    
    private void getUserClm (int row,TermAssessmentsTableDialog thisObj) {
        
        try {
        
            MyJDialog dialog = new MyJDialog("Insert column of the report structure table");

            JLabel txtLabel = new JLabel("<html><p>Details: " + thisObj.getSelectedRowData() + "<br>Row: " + (row + 1) +"<br>Select Column: <br></p></html>");
            FrontEndUtility.setJComponentDefaultFont(txtLabel);
            
            TableModel model = aggregateWindow.getReportStructureTable().getModel();

            
            JTableHeader tableHeader = aggregateWindow.getReportStructureTable().getTableHeader();
            
            
            
            ButtonGroup buttonGroup = new ButtonGroup();

            for (int i = 2; i < model.getColumnCount(); i++) {

                JRadioButton radioButton = new JRadioButton((String) tableHeader.getColumnModel().getColumn(i).getHeaderValue());
                FrontEndUtility.setJComponentDefaultFont(radioButton);
                
                buttonGroup.add(radioButton);
                
                int index = i - 2;
                
                radioButton.addActionListener(l -> {
                    handlerSubmit(row, index, thisObj);
                    dialog.dispose();
                });
            }
            
            dialog.add(getPanel(txtLabel), BorderLayout.NORTH);
            dialog.add(getBtnsPane(buttonGroup), BorderLayout.CENTER);
            
            dialog.setLocation(400, 100);
            
            dialog.setSize(new Dimension(500, 300));
            dialog.setVisible(true);
            
        }
        catch (Exception e) {
            throw new RuntimeException("some rubbish " + e);
        }
    }
    
    private JPanel getPanel (JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        panel.add(component);
        return panel;
    }
    
    private JScrollPane getBtnsPane (ButtonGroup buttonGroup) {
        JPanel panel = new JPanel();
        List<AbstractButton> buttonList = Collections.list( buttonGroup.getElements() );
        panel.setLayout(new GridLayout(buttonList.size(), 1, 0, 0));
        buttonList.forEach(panel::add);
        panel.setPreferredSize(new Dimension(400, 200));
        var pane = new JScrollPane(panel);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return pane;
    }
    
    private void addToStudentReportStructure (int row, int clm) {
        
        List<String> rowData = super.getTable().getRowDataAt(super.getRow());
        List<Object> rowDataObj = new ArrayList<>();
        rowDataObj.add(false);
        
        int i = 0;
        
        for (String data : rowData) {
            
            i += 1;
            
            if (i == 2)
                continue;
            
            rowDataObj.add(data);
        }
//        System.out.println(rowData);
//        System.out.println(rowDataObj);
//        System.out.println("row: " + row + "\tclm: " + clm);
        aggregateWindow.insertDataInReportStructureTable(row, clm, rowDataObj);
    }
    
}
