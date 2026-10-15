/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel.ChatPanel;

import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.DashboardViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class ChatPanelLoad {
    
    private final MenuItem chatMenuItem;
    private final Menu menu;
    private final Properties uiViewProperties;

    public ChatPanelLoad(MenuItem chatMenuItem, Menu menu, Properties uiViewProperties) {
        this.chatMenuItem = chatMenuItem;
        this.menu = menu;
        this.uiViewProperties = uiViewProperties;
    }
    
    public void run () {
        
        isChatMenuShowing();
        panelSelectedChild();
    }
    
    private void isChatMenuShowing () {
        
        if (!menu.isMenuFull())
            return;
        
        boolean isShowing = Boolean.parseBoolean( String.valueOf( uiViewProperties.get(DashboardViewConstants.IsShowingChats.toString()) ));
        
        if (isShowing)
            menu.addActionListener("Chat", chatMenuItem);
    }
    
    private void panelSelectedChild () {
        
        Object selectedChild = uiViewProperties.get(UIViewConstants.PanelSelectedChild.toString());
        
        if (selectedChild == null)
            return;
        
        int selectedChildInt = Integer.parseInt(selectedChild.toString());
        
        menu.getChatMenuItemUtilities().get(selectedChildInt).display();
    }
}
