package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies;

import app.classmanagementappclient.Medium.ClassTerm.ReadClassTerm.ClassTermReadMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface.PanelLoadInterfaceIndirect;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.InputWindowUtility;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author BEN
 */
public class StudentPanelLoadProxy implements PanelLoadInterfaceIndirect {

    private ClassPanel classPanel;
    private int classTermNum;
    
    @Override
    public void load (PanelLoad panelLoad, ClassPanel classPanel, int classTermNum) {
        
        this.classPanel = classPanel;
        this.classTermNum = classTermNum;
        
        List<List<String>> classStudents = new StudentClassMedium().getAllClassStudents(classTermNum);      
        
        if (!classStudents.isEmpty()) {
            panelLoad.loadClassStudentsTable(classStudents);
            return;
        }
        
        PanelDisplayHandler panelDisplayHandler = new PanelDisplayHandler(classPanel);
        
        List<Integer> classWithStudents = new StudentClassMedium().getAllClassWithStudents();     
        
        if (classWithStudents.isEmpty()) {
            panelLoad.UIForNoData_NoClassWithData (panelDisplayHandler, classPanel.getClassStudentsTablePanel(), new JLabel("Click this button to add students via input window: "), "There is no students in class", getAddBtnListener(), getReloadBtnListener());
            return;
        }
        
        List<List<String>> classTermDetails = new ClassTermReadMedium().getClassesDetails(classWithStudents);
        panelLoad.UIForNoData_NoClassWithData (panelDisplayHandler, classPanel.getClassStudentsTablePanel(), new JLabel("Click this button to add students via input window: "), "There is no students in class", getAddBtnListener(), getReloadBtnListener());
        panelLoad.UIForNoData_ClassWithData ("Other classes with students " + classTermDetails.size() + " classes" , panelDisplayHandler, classPanel.getClassStudentsTablePanel(), classTermDetails, EditableTableType.IsShowingClassWithStudentsTable, List.of("No", "Schools", "Classes", "Years", "Terms", "View"), List.of(1,2,3,4), 200);        
    }

    @Override
    public ActionListener getAddBtnListener() {
    
    return l -> {
            
            InputWindow inputWindow = new InputWindow();
            InputWindowUtility utility = new InputWindowUtility();
            utility.windowLoading(inputWindow);
            utility.showAddStudentPanelBtnActionPerformed(inputWindow);
            
            while (inputWindow.isWindowLoading()) {}

            inputWindow.getRecordedSchoolNewStudentComboBox().setSelectedItem(classPanel.getSchoolLabel().getText());
            inputWindow.getRecordedClassNewStudentComboBox().setSelectedItem(classPanel.getClassNameLabel().getText());
            inputWindow.getRecordedYearNewStudentComboBox().setSelectedItem(classPanel.getYearLabel().getText());
            inputWindow.getRecordedTermsNewStudentComboBox().setSelectedItem(classPanel.getTermLabel().getText());
        };
    }

    @Override
    public ActionListener getReloadBtnListener() {
        return l -> {
            new PanelLoad (classPanel, classTermNum).loadPanel (new StudentPanelLoadProxy());
        };
    }
}
