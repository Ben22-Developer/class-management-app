package app.classmanagementappclient.Frontend.InputWindows.EventHandlers;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.ClassExistanceChecker;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.Proxies.MsgProxy;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.NewLessonsTable.NewLessonsTableHandler;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.RecordedLessonsTable.RecordedDataHandler;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.SubmitNewClassHandler.SubmitBtnHandler;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.ExcelFileInsert.ExcelFileStudentInsert;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.InsertBySavedStudents.InsertBySavedStudents;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.OneByOneInsert.OneByOneStudentInsert;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.LoadWindow.InputWindowLoadingHandler;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author BEN
 */
public class InputWindowUtility 
{
    private final InputWindowLoadingHandler loadingHandler = new InputWindowLoadingHandler();
    
    public void windowLoading (InputWindow inputWindow) {        
        if (!FrontEndUtility.isTeacherLoggedIn()) {
            FrontEndUtility.showMsgJOptionPane("To access this functionality you need to log in or sign in\nGo to file > setings or press 'Ctrl+Alt+S' ", "Informational message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        loadingHandler.windowLoading(inputWindow);
    }
    
    public void showAddClassPanelBtnActionPerformed (InputWindow inputWindow) {
        loadingHandler.showAddClassPanelBtnActionPerformed(inputWindow);
    }
    
    public void submitNewClassActionPerformed (InputWindow inputWindow, JTree jTree) 
    {
        Runnable runnable = () -> {
            SubmitBtnHandler classSubmit = new SubmitBtnHandler(jTree);
            classSubmit.submit(inputWindow);
        };
        new FrontEndUtility().runOneFixedThread(runnable);
    }
    
    public void showAddStudentPanelBtnActionPerformed (InputWindow inputWindow) {
        loadingHandler.showAddStudentPanelBtnActionPerformed(inputWindow);
    }
    
    public void checkClassExistance (JComboBox<String> schoolName, JComboBox<String> className, JComboBox<String> year, JComboBox<String> term)
    {
        new SwingWorker<Object, Object> () 
        {
            @Override
            protected Object doInBackground() throws Exception 
            {        
                new ClassExistanceChecker(String.valueOf( schoolName.getSelectedItem() ), String.valueOf ( className.getSelectedItem() ),  Integer.parseInt(String.valueOf(year.getSelectedItem())), Integer.parseInt(String.valueOf(term.getSelectedItem()))).checkClassExistance (new MsgProxy());
                return null;
            }
        }.execute();
    }
    
    public void insertByIndividualRadioBtnActionPerformed (InputWindow inputWindow) {
        new OneByOneStudentInsert(inputWindow).run();
    }
    
    public void insertByExcelRadioBtnActionPerformed (InputWindow inputWindow) {
        new ExcelFileStudentInsert(inputWindow).runProcess();
    }
    
    public void insertBySavedRadioBtnActionPerformed (InputWindow inputWindow) {
        new InsertBySavedStudents(inputWindow).runProcess();
    }
    
    private void runExecutorService (Runnable runnable) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.execute(runnable);
        es.shutdown();
    }
     
    public void handleViewRecordedLessonsBtnActionPerformed (InputWindow inputWindow) 
    {
        Runnable runnable = () -> {
            RecordedDataHandler recordedLessonsHandler = new RecordedDataHandler();
            recordedLessonsHandler.run(inputWindow);
        };
        new FrontEndUtility().runOneFixedThread(runnable);
    }
    
    public void handleAddLessonsButtonActionPerformed (InputWindow inputWindow) 
    {
        Runnable runnable = () -> 
        {
            NewLessonsTableHandler dataHandler = new NewLessonsTableHandler();
            dataHandler.run(inputWindow);
        };
        runExecutorService(runnable);
    }
}
