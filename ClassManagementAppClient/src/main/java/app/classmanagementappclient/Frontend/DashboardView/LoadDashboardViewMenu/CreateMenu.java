/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.LoadDashboardViewMenu;

import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BEN
 */
public class CreateMenu {
    
    private final JPanel panelMenu;
    private final Map<String, List<String>> menuItemsMap;
    private final Menu menu;

    public CreateMenu(Menu menu) {
        this.panelMenu = menu.getPanelMenu();
        this.menuItemsMap = menu.getMenuItemsMap();
        this.menu = menu;
    }
    
    public void createMenu() {
        
        int index = 0;
        
        for (Map.Entry<String,List<String>> set : menuItemsMap.entrySet()) {
            
            String menuName = set.getKey();
            if (menuName.startsWith("~") && menuName.endsWith("~")) {
                panelMenu.add(createTitle(menuName));
            } 
            else {
                MenuItem menuItem = new MenuItem(menu, menuName, set.getValue(), index);
                panelMenu.add(menuItem);
                index ++;
            }
        }
    }

    private JLabel createTitle(String title) {
        String menuName = title.substring(1, title.length() - 1);
        JLabel lbTitle = new JLabel(menuName);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$Menu.label.font;"
                + "foreground:$Menu.title.foreground");
        return lbTitle;
    }    
}
