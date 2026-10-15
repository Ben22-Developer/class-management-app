package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface.PanelLoadInterfaceIndirect;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.InputWindowUtility;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class LessonPanelLoadProxy implements PanelLoadInterfaceIndirect {

    private ClassPanel classPanel;
    private int classTermNum;
    
    @Override
    public void load (PanelLoad panelLoad, ClassPanel classPanel, int classTermNum) {
        
        this.classPanel = classPanel;
        this.classTermNum = classTermNum;
        
        List<String> classLessons = getClassLessons();
        
        if (!classLessons.isEmpty()) {
            List<List<String>> data = new ArrayList<>();
            data.add(classLessons);
            new PanelLoad(classPanel, classTermNum).loadClassLessonsTable(data);
            return;
        }
        
        PanelDisplayHandler panelDisplayHandler = new PanelDisplayHandler(classPanel);
        
        List<List<Object>> classWithLessons = new ClassLessonMedium().getAllLessonsWithDetails();     
        
        if (classWithLessons.isEmpty()) {
            panelLoad.UIForNoData_NoClassWithData (panelDisplayHandler,  classPanel.getClassLessonsTablePanel(), new JLabel("Click this button to add lessons via input window: "), "There is no recorded lessons in this class", getAddBtnListener(), getReloadBtnListener());
            return;
        }
        
        List<List<String>> classWithLessonsStr = objectToString(classWithLessons);
        panelLoad.UIForNoData_NoClassWithData (panelDisplayHandler, classPanel.getClassLessonsTablePanel(), new JLabel("Click this button to add lessons via input window: "), "There is no recorded lessons in this class", getAddBtnListener(), getReloadBtnListener());
        panelLoad.UIForNoData_ClassWithData ("Other classes with lessons " + classWithLessonsStr.size() + " classes",panelDisplayHandler, classPanel.getClassLessonsTablePanel(), classWithLessonsStr, EditableTableType.IsShowingClassWithLessonsTable, List.of("No", "Schools", "Classes", "Years", "Terms", "View"), List.of(1,2,3,4), 200);        
    }
    
    private List<List<String>> objectToString (List<List<Object>> classWithLessons) {
        
        List<List<String>> classWithLessons1 = new ArrayList<>();
        
        for (List<Object> objects : classWithLessons) {
            
            List<String> strings = new ArrayList<>();
            
            objects.stream().forEach(e -> {
                strings.add(String.valueOf(e));
            });
            
            classWithLessons1.add(strings);
        }
        
        return classWithLessons1;
    }
    
    private List<String> getClassLessons () {
        
        String schoolName = classPanel.getSchoolLabel().getText();
        String className = classPanel.getClassNameLabel().getText();
        int year = Integer.parseInt( classPanel.getYearLabel().getText() );
        int term = Integer.parseInt( classPanel.getTermLabel().getText() );
                
        return new ClassLessonMedium().getAllLessonsInTerm(schoolName, className, year, term);     
    }

    @Override
    public ActionListener getAddBtnListener() {
    
    return l -> {
        
        String schoolName = classPanel.getSchoolLabel().getText();
        String className = classPanel.getClassNameLabel().getText();
        String yr = classPanel.getYearLabel().getText();
        String term = classPanel.getTermLabel().getText();

        InputWindow inputWindow = new InputWindow(schoolName, className, yr, term);
        InputWindowUtility utility = new InputWindowUtility();
        utility.windowLoading(inputWindow);
        utility.showAddClassPanelBtnActionPerformed(inputWindow);
        };
    }

    @Override
    public ActionListener getReloadBtnListener() {
        return l -> {
            new PanelLoad (classPanel, classTermNum).loadPanel (new LessonPanelLoadProxy());
        };
    }
    
    
}
