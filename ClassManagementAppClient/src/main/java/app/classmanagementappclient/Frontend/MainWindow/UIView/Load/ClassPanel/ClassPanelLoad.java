/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Load.ClassPanel;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.ClassPanelWindowLoadController;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class ClassPanelLoad {
    
    private final MainWindow mainWindow;
    private final Properties uiViewProperties; 
    
    private final List<List<String>> pathList = new ArrayList<>();

    public ClassPanelLoad(MainWindow mainWindow, Properties uiViewProperties) {
        this.mainWindow = mainWindow;
        this.uiViewProperties = uiViewProperties;
    }
    
    public void run () {
        
        String[] childKeys = getValues(UIViewConstants.AllPanelChildKey.toString());
        loadChildPanels(childKeys);
        
        int selectedIndex = Integer.parseInt(FrontEndUtility.getUIViewValue(UIViewConstants.PanelSelectedChild.toString()));
        loadSelectedPanelChild(selectedIndex);
    }
    
    private void loadChildPanels (String[] childKeys) {
        
        for (String childKey : childKeys) 
        {
            String [] values = getValues(childKey);
            
            int displayingPanel = Integer.parseInt(values[0]);
            boolean showingSettingsPanel = Boolean.parseBoolean(values[1]);
            boolean showingTitlePanel = Boolean.parseBoolean(values[2]);
            String[] path = values[3].split("/");
            
            addPathToPathList(path);
            
            classPanelInit(displayingPanel, showingSettingsPanel, showingTitlePanel, path);
        }
    }
    
    
    private void classPanelInit (int displayingCardNum, boolean showingSettingPanel, boolean showingTitlePanel, String[] pathArr) 
    {
        var classPanelWindowLoadController = new ClassPanelWindowLoadController(mainWindow);
        
        List<String> pathList = classPanelWindowLoadController.getPathStr();
        ClassPanel classPanel = classPanelWindowLoadController.getClassPanel();
        
        addEltsInPathStr(pathArr, pathList);
        classPanelWindowLoadController.load(displayingCardNum, showingSettingPanel, showingTitlePanel);
        displayCard(classPanel, displayingCardNum);
        showPanels(classPanel, showingSettingPanel, showingTitlePanel);
    }
    
    private void addEltsInPathStr (String[] pathArr, List<String> pathList) {
        Arrays.stream(pathArr).forEach(pathList::add);
    }
    
    private void displayCard (ClassPanel classPanel, int displayingCardNum) {
        
        switch (displayingCardNum) {
            
            case 0 -> classPanel.studentsButtonAction();
            case 1 -> classPanel.lessonsButtonAction();
            case 2 -> classPanel.attendancesButtonAction();
            case 3 -> classPanel.assessmentsButtonAction();
            default -> throw new AssertionError("Unkown displaying card at "+getClass().getName()+".displayCard\ndisplayingCardNum == " + displayingCardNum);
        }
    }

    
    private void showPanels (ClassPanel classPanel, boolean showingSettingPanel, boolean showingTitlePanel) {
        
        if (!showingSettingPanel)
            classPanel.hideSetting();
        
        if (!showingTitlePanel)
            classPanel.hideTitle();
    }
    
    private void loadSelectedPanelChild (int index) {
        
        SwingUtilities.invokeLater(() -> {
            mainWindow.getJTabbedPane().setSelectedIndex(index);
        });
    }
    
    private String[] getValues (String key) {
        return FrontEndUtility.getUIViewValue(key).split(",");
    }  
    
    private void addPathToPathList (String[] pathArr) {
        
        List <String> pathList = new ArrayList<>();
        
        TreeWindowUtility.programDSToTreeDS(pathArr, pathList);
        
        this.pathList.add(pathList);
    }

    public List<List<String>> getPathList() {
        return pathList;
    }
}
