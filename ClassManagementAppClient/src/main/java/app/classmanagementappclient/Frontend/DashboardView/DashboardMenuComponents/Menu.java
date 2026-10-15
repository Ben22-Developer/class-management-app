package app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents;

import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector.BackendToMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.ChatMenu.ChatSubMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.EventHandler.ChatCreateEventHandler;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.ContactUsPanel;
import app.classmanagementappclient.Frontend.DashboardView.ContactUs.Load.LoadContactUsPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.menuEnum.DisplayingMenuWindowEnum;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.mode.LightDarkMode;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.mode.ToolBarAccentColor;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.DashboardPanel;
import app.classmanagementappclient.Frontend.DashboardView.DashboardStatistics_TotalPanel.DashboardPanel.DashboardPanelUtility;
import app.classmanagementappclient.Frontend.DashboardView.LoadDashboardViewMenu.CreateMenu;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.ScheduleUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Raven
 */
public class Menu extends JPanel {
    
    private final MenuItem[] menuItems;
    
    private boolean menuFull = true;
    private final String headerName = "Class Management";

    protected final boolean hideMenuTitleOnMinimum = true;
    protected final int menuTitleLeftInset = 5;
    protected final int menuTitleVgap = 5;
    protected final int menuMaxWidth = 250;
    protected final int menuMinWidth = 60;
    protected final int headerFullHgap = 5;
    private final JPanel panelBody;    

    private DashboardPanel dashboardPanel;
    
    private Schedule schedule;
    
    private final List<ChatSubMenuUtility> chatMenuItemUtilities = new ArrayList<>();
    private int displayingChatMenuIndex = -1;

    private ContactUsPanel contactUsPanel;
    
    
    private final Map<String, List<String>> menuItemsMap = new LinkedHashMap<>();
    
    private final JLabel connectionLabel;
    
    private DisplayingMenuWindowEnum displayingWindow;
    

    
    public Menu(JPanel panelBody, JLabel connectionLabel) {
        
        menuItemsMap.put("~MAIN~", null);
        menuItemsMap.put("Dashboard", null); // while changing this map remember to change the MenuItems static array, also check how the changes impact Save and UI Load files
        menuItemsMap.put("~WEB APP~", null);
        menuItemsMap.put("Chat", BackendToMenuUtility.getAllChatNames());
        menuItemsMap.put("Schedule", null);
        menuItemsMap.put("Contact Us", null);
        menuItemsMap.put("~OTHER~", null);
        menuItemsMap.put("Help", null);
        this.panelBody = panelBody;
        this.connectionLabel = connectionLabel;
        menuItems = new MenuItem[5]; // 6 menu items
        init();
    }    
    
    public boolean isMenuFull() {
        return menuFull;
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        if (menuFull) {
            header.setText(headerName);
            header.setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? JLabel.LEFT : JLabel.RIGHT);
        } else {
            header.setText("");
            header.setHorizontalAlignment(JLabel.CENTER);
        }
        
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem menuItem) {
                menuItem.setFull(menuFull);
            }
        }
        lightDarkMode.setMenuFull(menuFull);
        toolBarAccentColor.setMenuFull(menuFull);
    }

    private void init() {
        setLayout(new MenuLayout());
        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:20,2,2,2;"
                + "background:$Menu.background;"
                + "arc:10");
        header = new JLabel(headerName);
        header.setIcon(new ImageIcon(FrontEndUtility.menuLogoIconPath));
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$Menu.header.font;"
                + "foreground:$Menu.foreground");

        //  Menu
        scroll = new JScrollPane();
        panelMenu = new JPanel(new MenuItemLayout(this));
        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5;"
                + "background:$Menu.background");

        scroll.setViewportView(panelMenu);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:null");
        JScrollBar vscroll = scroll.getVerticalScrollBar();
        vscroll.setUnitIncrement(10);
