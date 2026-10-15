package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.ClassOptions;

import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.TableDisplayHelperClass;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ClassOptionsWorker extends SwingWorker<Object, Object>{

    private final TableDisplayHelperClass insertManipulator;

    public ClassOptionsWorker (TableDisplayHelperClass insertManipulator) {
        this.insertManipulator = insertManipulator;
    }
    
    @Override
    protected Object doInBackground() throws Exception {
        new ClassOptions( insertManipulator.getInputWindow() ).run();
        return null;
    }

    @Override
    protected void done() {
        insertManipulator.setTableDisplayTitle();
    }
}
