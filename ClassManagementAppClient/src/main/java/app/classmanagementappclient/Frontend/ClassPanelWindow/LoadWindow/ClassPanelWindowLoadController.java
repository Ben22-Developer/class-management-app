package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.AssessmentPanelLoadProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.AttendancePanelLoadProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.LessonPanelLoadProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.StudentPanelLoadProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassPanelWindowLoadController {
    
    private final MainWindow mainWindow;
    private final List<String> pathStr = new ArrayList();
    private int classTermNum;
    private final ClassPanel classPanel = new ClassPanel();
    
    public ClassPanelWindowLoadController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }      
    
    public void load () {
        setPathStr();
        setClassTermNum();
        setTitlePanelLabelTxt();
        setClassPanelFields(0, true, true);
        addClassPanelToMainWindowList();
        // any stuff to be done can be put here
        showPanel();
    }

    public void load 
        (int displayingCardNum, boolean showingSettingPanel, boolean showingTitlePanel) 
    {
        setTitlePanelLabelTxt();
        setClassPanelFields(displayingCardNum, showingSettingPanel, showingTitlePanel);
        addClassPanelToMainWindowList();
        // any stuff to be done can be put here
        showPanel();
    }
    
    private void setPathStr() {
        JTree tree = mainWindow.getjTree();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLeadSelectionPath().getLastPathComponent();
        Object[] pathObj = node.getUserObjectPath();
        TreeWindowUtility.pathObjToProgramDS(pathObj, pathStr);
    }
    
    private void setClassTermNum() {
        classTermNum = new ClassTermMedium().getOneClassTermNumV1(pathStr.get(0), pathStr.get(1), Integer.parseInt(pathStr.get(2)), Integer.parseInt(pathStr.get(3)));
    } 
    
    private void setTitlePanelLabelTxt() {
        classPanel.getSchoolLabel().setText(pathStr.get(0));
        classPanel.getClassNameLabel().setText(pathStr.get(1));
        classPanel.getYearLabel().setText(pathStr.get(2));
        classPanel.getTermLabel().setText(pathStr.get(3));
    }    

    private void addClassPanelToMainWindowList () {
        mainWindow.addDataToClassPanelList(classPanel);
    }

    private void setClassPanelFields (int displayingCardNum, boolean showingSettingPanel, boolean showingTitlePanel) 
    {
        classPanel.setDisplayingCardNum(displayingCardNum);
        classPanel.setShowingSettingsPanel(showingSettingPanel);
        classPanel.setShowingTitlePanel(showingTitlePanel);
    }
    
    private void showPanel () {
        
        new SwingWorker<Object, Integer> () {
            
            private final int newTab = -1;
            private final JTabbedPane pane = mainWindow.getJTabbedPane();
            private boolean endedProcess;
            private int index;
            @Override
            protected Object doInBackground() throws Exception {
                
                index = pane.indexOfTab(classPanel.getTitle());
                publish(index);
                return null;
            }

            @Override
            protected void done() {
                
                panelsLoad();
                
                if (index != -1)
                    return;
                
               FrontEndUtility.updateUI();
            }

            @Override
            protected void process(List<Integer> chunks) {
                if (chunks.getFirst() == newTab) {
                    addNewTab();
                }
                else    
                    pane.setSelectedIndex(chunks.getFirst());
//                endedProcess = true;
            }
            
            private void addNewTab () {
                
                if (mainWindow.getSplitPane().getRightComponent() == null)
                    mainWindow.addToSplitPanelRightSide(mainWindow.getJTabbedPane());
                
                pane.addTab(classPanel.getTitle(), classPanel);
                pane.setSelectedIndex(pane.getTabCount() - 1);
                
                JPanel panel = getTitlePanel(pane);
                pane.setTabComponentAt(mainWindow.getJTabbedPane().getTabCount() - 1, panel);                
                
            }
            
        }.execute();
    }
    
    private JPanel getTitlePanel (JTabbedPane pane) {
        
        JPanel panel = new JPanel();
        JLabel label = new JLabel(classPanel.getTitle());
        JButton btn = new JButton("X");

        FrontEndUtility.setJComponentDefaultFont(label);
        FrontEndUtility.setJComponentCustomFont(btn, new Font("Arial", Font.PLAIN, 18));

        setClosingTabButtonProperties(btn, pane);
        
        panel.setOpaque(false);
        panel.add(label);
        panel.add(btn);
        return panel;
    }
    
    private void setClosingTabButtonProperties (JButton closingTabButton, JTabbedPane pane) {
    
        closingTabButton.setBackground(Color.RED);        
        closingTabButton.addActionListener(l -> 
        {
            SwingUtilities.invokeLater(() -> 
            {
                int index = pane.indexOfTab(classPanel.getTitle());
                pane.remove(index);
                mainWindow.removeDataFromClassPanelList(classPanel);            
            });
        });        
    }
    
    private void panelsLoad () {
        
        new SwingWorker<Object, Object> ()  {

            @Override
            protected Object doInBackground() throws Exception {
                PanelLoad panelLoad = new PanelLoad(classPanel, classTermNum);
                panelLoad.loadPanel (new StudentPanelLoadProxy());
                panelLoad.loadPanel (new LessonPanelLoadProxy());
                panelLoad.loadPanel (new AttendancePanelLoadProxy());
                panelLoad.loadPanel (new AssessmentPanelLoadProxy());
//                classPanel.loadCustomPanel();
                return null;
            }
        }.execute(); 
    }

    public ClassPanel getClassPanel() {
        return classPanel;
    }

    public List<String> getPathStr() {
        return pathStr;
    }
}