//        vscroll.putClientProperty(FlatClientProperties.STYLE, ""
//                + "width:$Menu.scroll.width;"
//                + "trackInsets:$Menu.scroll.trackInsets;"
//                + "thumbInsets:$Menu.scroll.thumbInsets;"
//                + "background:$Menu.ScrollBar.background;"
//                + "thumb:$Menu.ScrollBar.thumb");
        
        new CreateMenu(this).createMenu();
        
        //dark & light mode
        lightDarkMode = new LightDarkMode();
        
        toolBarAccentColor = new ToolBarAccentColor(this);
        toolBarAccentColor.setVisible(FlatUIUtils.getUIBoolean("AccentControl.show", false));
        add(header);
        add(scroll);
        add(lightDarkMode);
        add(toolBarAccentColor);
    }

    public void addActionListener (String menuName, MenuItem item) {
        
        switch (menuName) // consider Load Dashboard Panel files if u want to change or add a new menu
        {
            case "Dashboard" -> dashboardPanelDisplay();
            
            case "Schedule" -> schedulePanelDisplay();
            
            case "Chat" -> chatPanelDisplay(item);
            
            case "Contact Us" -> contactUsPanelDisplay();
            
            case "Help" -> System.out.println("help window");
            
            default -> throw new AssertionError();
        } 
    }

    
    private void dashboardPanelDisplay () {
        
        if (dashboardPanel == null)
            setDashboardPanel();
        
        setDisplayingWindow(DisplayingMenuWindowEnum.DisplayingDashboard);
        displayPanel(dashboardPanel);
    }

    private void schedulePanelDisplay () {
        
        if (schedule == null)
            setSchedulePanel();
        
        setDisplayingWindow(DisplayingMenuWindowEnum.DisplayingSchedule);
        displayPanel(schedule);
    }
    
    private void chatPanelDisplay (MenuItem item) {
        
        setDisplayingWindow(DisplayingMenuWindowEnum.DisplayingChat);
        new ChatCreateEventHandler(this).chatPanelHandler(item);
    }
    
    private void contactUsPanelDisplay () {
        
        if (contactUsPanel == null)
            setContactUsPanel ();
        
        setDisplayingWindow(DisplayingMenuWindowEnum.DisplayingContactUs);
        displayPanel(contactUsPanel);
    }
    
    public void displayPanel (JPanel panel) {
        panelBodyRemoveAll();
        panelBody.add(panel);
        FrontEndUtility.updateUINoSafety();
        FrontEndUtility.refresh(panelBody);
    }
    
    public void panelBodyRemoveAll () {
        panelBody.removeAll();
    }

    public void hideMenuItem() {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).hideMenuItem();
            }
        }
        revalidate();
    }
    
    public void addChatMenuUtility (ChatSubMenuUtility utility) {
        chatMenuItemUtilities.add(utility);
    }
    
    public void removeChatMenuUtility (ChatSubMenuUtility utility) {
        chatMenuItemUtilities.remove(utility);
    } 
    
    public void updateChatMenuUtilityIndex (int deletedIndex) {
        for (int i = deletedIndex; i < chatMenuItemUtilities.size(); i++)
            chatMenuItemUtilities.get(i).setIndex(i);
    }

    public boolean isHideMenuTitleOnMinimum() {
        return hideMenuTitleOnMinimum;
    }

    public int getMenuTitleLeftInset() {
        return menuTitleLeftInset;
    }

    public int getMenuTitleVgap() {
        return menuTitleVgap;
    }

    public int getMenuMaxWidth() {
        return menuMaxWidth;
    }

    public int getMenuMinWidth() {
        return menuMinWidth;
    }

    public Map<String, List<String>> getMenuItemsMap() {
        return menuItemsMap;
    }

    public List<ChatSubMenuUtility> getChatMenuItemUtilities() {
        return chatMenuItemUtilities;
    }

    private void setDashboardPanel () {
        dashboardPanel = (DashboardPanel) DashboardPanelUtility.loadDashboardPanel();
    }    
    
    private void setSchedulePanel () {
        schedule = ScheduleUtility.createSchedule(connectionLabel);
    }
    
    private void setContactUsPanel () {
        contactUsPanel = LoadContactUsPanel.loadContactUsPanel(connectionLabel);
    }    

    public DisplayingMenuWindowEnum getDisplayingMenuEnum() {
        return displayingWindow;
    }

    public void setDisplayingWindow(DisplayingMenuWindowEnum displayingWindow) {
        this.displayingWindow = displayingWindow;
    }

    public JPanel getPanelMenu() {
        return panelMenu;
    }

    public int getDisplayingChatMenuIndex() {
        return displayingChatMenuIndex;
    }

    public void setDisplayingChatMenuIndex(int displayingChatMenuIndex) {
        this.displayingChatMenuIndex = displayingChatMenuIndex;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public JLabel getConnectionLabel() {
        return connectionLabel;
    }
    
    public void addMenuItem (MenuItem menuItem, int index) {
        menuItems[index] = menuItem;
    }

    public MenuItem[] getMenuItems() {
        return menuItems;
    }
    
    private JLabel header;
    private JScrollPane scroll;
    private JPanel panelMenu;
    private LightDarkMode lightDarkMode;
    private ToolBarAccentColor toolBarAccentColor;

    private class MenuLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int gap = UIScale.scale(5);
                int sheaderFullHgap = UIScale.scale(headerFullHgap);
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int iconWidth = width;
                int iconHeight = header.getPreferredSize().height;
                int hgap = menuFull ? sheaderFullHgap : 0;
                int accentColorHeight = 0;
                if (toolBarAccentColor.isVisible()) {
                    accentColorHeight = toolBarAccentColor.getPreferredSize().height+gap;
                }

                header.setBounds(x + hgap, y, iconWidth - (hgap * 2), iconHeight);
                int ldgap = UIScale.scale(10);
                int ldWidth = width - ldgap * 2;
                int ldHeight = lightDarkMode.getPreferredSize().height;
                int ldx = x + ldgap;
                int ldy = y + height - ldHeight - ldgap  - accentColorHeight;

                int menux = x;
                int menuy = y + iconHeight + gap;
                int menuWidth = width;
                int menuHeight = height - (iconHeight + gap) - (ldHeight + ldgap * 2) - (accentColorHeight);
                scroll.setBounds(menux, menuy, menuWidth, menuHeight);

                lightDarkMode.setBounds(ldx, ldy, ldWidth, ldHeight);

                if (toolBarAccentColor.isVisible()) {
                    int tbheight = toolBarAccentColor.getPreferredSize().height;
                    int tbwidth = Math.min(toolBarAccentColor.getPreferredSize().width, ldWidth);
                    int tby = y + height - tbheight - ldgap;
                    int tbx = ldx + ((ldWidth - tbwidth) / 2);
                    toolBarAccentColor.setBounds(tbx, tby, tbwidth, tbheight);
                }
            }
        }
    }
}
