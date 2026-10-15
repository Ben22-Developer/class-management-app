package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AggregateClassLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class ViewAllClassAttendanceDialog extends ViewTables {

    private final List<Integer> attendanceNums = new ArrayList<>();
    
    @Override
    protected int getIndentifier() {
        AggregateClassLessonsTable table = (AggregateClassLessonsTable) super.getTable();
        return table.getLessonNums().get(super.getRow());
    }    
    
    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Unexpected Error!\nContact the developer if it persists!", "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public List<List<String>> getData(int identifier) {
        attendanceNums.clear();
        List<List<String>> data = new AttendanceMedium().getStringsAttendanceDetails(identifier);
        data.forEach(e -> {
            attendanceNums.add( Integer.parseInt(e.removeFirst()) );
            e.set(0, FrontEndUtility.getAppFormatDate( java.sql.Date.valueOf(e.get(0)) ));
            e.set(1, FrontEndUtility.getRecordedAtTimeInAppFormat( LocalDateTime.parse( getLocalDateTimeT(e.get(1)) ) ));
        });
        
        var table = (AggregateClassLessonsTable) super.getTable();
        setTempAttendanceNum(table.getAggregateWindow());
        return data;
    }
    
    private String getLocalDateTimeT (String string) {
        String split[] = string.split(" ");
        return split[0] + "T" + split[1];
    }
    
    private void setTempAttendanceNum (AggregateWindow window) {
        
        Set<Integer> tempAttendanceNums = window.getTempAttendanceNums();
        Set<Integer> attendanceNums = window.getAttendanceNums();
        
        if (attendanceNums.isEmpty())
            return;
        
        for (int attendanceNum : this.attendanceNums) {
            if (attendanceNums.contains(attendanceNum))
                tempAttendanceNums.add(attendanceNum);
        }
    }

    @Override
    public void display(List<List<String>> data) {
        
        TableDisplay display = new TableDisplay();
        
        JButton selectAll = new JButton("Select All");
        JButton sellectNone = new JButton("Select None");
        
        Table attendanceTable = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("", "Attendance Date", "Recorded At"), List.of(1,2), data, 200);
        
        JButton removeSelected = super.getRemoveSelectedBtn(attendanceTable);
        JButton undoSubmit = undoSubmit(display, (AggregateClassLessonsTable) super.getTable(), attendanceTable);
        JButton submitBtn = getSubmitBtn(display, (AggregateClassLessonsTable) super.getTable(), attendanceTable);
        
        super.commonPropertiesSetup(new JButton[] { selectAll,sellectNone }, new JComponent[] { selectAll,sellectNone,removeSelected, undoSubmit, submitBtn }, display, attendanceTable, "Lesson Attendances");
        setRowsSelected(attendanceTable, (AggregateClassLessonsTable) super.getTable());
    }
    
    private void setRowsSelected (Table attendanceTable, AggregateClassLessonsTable table) {
        
        var window = table.getAggregateWindow();
        
//        System.out.println("\n"+window.getTempAttendanceNums().stream().toList() +"\t" + attendanceNums);
        SwingUtilities.invokeLater(() -> {
            int i = 0;
            for (int num : attendanceNums) {
                if (window.getTempAttendanceNums().contains(num)) {
//                    System.out.println(i + ", 0");
                    attendanceTable.getjTable().setValueAt(true, i, 0);
                }
                i += 1;
            }        
        });
    }
    
    private JButton getSubmitBtn (TableDisplay display, AggregateClassLessonsTable aggregateTable, Table attendanceTable) {
        
        var thisObj = this;
        JButton submitBtn = new JButton("Submit");
        
        var sw = new SwingWorker<Object, Integer> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                List<List<String>> data = attendanceTable.getCheckedRowsData();
                
                if (data.isEmpty()) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no checked row yet!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
                
                int selectedNum = updateAggregateWindowDSA(aggregateTable, data);
                publish(selectedNum);
                display.getDialog().dispose();
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                aggregateTable.getjTable().setValueAt(chunks.get(0), thisObj.getRow(), 3);
            }
        };
        
        submitBtn.addActionListener(l -> {
            sw.execute();
        });
        
        return submitBtn;
    }
    
    public int updateAggregateWindowDSA (AggregateClassLessonsTable table, List<List<String>> data) {
        
        var window = table.getAggregateWindow();
        Set<Integer> tempAttendances = window.getTempAttendanceNums();
        Set<Integer> attendences = window.getAttendanceNums();
        setCheckedAttendanceNums(data,getIndentifier());
        int size = setAggregateWindowAttendanceNums(tempAttendances, attendences);
        toucheFinale(window);
        return size;
    }
    
    private void setCheckedAttendanceNums (List<List<String>> data, int lessonNum ) {
        
        emptyfyAttendanceNumList();
        
        data.forEach(e -> {
            int attendanceNum = new AttendanceMedium().getAttendanceNum(lessonNum, FrontEndUtility.getAppFormatDateToSQLFormatDate(e.get(0)), FrontEndUtility.getAppFormatTimestampToSQLFormatTimestamp(e.get(1)));
            attendanceNums.add(attendanceNum);
        });
    }
    
    private int setAggregateWindowAttendanceNums (Set<Integer> tempAttendanceNums, Set<Integer> attendanceNums) {
        
        int size = this.attendanceNums.size();
        
        while (!this.attendanceNums.isEmpty()) {
            
            int data = this.attendanceNums.getLast();
            if (attendanceNums.contains(data))
                tempAttendanceNums.remove(data);
            else
                attendanceNums.add(data);
            
            int removed = this.attendanceNums.removeLast();
//            System.out.println(removed + "\t removed");
        }
        
        return size;
    }
    
    private void toucheFinale (AggregateWindow window) {
       
        Set<Integer> tempAttendanceNums = window.getTempAttendanceNums();
        Set<Integer> attendanceNums = window.getAttendanceNums();
        List<Integer> list = tempAttendanceNums.stream().toList();
        int i = 0;
        
        while (!tempAttendanceNums.isEmpty()) {
            
            int data = list.get(i);
//            System.out.println (data + "\t to be removed(1)");
            removeBothDSAData(tempAttendanceNums, attendanceNums, data);
            i += 1;
        }
        
        var u = attendanceNums.stream().toList();
//        System.out.println("current set: " + u);        
    }
    
    private JButton undoSubmit (TableDisplay display, AggregateClassLessonsTable aggregateTable, Table attendanceTable) {
        
        JButton undoSubmit = new JButton("Undo Submit");
        
        undoSubmit.addActionListener(l -> {
            
            int size = aggregateTable.getAggregateWindow().getTempAttendanceNums().size();
            
            SwingUtilities.invokeLater(() -> 
            {
                int removed = updateTempAttendanceNums(aggregateTable.getAggregateWindow(), attendanceTable.getCheckedRowsData(), getIndentifier());
                
                if (removed == 0) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("You have nothing to undo!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                filterTempAttendanceNums(aggregateTable.getAggregateWindow(), attendanceTable);

                int diff = size - removed;
                aggregateTable.getjTable().setValueAt( /*Integer.parseInt(String.valueOf(aggregateTable.getjTable().getValueAt(super.getRow(), 3))) -*/ diff , super.getRow(), 3);
                display.getDialog().dispose();
            });
        });
        
        return undoSubmit;
    }
    
    private int updateTempAttendanceNums (AggregateWindow window, List<List<String>> data, int lessonNum) {
        
        Set<Integer> tempAttendanceNums = window.getTempAttendanceNums();
        Set<Integer> attendanceNums = window.getAttendanceNums();
        int i[] = {0};
        data.forEach(e -> {
            int attendanceNum = new AttendanceMedium().getAttendanceNum(lessonNum, FrontEndUtility.getAppFormatDateToSQLFormatDate(e.get(0)), FrontEndUtility.getAppFormatTimestampToSQLFormatTimestamp(e.get(1)));
            if (tempAttendanceNums.contains(attendanceNum)) {
                removeBothDSAData(tempAttendanceNums, attendanceNums, attendanceNum);
                i[0] += 1;
            }
        });        
        return i[0];
    }
    
    private int filterTempAttendanceNums (AggregateWindow window, Table attendanceTable) {
        
        var thisObj = this;
        
        var sw = new SwingWorker<Integer, Integer>() {
            
            @Override
            protected Integer doInBackground() throws Exception {
                
                Set<Integer> tempAttendanceNums = window.getTempAttendanceNums();
                Set<Integer> attendanceNums = window.getAttendanceNums();
                int i = 0;
                
//                System.out.println(thisObj.attendanceNums);
                for (int data : thisObj.attendanceNums) {
                    
                    if(!tempAttendanceNums.contains(data))
                        publish(i);
                    
                    i += 1;
                
                }
                
//                System.out.println("curr set: " + tempAttendanceNums.stream().toList() + " \t" + attendanceNums.stream().toList());
                
                
                return tempAttendanceNums.size();
            }

            @Override
            protected void process(List<Integer> chunks) {
//                System.out.println(chunks);
                for (int i : chunks)
                    attendanceTable.getjTable().setValueAt(false, i, 0);
            }
        };

        sw.execute();
        
        try {
            return sw.get();
        } catch (Exception e) {}
        return 0;
    }
    
    private void emptyfyAttendanceNumList () {
        attendanceNums.clear();
    }
    
    @Override
    protected void setTableDisplayProperties (TableDisplay tableDisplay) {
        tableDisplay.setCustomTableDisplayProperties(200, 100, new Dimension(900, 500));
    }    
    
    private void removeBothDSAData (Set<Integer> tempAttendanceNums, Set<Integer> attendanceNums, int data) {
        tempAttendanceNums.remove(data);
        attendanceNums.remove(data);
    }
}
