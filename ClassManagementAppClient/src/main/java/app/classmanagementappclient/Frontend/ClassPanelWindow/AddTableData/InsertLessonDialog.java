package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy.InsertClassLessonProxy;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertLessonDialog extends ADialog {

    private final int classTermNum;
    private final int rows;
    private boolean isInserted;
    private Object[] res;
    
    public InsertLessonDialog(int classTermNum, int rows) {
        this.classTermNum = classTermNum;
        this.rows = rows;
    }
    
    @Override
    public void drawJDialog() {
        super.setHeaders(List.of("Lesson name"));
        super.setValues(List.of(""));
        super.drawJDialog();
    }
    
    @Override
    protected void addTitleLabel() {
        super.setTitleLable("Add");
        super.addTitleLabel();
    }

    @Override
    protected synchronized void setJDialogProp() {
        super.setWindowTitle("Add New Lesson");
        super.setJDialogProp();
        JDialog dialog = super.getDialog();
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                notifyThread();
            }
        });
    }
    
    @Override
    public synchronized boolean handler() {
        
        String userInput = super.getUserInputs().get(0);

        ClassLesson classLesson = new ClassLesson(userInput);
        classLesson.setClassTermNum(classTermNum);
        boolean bool = new ClassLessonMedium(classLesson).insertClassLesson(new InsertClassLessonProxy());
        
        if (bool) {
            setRes(new Object[] { false, rows + 1, classLesson.getLessonName(), "Edit" });
            notifyThread();
        }
        return bool;
    }

    private synchronized void notifyThread () {
        isInserted = true;
        notifyAll();        
    }
    
    public boolean isIsInserted() {
        return isInserted;
    }

    public synchronized void setIsInserted(boolean isInserted) {
        this.isInserted = isInserted;
    }

    public synchronized Object[] getRes() {
        while (res == null && !isInserted) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        return res;
    }

    public void setRes(Object[] res) {
        this.res = res;
    }
}
