package app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel;

import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.menuEnum.DisplayingMenuWindowEnum;
import app.classmanagementappclient.Frontend.DashboardView.form.MainForm;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.DashboardViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel.ChatPanel.ChatPanelLoad;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel.SchedulePanel.SchedulePanelLoad;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class DashboardPanelLoad {
    
    private MainForm dashboardForm; 
    private Menu menu;
    private MenuItem[] menuItems;
    
    private final MainWindow mainWindow;
    
    private final Properties uiViewProperties;

    public DashboardPanelLoad (MainWindow mainWindow, Properties uiViewProperties) {
        this.mainWindow = mainWindow;
        this.uiViewProperties = uiViewProperties;
    }    
    
    public void run () {
        
        init();
        isMenuFull();
        String childPanel = childPanel();
        
        if (childPanel.isBlank())
            return;
        
        childPanelManipulate(childPanel);
        
    }
    
    private void init () {
        this.dashboardForm = mainWindow.getDashboard();
        this.menu = this.dashboardForm.getMenu();
        this.menuItems = this.menu.getMenuItems();        
    }
    
    private void isMenuFull () {
        
        String key = DashboardViewConstants.IsMenuFull.toString();
        boolean isFull = Boolean.parseBoolean( String.valueOf( uiViewProperties.getProperty(key)) );
        
        if (!isFull)
            dashboardForm.setMenuFull(isFull);
    }
    
    private String childPanel () {
        
        Object value = uiViewProperties.get(UIViewConstants.PanelChild.toString());
        
        return value == null ? "" :  String.valueOf( value );
    }
    
    private void childPanelManipulate (String childPanel) {
        
        if (childPanel.equals(DisplayingMenuWindowEnum.DisplayingDashboard.toString() ))
            dashboardPanel();

        else if (childPanel.equals(DisplayingMenuWindowEnum.DisplayingChat.toString() ))
            chatPanel();
        
        else if (childPanel.equals(DisplayingMenuWindowEnum.DisplayingSchedule.toString() ))
            schedulePanel();

        else if (childPanel.equals(DisplayingMenuWindowEnum.DisplayingContactUs.toString() ))  
            contactPanel();
        else
            throw new AssertionError("Unknown PanelSelectedChild");
    }
    
    private void dashboardPanel () {
        menu.addActionListener("Dashboard", menuItems[0]);
    }
    
    private void chatPanel () {
        new ChatPanelLoad(menuItems[1], menu, uiViewProperties).run();
    }

    private void schedulePanel () {
        new SchedulePanelLoad(menu, menuItems[2], uiViewProperties).run();
    }
    
    private void contactPanel () {
        menu.addActionListener("Contact Us", menuItems[3]);
    }
}
