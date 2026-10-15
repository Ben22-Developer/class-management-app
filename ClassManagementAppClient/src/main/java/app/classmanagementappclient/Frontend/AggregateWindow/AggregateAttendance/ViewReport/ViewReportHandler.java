package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.ViewReport;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Medium.AttendanceRecord.Read.ReadRecordedAttendanceMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceReportsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class ViewReportHandler {
    
   private final AggregateWindow window;
   
   private final String text = "Search...........";
   private final JButton selectAllBtn = new JButton("Select All");
   private final JButton selectNoneBtn =  new JButton("Select None");
   private final JButton removeSelectedBtn =  new JButton("Remove Selected");
   private final JButton reportBtn = new JButton("Report");
   private final JTextField searchField = new JTextField(text);
   private final TableDisplay tableDisplay = new TableDisplay();
   
    public ViewReportHandler(AggregateWindow window) {
        this.window = window;
    }

    public void run() {
        
        List<Integer> classTerms = window.getAttendanceClassTermNums();
        Set<Integer> attendanceNums = window.getAttendanceNums();
        
        if (classTerms == null || attendanceNums.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Can't generate report due to:\n1.You haven't selected class (School, class name, academic year and term(s))\n2.Even though you've selected the class, you haven't selected a class(es) lesson attendance", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        System.out.println(classTerms);
        System.out.println("-->  " + attendanceNums.stream().toList());
        
        var attendanceNumsList = attendanceNums.stream().toList();
        final StringBuilder report = new StringBuilder();
        
        List<List<String>> data = new ReadRecordedAttendanceMedium().getAggregateAttendanceReport( attendanceNumsList,classTerms);
        
        if (data.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Sorry this class has no student registered in it!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        List<List<String>> attendanceDetails = new AttendanceMedium().getStringsAttendanceDetails (attendanceNumsList);
        constructReport(report, attendanceDetails);
        List<String> headers = getClmHeaders(data.get(0).size());
        Table table = getAttendanceReportsTable(data, headers);
        TableDisplay display = new TableDisplay();
        setComponentsProp(); 
        addEvents(table, report);
        setNorthTitle(display);
        setCenterPanel(display, table);
        setSouthPanel(display);
        setTableDisplayProp(display, table);
        
//        data.forEach(System.out::println);
//        System.out.println("\n\n");
//        System.out.println(report);
    }
    
    private void constructReport (StringBuilder report, List<List<String>> attendanceDetails) {
        
        String string = "Column ";
        int i = 1;
        
        for (List<String> attendanceDetail : attendanceDetails) {
            report.append(string).append(i).append(" ---> ").append(attendanceDetail).append("\n\n");
            i += 1;
        }
    }
    
    private List<String> getClmHeaders (int size) {
        
        List<String> headers = new ArrayList<>();
        
        headers.add("");
        headers.add("No");
        headers.add("Student ID");
        headers.add("Student Name");
        
        int normalClmsSize = size - 3;
        
        for (int i = 0; i < normalClmsSize - 1; i++)
            headers.add("Column " + (i+1));
        
        headers.add("Total (%)");
        
        return headers;
    }
    
    private AttendanceReportsTable getAttendanceReportsTable (List<List<String>> data, List<String> headers) {
        return AttendanceReportsTable.getAttendanceReportsTable(data, headers, 400);
    }
    
    private void setComponentsProp () {
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { selectAllBtn, selectNoneBtn, removeSelectedBtn, reportBtn, searchField });
    }
    
    private void addEvents (Table table, StringBuilder report) {
        
        Table.setCommonEventHandlers(new JButton[]{ selectAllBtn,selectNoneBtn }, table);
        
        removeSelectedBtn.addActionListener(l -> {
            table.removeAllTableCheckedRows();
        });
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(text))
                    searchField.setText("");
            }
        });
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (table instanceof SearchableTable searchableTable)
                    searchableTable.searchInTable(searchField.getText(), table);
            }
        });
        
        reportBtn.addActionListener(l -> {
            new ShowReport().showReport(report.toString(), "", true);
        });
    }
    
    private void setNorthTitle (TableDisplay display) {
        display.getTitleLabel().setText("Aggregate Attendance Report");
    }
    
    private void setCenterPanel (TableDisplay display, Table table) {
        display.getScrollPaneContainer().setViewportView(table.getjTable());
    }    
    
    private void setSouthPanel (TableDisplay display) {
        JComponent[] components = { selectAllBtn, selectNoneBtn, removeSelectedBtn, reportBtn, searchField };
        FrontEndUtility.addJComponentToPanel(display.getSouthPanel(), components);
    }

    private void setTableDisplayProp(TableDisplay display,  Table table) {
        display.setCustomTableDisplayProperties(100, 60, new Dimension(1000,600));
        display.useJDialog(table, true);
    }
}
