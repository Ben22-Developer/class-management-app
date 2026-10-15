/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Save.DashboardUI;

import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.menuEnum.DisplayingMenuWindowEnum;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.DashboardViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class DashboardUISave {

    private final Menu menu;
    private final Properties uiViewProperties;
    
    public DashboardUISave (MainWindow mainWindow, Properties uiViewProperties) {
        menu = mainWindow.getDashboard().getMenu();
        this.uiViewProperties = uiViewProperties;
    }

    public void run() {
        
        showingPanel();
        selectedChild();
        panelChildSelectedViews();
        isMenuFull();
        isChatMenuShowing();
    }
    
    private void showingPanel() {
        uiViewProperties.put(UIViewConstants.ShowingPanel.toString(), UIViewConstants.DashboardPanel.toString());
    }
    
    private void selectedChild () {

        var displayingMenuWindowEnum = menu.getDisplayingMenuEnum();
        
        if (displayingMenuWindowEnum == null)
            return;
        
        switch (displayingMenuWindowEnum) {
            
            case DisplayingDashboard -> uiViewProperties.put(UIViewConstants.PanelChild.toString(), DisplayingMenuWindowEnum.DisplayingDashboard.toString());
            
            case DisplayingChat -> uiViewProperties.put(UIViewConstants.PanelChild.toString(), DisplayingMenuWindowEnum.DisplayingChat.toString());
            
            case DisplayingSchedule -> uiViewProperties.put(UIViewConstants.PanelChild.toString(), DisplayingMenuWindowEnum.DisplayingSchedule.toString());
            
            case DisplayingContactUs -> uiViewProperties.put(UIViewConstants.PanelChild.toString(), DisplayingMenuWindowEnum.DisplayingContactUs.toString());
        }        
    }

    private void panelChildSelectedViews() {
        
        var displayingMenuWindowEnum = menu.getDisplayingMenuEnum();
        
        if (displayingMenuWindowEnum == null)
            return;        
        
        switch (displayingMenuWindowEnum) {
            
            case DisplayingChat -> uiViewProperties.put(UIViewConstants.PanelSelectedChild.toString(), String.valueOf(menu.getDisplayingChatMenuIndex()));
            
            case DisplayingSchedule -> uiViewProperties.put(UIViewConstants.PanelSelectedChild.toString(), menu.getSchedule().getDisplayingSchedule().toString());
        }        
    }
    
    private void isMenuFull () {
        uiViewProperties.put(DashboardViewConstants.IsMenuFull.toString(), String.valueOf(menu.isMenuFull()));
    }
    
    private void isChatMenuShowing () {
        
        if (!menu.isMenuFull())
            return;
        uiViewProperties.put(DashboardViewConstants.IsShowingChats.toString(), String.valueOf(menu.getMenuItems()[1].isMenuShow()));
    }    
}
