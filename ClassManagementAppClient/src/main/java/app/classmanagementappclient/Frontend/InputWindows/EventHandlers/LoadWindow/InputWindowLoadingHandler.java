package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.LoadWindow;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InputWindowLoadingHandler 
{
    public void windowLoading (InputWindow inputWindow)
    {   
        windowLoadingCoordinator(inputWindow);
        setInputWindowJDialog(inputWindow);
        //new FrontEndUtility().runOneFixedThread(runnable);
    }
    
    public void showAddClassPanelBtnActionPerformed (InputWindow inputWindow) 
    {
        String constraint = getInputWindowCurrentPanelConstraint(0);
        panelLoadCoordinator(inputWindow, constraint);
    }

    public void showAddStudentPanelBtnActionPerformed (InputWindow inputWindow) {
        String constraint = getInputWindowCurrentPanelConstraint(1);
        panelLoadCoordinator(inputWindow, constraint);
    }    
    
    private void panelLoadCoordinator (InputWindow inputWindow, String constraint) 
    {
        new SwingWorker<Object, Object> () 
        {
            @Override
            protected Object doInBackground() throws Exception 
            {        
                updateInputWindowCurrentPanel(constraint);        
                panelDisplay(inputWindow, constraint);
                return null;
            }
        }.execute();       
    }    
    
    public void windowLoadingCoordinator (InputWindow inputWindow) 
    {
        new SwingWorker<Object, Object> () 
        {
            @Override
            protected Object doInBackground() throws Exception 
            {
                int index = windowLoadDisplayingPanel(inputWindow);
                panelDisplay(inputWindow, getInputWindowCurrentPanelConstraint(index));
                
                inputWindow.setWindowLoading(true);
                populateToList(inputWindow);
                loadingController(inputWindow, index);
                inputWindow.setWindowLoading(false);
                
                return null;
            }
            
        }.execute();
    }
    
    private void loadingController (InputWindow inputWindow, int index) 
    {
        switch (index) 
        {
            case 0 -> { //class panel
                setNewClassPanelGUIPopulate(inputWindow);
                setNewStudentPanelGUIPopulate(inputWindow);
            }
            case 1 -> { //student panel
                setNewStudentPanelGUIPopulate(inputWindow);
                setNewClassPanelGUIPopulate(inputWindow);
            }
        }
    }
  
    private int windowLoadDisplayingPanel (InputWindow inputWindow)
    {
        String constraint = getInputWindowCurrentPanelConstraint();
        panelDisplay(inputWindow, constraint);
        return FrontEndUtility.getWindowPanelToDisplay("InputWindowCurrentPanel");
    }
    
    private void populateToList (InputWindow inputWindow) {   
        
        inputWindow.setSchools( new SchoolMedium().getAllSchools() );
        inputWindow.setClasses(  new ClassMedium().getAllClasses() );
        inputWindow.setClassYears( new ClassYearMedium().getAllYears() );
        inputWindow.setClassTerms( new ClassTermMedium().getAllTerms() );
        inputWindow.setClassLessons( new ClassLessonMedium().getAllLessons() );
    }
    
    private void setNewClassPanelGUIPopulate (InputWindow inputWindow)
    {
        setNewClassPanel_schoolsGUIPopulate(inputWindow);
        setNewClassPanel_classesGUIPopulate(inputWindow);
        setNewClassPanel_termsGUIPopulate(inputWindow);
        setNewClassPanel_yearsGUIPopulate(inputWindow);
    }
    
    private void setNewStudentPanelGUIPopulate (InputWindow inputWindow) 
    {
        setNewStudentPanel_schoolsGUIPopulate(inputWindow);
        setNewStudentPanel_classesGUIPopulate(inputWindow);
        setNewStudentPanel_termsGUIPopulate(inputWindow);
        setNewStudentPanel_yearsGUIPopulate(inputWindow);        
    }
        
    private void setNewClassPanel_schoolsGUIPopulate (InputWindow inputWindow) 
    {
        List<String> schools = inputWindow.getSchools();
        JComboBox<String> recordedSchoolBox = inputWindow.getRecordedSchoolsNewClassComboBox();
        uiDataPopulateStr(recordedSchoolBox, schools);        
    }
    
    private void setNewStudentPanel_schoolsGUIPopulate (InputWindow inputWindow) 
    {
        List<String> schools = inputWindow.getSchools();
        JComboBox<String> recordedSchoolBox = inputWindow.getRecordedSchoolNewStudentComboBox();   
        uiDataPopulateStr(recordedSchoolBox, schools);  
    }

    private void setNewStudentPanel_classesGUIPopulate (InputWindow inputWindow) 
    {
        List<String> classes = inputWindow.getClasses();
        JComboBox<String> recordedClassBox = inputWindow.getRecordedClassNewStudentComboBox();
        uiDataPopulateStr(recordedClassBox, classes);        
    }
    
    private void setNewClassPanel_classesGUIPopulate (InputWindow inputWindow) 
    {
        List<String> classes = inputWindow.getClasses();
        JComboBox<String> recordedClassBox = inputWindow.getRecordedClassesNewClassComboBox();
        uiDataPopulateStr(recordedClassBox, classes);     
    }
    
    private void setNewClassPanel_termsGUIPopulate (InputWindow inputWindow) 
    {
        List<Integer> terms = inputWindow.getClassTerms();        
        JComboBox<String> recordedTermBox = inputWindow.getRecordedTermsNewClassComboBox();     
        uiDataPopulateInt(recordedTermBox, terms);        
    }
    
    private void setNewStudentPanel_termsGUIPopulate (InputWindow inputWindow) 
    {
        List<Integer> terms = inputWindow.getClassTerms();      
        JComboBox<String> recordedTermBox = inputWindow.getRecordedTermsNewStudentComboBox();     
        uiDataPopulateInt(recordedTermBox, terms);        
    }

    private void setNewClassPanel_yearsGUIPopulate (InputWindow inputWindow) 
    {
        List<Integer> years = inputWindow.getClassYears();        
        JComboBox<String> recordedTermBox = inputWindow.getRecordedYearsNewClassComboBox();     
        uiDataPopulateInt(recordedTermBox, years);        
    }
    
    private void setNewStudentPanel_yearsGUIPopulate (InputWindow inputWindow)  
    {
        List<Integer> years = inputWindow.getClassYears(); 
        JComboBox<String> recordedTermBox = inputWindow.getRecordedYearNewStudentComboBox();     
        uiDataPopulateInt(recordedTermBox, years);        
    } 
    
    
    private void uiDataPopulateStr (JComboBox<String> recordedComboBox, List<String> data)
    {
        recordedComboBox.removeAllItems();
        for (String aData : data) {
                recordedComboBox.addItem(aData);
        }       
    }
    
    private void uiDataPopulateInt (JComboBox<String> recordedComboBox, List<Integer> data)
    {
        recordedComboBox.removeAllItems();
        for (int aData : data) {
                recordedComboBox.addItem(String.valueOf(aData));
        }       
    }    

    private void updateInputWindowCurrentPanel (String objectConstraint) 
    {
        int index = -1;
        switch (objectConstraint) 
        {
            case "card2" -> {
                index = 0;
            }
            case "card3" -> {
                index = 1;
            }            
        }
        FrontEndUtility.storeWindowPanelToDisplay("InputWindowCurrentPanel",index);       
    }    
    
    private String getInputWindowCurrentPanelConstraint (int...index) 
    {
        int panelIndex = (index.length == 0) ? FrontEndUtility.getWindowPanelToDisplay("InputWindowCurrentPanel") : index[0];
        switch (panelIndex) 
        {
            case 0 -> { // newClassPanel
                return "card2";
            }
            case 1 -> { //newStudentPanel
                return "card3";
            }            
        }
        return "";
    }
    
    private void panelDisplay (InputWindow inputWindow, String cardStr) {
        JPanel inputPanel = inputWindow.getInputPanel();
        CardLayout cardLayout = (CardLayout) inputPanel.getLayout();
        cardLayout.show(inputPanel, cardStr);
    }  
    
    private void setInputWindowJDialog (InputWindow inputWindow) {
        inputWindow.setJDialog();
    }
}
