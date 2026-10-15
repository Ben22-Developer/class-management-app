package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.OneByOneInsert.InsertOneStudentDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertOneStudentFromClassStudentPanel extends InsertOneStudentDialog {

    private Object[] insertedData;
    private final  int classTermNum;
    private boolean inserted;
    
    public InsertOneStudentFromClassStudentPanel(Table table, int classTermNum) {
        super(table);
        this.classTermNum = classTermNum;
    }
    
    @Override
    protected void addDialogListener () {
        super.getDialog().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                notifyThread();
            }
        });
    }
    
    @Override
    protected void dataInsert() {
        
        List<String> data = List.of(String.valueOf( super.getTextField().getText() ) , String.valueOf( super.getTextField1().getText() ));    
        Student student = new Student(data.get(0), data.get(1));
        StudentClass studentClass = new StudentClass(student, classTermNum);
        
        StudentClassMedium medium = new StudentClassMedium(studentClass);
        boolean bool = medium.insertStudentToClass();
        
        if (bool) {
            setInsertedData( new Object[] { false, getTable().getjTable().getRowCount() + 1, super.getTextField().getText(), super.getTextField1().getText(), "Edit" } );
            disposeDialog();        
            notifyThread();
        }
        else 
            medium.showErrMsg();
    }
    
    private synchronized void notifyThread () {
        inserted = true;
        notifyAll();
    }

    public void disposeDialog () {
        super.getDialog().dispose();
    }
    
    public synchronized Object[] getInsertedData() {
        while (insertedData == null && !inserted) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        return insertedData;
    }

    public void setInsertedData(Object[] insertedData) {
        this.insertedData = insertedData;
    }
}
