/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Load;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Load.ClassPanel.ClassPanelLoad;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel.DashboardPanelLoad;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Load.TreeView.LoadTreeView;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class LoadUIViewUtility {
    
    private final MainWindow mainWindow;
    private final Properties uiViewProperties = FrontEndUtility.getUiViewProperties();
    
    private final LoadTreeView loadTreeView;
    
    private final ClassPanelLoad classPanelLoad;
    private final DashboardPanelLoad dashboardPanelLoad;

    public LoadUIViewUtility(MainWindow mainWindow) {
        
        this.mainWindow = mainWindow;
        
        this.loadTreeView = new LoadTreeView(mainWindow);
        
        this.classPanelLoad = new ClassPanelLoad(mainWindow, uiViewProperties);
        this.dashboardPanelLoad = new DashboardPanelLoad(mainWindow, uiViewProperties);
    }
    
    public void run () {
        
        String view = FrontEndUtility.getUIViewValue(UIViewConstants.View.toString());
        
        if (!view.isBlank())
            loadView(view);
        
        String showingPanel = FrontEndUtility.getUIViewValue(UIViewConstants.ShowingPanel.toString());
        
        if (!showingPanel.isBlank())
            loadPanel(showingPanel);
        
        if (loadTreeView.hasShownTree())
            loadTreeView.expandTreePath(classPanelLoad.getPathList());
        
        eraseProperties();
        FrontEndUtility.storeUIViewProperties();
    }
    
    private void loadView (String view) {
        
        if (view.equals(UIViewConstants.ViewTreeFolder.toString()))
            loadTreeView.showTree();
        
        else if (view.equals(UIViewConstants.ViewDashboard.toString()))
            mainWindow.dashboardViewActionPerform();
    }
    
    private void loadPanel (String showingPanel) {
        
        if (showingPanel.equals(UIViewConstants.ClassPanel.toString()))
            classPanelLoad.run();
        
        else if (showingPanel.equals(UIViewConstants.DashboardPanel.toString()))
            dashboardPanelLoad.run();
    }
    
    private void eraseProperties () {
        uiViewProperties.clear();
    }
}
