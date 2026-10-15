package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface.PanelLoadInterface;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.ClassStudentTable.ClassStudentTableManagerProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.LessonsTable.ClassLessonsTableManagerProxy;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author BEN
 */
public class PanelLoad {
    
    private final ClassPanel classPanel;
    private final int classTermNum;

    public PanelLoad(ClassPanel classPanel, int classTermNum) {
        this.classPanel = classPanel;
        this.classTermNum = classTermNum;
    }

    public void loadPanel(PanelLoadInterface panelLoadInterface) {
        panelLoadInterface.load (this, classPanel, classTermNum);
    }    
    
    public void loadClassStudentsTable (List<List<String>> classStudents) {
        var proxy = new ClassStudentTableManagerProxy();
        addNoToData(proxy, classStudents);
        Table table = ClassStudentsTable.getClassStudentsTable(classStudents, classTermNum, 300);
        var manager = new TableManager(classStudents, classTermNum, classPanel, table, classPanel.getClassStudentsTablePanel(), proxy);        
        manager.run();
    }
        
    public void loadClassLessonsTable (List<List<String>> classLessons) {
        var proxy = new ClassLessonsTableManagerProxy();
        addNoToData(proxy, classLessons);
        Table table = ClassLessonsTable.getClassLessonsTable(true, true, "Edit", List.of ("", "No", "Lessons", "Edit"), List.of(2), classLessons, classTermNum, 300);
        var manager = new TableManager(classLessons, classTermNum, classPanel, table, classPanel.getClassLessonsTablePanel(), proxy);        
        manager.run();
    }
    
    
    
    private void addNoToData (DataEntryActions proxy, List<List<String>> data) {
        proxy.addNoToData(data);
    }    
    
    public void UIForNoData_NoClassWithData (PanelDisplayHandler panelDisplayHandler, JPanel mainPanel, JLabel label, String errMsg, ActionListener addStudentsListener, ActionListener reloadListener) {
        
        JButton button = new JButton("+");
        panelDisplayHandler.showAddDataByInputWindowPanel(mainPanel, label, button);
        panelDisplayHandler.addDataByInputWindowButtonEventListener(button, addStudentsListener);
        JButton reloadBtn = panelDisplayHandler.showReloadPanel (errMsg, mainPanel);
        if (reloadBtn != null)
            panelDisplayHandler.setReloadBtnEvent(reloadBtn, reloadListener);
    }
    
    public void UIForNoData_ClassWithData (String titleLabelTxt, PanelDisplayHandler panelDisplayHandler, JPanel mainPanel, List<List<String>> data, EditableTableType tableType, List<String>headers, List<Integer> clmsToCheck, int clmWidth) {
        
        for (int i = 0; i < data.size(); i++)
            data.get(i).add(0, String.valueOf(i + 1));
        
        JPanel centerPanel = panelDisplayHandler.showClassWithStudentsPanel(mainPanel, titleLabelTxt);
        panelDisplayHandler.setTable(centerPanel, data, classTermNum, tableType, headers, clmsToCheck , clmWidth);
        panelRevalidate(centerPanel);    
    }
    
    public void UIForCommonTabbedPanePanel (PanelDisplayHandler panelDisplayHandler, JPanel mainPanel, ActionListener listener) {
        JButton reloadBtn = panelDisplayHandler.showReloadPanel(mainPanel);
        panelDisplayHandler.setReloadBtnEvent(reloadBtn, listener);
        panelDisplayHandler.setPanelBgColor(mainPanel, BorderLayout.SOUTH);
    }
    
    private void panelRevalidate (JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }
}
