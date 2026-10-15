package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.SubmitNewClassHandler;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.InsertTreeDataFromInputWindow;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.LoadWindow.InputWindowLoadingHandler;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;

import javax.swing.*;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class SubmitBtnHandler 
{
    private final JTree jTree;
    private int isInserted;

    public SubmitBtnHandler(JTree jTree) {
        this.jTree = jTree;
    }
    
    public void submit (InputWindow inputWindow) 
    {
        String schoolName = inputWindow.getSchoolNameTextField().getText();
        String className = inputWindow.getClassNameTextField().getText();
        String year = inputWindow.getYearTextField().getText();
        String term = inputWindow.getTermTextField().getText();
        Set<String> newLessons = inputWindow.getTextAreaContent();
        
        if (emptyStrValidation(schoolName, className, year, term, newLessons))
            return;
        
        boolean res = dataInsert(schoolName, className, year, term, newLessons);
        if (!res)
            return;
        
        if (isInserted == 0)
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no new data to insert!", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
        else { 
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Data inserted successfully!", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            reloadSettingsUIComponents(inputWindow);
        }
            
    }
    
    private boolean emptyStrValidation (String schoolName, String className, String year, String term, Set<String> newLessons) {
        boolean res = schoolName.isBlank() || className.isBlank() || year.isBlank() || term.isBlank() || newLessons.isEmpty();
        if (res)
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("No text field should must be blank!", "Error Message", JOptionPane.ERROR_MESSAGE);
        return res;
    }

    
    private void reloadSettingsUIComponents (InputWindow inputWindow) 
    {
        InputWindowLoadingHandler loadingHandler = new InputWindowLoadingHandler();
        loadingHandler.windowLoadingCoordinator(inputWindow);
        inputWindow.getAddedLessonsTextArea().setText("");    
        inputWindow.setMakeNewLessonTable(true);
        inputWindow.setMakeRecordedLessonTable(true);
        inputWindow.getTextAreaContent().clear();
        
    }
    
    public boolean dataInsert(String schoolName, String className, String year, String term, Set<String> newLessons) {
        var itdfiw =  new InsertTreeDataFromInputWindow(jTree);
        itdfiw.dataInsert(schoolName, className, year, term, newLessons);
        isInserted = itdfiw.getIsInserted();
        return itdfiw.isCanDisplayMsg();
    }
}
