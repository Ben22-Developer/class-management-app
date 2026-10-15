/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Save;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Save.ClassPanelUI.ClassPanelUISave;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Save.DashboardUI.DashboardUISave;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class SaveUIViewUtility {
    
    private final MainWindow mw;
    
    private final Properties uiViewProperties = FrontEndUtility.getUiViewProperties();
    
    public SaveUIViewUtility(MainWindow mw) {
        this.mw = mw;
    }
    
    public void run () {
        saveView();
        saveUI();
        FrontEndUtility.storeUIViewProperties();
    }
    
    private void saveView () {
        String view = getShowingView();
        uiViewProperties.put(UIViewConstants.View.toString(), view);
    }
    
    private String getShowingView () {
        
        if (mw.isShowingTree())
            return UIViewConstants.ViewTreeFolder.toString();
        
        else if (mw.isShowingDashboardPanel())
            return UIViewConstants.ViewDashboard.toString();
        
        return "";        
    }
    
    private void saveUI () {
        
        if (mw.isShowingClassPanel())
            new ClassPanelUISave(mw, uiViewProperties).run();
        
        else if (mw.isShowingDashboardPanel())
            new DashboardUISave(mw, uiViewProperties).run();
    }
}
