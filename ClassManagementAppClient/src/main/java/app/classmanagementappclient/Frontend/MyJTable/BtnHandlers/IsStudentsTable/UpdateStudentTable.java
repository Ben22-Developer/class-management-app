package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsStudentsTable;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class UpdateStudentTable {
    
    private final List<String> currStudentData;
    private final List<String> newStudentData;
    private final Table table;
    private final int row;
    
    public UpdateStudentTable(Table table, int row, List<String> oldStudentData, List<String> newStudentData) {
        this.currStudentData = oldStudentData;
        this.newStudentData = newStudentData;
        this.table = table;
        this.row = row;
    }
    
    public boolean run () {

        SwingWorker sw = new SwingWorker<Boolean, List<String>>() 
        {
            @Override
            protected Boolean doInBackground() throws Exception {
               
                Student currStudent = new Student(currStudentData.get(0), currStudentData.get(1));
                Student newStudent = new Student(newStudentData.get(0), newStudentData.get(1));
                Boolean res = new StudentMedium().updateStudent(currStudent, newStudent);
                
                if (res)    
                    publish(List.of(newStudentData.get(0), newStudentData.get(1)));
                return res;
            }

            @Override
            protected void process(List<List<String>> chunks) { 
                for (List<String> chunk : chunks)
                    table.setValueAt(row, chunk);
            }
        };
        
        sw.execute();
        Boolean bool = null;
        try {
            bool = (Boolean) sw.get();
        } catch (Exception ex) {} 
        
        return bool;
    }
}
