package app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents;

import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.EventHandler.ChatCreateEventHandler;
import app.classmanagementappclient.Frontend.Exception.SubMenuCollapsedException;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Raven
 */
public class MenuItem extends JPanel {

    public boolean isMenuShow() {
        return menuShow;
    }

    public void setMenuShow(boolean menuShow) {
        this.menuShow = menuShow;
    }

    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    public List<String> getSubMenusList() {
        return subMenusList;
    }
    
    public String getSubMenu (int index) {
        return subMenusList.get(index);
    }
    
    public void updateSubMenuNameList (String newSubMenuName, int index) {
        subMenusList.set(index, newSubMenuName);
    }
    
    public void deleteSubMenu (int index) {
        subMenusList.remove(index);
    }    
    
    public void updateSubMenuNameUIComponent (String oldSubMenuName, String newSubMenuName) {
        
        if (!menu.isMenuFull())
            return;    
        
        JButton button = getUIComponent(oldSubMenuName);
        button.setText(newSubMenuName);
    }
    
    public void deleteUIComponent (String subMenuName) {
        
        for (Component component : getComponents()) {
            if (component instanceof JButton button)
                System.out.println("--> "+button.getText());
        }
        
        if (!menu.isMenuFull())
            throw new SubMenuCollapsedException();
        
        JButton button = getUIComponent(subMenuName);
        remove(button);
    }
    
    public void deleteUIComponent (int index) {
        JButton hiddenSubMenu = hiddenSubMenuBtns.remove(index);
        remove(hiddenSubMenu);
    }
    
    private JButton getUIComponent (String componentTxt) {
        
        Component[]components = getComponents();
        
        for (Component component : components) {
            if (component instanceof JButton button && button.getText().equals(componentTxt))
                return button;
        }
        throw new AssertionError("Unknown UI component at MenuItem.getUIComponent");
    }
    
    

    public int getMenuIndex() {
        return menuIndex;
    }
    
    public boolean areSubMenusAvailable () {
        return !subMenusList.isEmpty();
    }

    public PopupSubmenu getPopup() {
        return popup;
    }

    public Menu getMenu() {
        return menu;
    }
    
    private final Menu menu;
    private final String menuName; 
    private final List<String> subMenusList;
    private final int menuIndex;
    private final int menuItemHeight = 38;
    private final int subMenuItemHeight = 35;
    private final int subMenuLeftGap = 34;
    private final int firstGap = 5;
    private final int bottomGap = 5;
    private boolean menuShow;
    private float animate;
    
    private final List<JButton> hiddenSubMenuBtns = new ArrayList<>();

    private PopupSubmenu popup;

    public MenuItem(Menu menu, String menuName, List<String> subMenusList, int menuIndex) {        
        this.menu = menu;
        this.menuName = menuName;
        this.subMenusList = subMenusList;
        this.menuIndex = menuIndex;
        init();
    }

    private Icon getIcon() {
        FlatSVGIcon icon = new FlatSVGIcon(FrontEndUtility.menuSVGIconPath +menuIndex + ".svg");
        FlatSVGIcon.ColorFilter f = new FlatSVGIcon.ColorFilter();
        icon.setColorFilter(f);
        return icon;
    }

    private void init() {
        
        setLayout(new MenuLayout());
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;"
                + "foreground:$Menu.lineColor");
        
        
        createMenuItem_Event();
        
        menu.addMenuItem(this, menuIndex);
        
        if (subMenusList == null)
            return;
        
        if (areSubMenusAvailable())
            createSubMenuItem_Event();

        popup = new PopupSubmenu(getComponentOrientation(), this, menuIndex, subMenusList);
    }
    
    private void createMenuItem_Event () {
        
        JButton menuItem = createButtonItem(menuName);
        menuItem.setHorizontalAlignment(menuItem.getComponentOrientation().isLeftToRight() ? JButton.LEADING : JButton.TRAILING);
        menuItem.setIcon(getIcon());
        
        
        menuItem.addActionListener(l -> {
            menu.addActionListener(menuName, this);
        });
        add(menuItem);
    }    
    
    public void showSubMenus() {
        
        if (menu.isMenuFull()) {
            MenuAnimation.animate(MenuItem.this, !menuShow);
        } else {
            popup.show(this, (int) getWidth() + UIScale.scale(5), UIScale.scale(menuItemHeight) / 2);
        }      
    }
    
    private void createSubMenuItem_Event () { // these are designed for chatting sub menus when the app starts

        for (int i = 0; i < subMenusList.size(); i++) {
            
            createSubMenuItem_Event(subMenusList.get(i));     
            
            switch (menuName) {
                case "Chat" -> {
                    new ChatCreateEventHandler(menu).createNewChatMenuItemUtility(this, i);
                }
                default -> throw new AssertionError();
            }
        }
    }
    
    public void createSubMenuItem_Event (String subMenu) { // public because at run time we need to add other submenu dynamically
        
        JButton menuItem = createButtonItem(subMenu);
        menuItem.setHorizontalAlignment(menuItem.getComponentOrientation().isLeftToRight() ? JButton.LEADING : JButton.TRAILING);

        menuItem.addActionListener(l -> {
            // create a function to handle the chat loading, provide a boolean to cacth if loaded or not
            subMenuEventHandler((JButton) l.getSource());
        });
        add(menuItem);        
    }
    
    public void subMenuEventHandler (JButton src) {
        SwingUtilities.invokeLater(() -> {
            int subMenuIndex = getSubMenuIndex(src.getText());
            displaySubMenuPanel(subMenuIndex);
        });
    }
    
    private int getSubMenuIndex (String subMenu) {
        return subMenusList.indexOf(subMenu);
    }
    
    private void displaySubMenuPanel (int subMenuIndex) {
        
        switch (menuIndex) {
            case 1 -> displayAIBotChat(subMenuIndex);
            default -> throw new AssertionError("Unknown menu at MenuItem.displaySubMenuPanel");
        }
    }
    
    private void displayAIBotChat (int subMenuIndex) {
        menu.getChatMenuItemUtilities().get(subMenuIndex).display();
    }
    
    public void createPopup (String subMenu) {
        popup.addPopup(subMenu);
    }
        
    
    public void refresh () {
        revalidate();
        repaint();
    }

    protected void setSelectedIndex(int index) {
        int size = getComponentCount();
        boolean selected = false;
        for (int i = 0; i < size; i++) {
            Component com = getComponent(i);
            if (com instanceof JButton) {
                ((JButton) com).setSelected(i == index);
                if (i == index) {
                    selected = true;
                }
            }
        }
        ((JButton) getComponent(0)).setSelected(selected);
        popup.setSelectedIndex(index);
    }

    private JButton createButtonItem(String text) {
        JButton button = new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background;"
                + "foreground:$Menu.foreground;"
                + "selectedBackground:$Menu.button.selectedBackground;"
                + "selectedForeground:$Menu.button.selectedForeground;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "arc:10;"
                + "iconTextGap:10;"
                + "margin:3,11,3,11");
        return button;
    }

    public void hideMenuItem() {
        animate = 0;
        menuShow = false;
    }

    public void setFull(boolean full) {
        if (full) {
            int size = getComponentCount();
            for (int i = 0; i < size; i++) {
                Component com = getComponent(i);
                if (com instanceof JButton) {
                    JButton button = (JButton) com;
                    button.setText(i == 0 ? menuName : subMenusList.get(i-1));
                    button.setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? JButton.LEFT : JButton.RIGHT);
                }
            }
         hiddenSubMenuBtns.clear();
        } 
        else {
            int index = 0;
            
            for (Component com : getComponents()) {
                
                if (com instanceof JButton button) {
                    button.setText("");
                    button.setHorizontalAlignment(JButton.CENTER);
                    
                    if (index > 0) // entered in submenu flow 
                        hiddenSubMenuBtns.add(button);
                }
                index ++;
            }
            animate = 0f;
            menuShow = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (animate > 0) {
            int ssubMenuItemHeight = UIScale.scale(subMenuItemHeight);
            int ssubMenuLeftGap = UIScale.scale(subMenuLeftGap);
            int smenuItemHeight = UIScale.scale(menuItemHeight);
            int sfirstGap = UIScale.scale(firstGap);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Path2D.Double p = new Path2D.Double();
            int last = getComponent(getComponentCount() - 1).getY() + (ssubMenuItemHeight / 2);
            boolean ltr = getComponentOrientation().isLeftToRight();
            int round = UIScale.scale(10);
            int x = ltr ? (ssubMenuLeftGap - round) : (getWidth() - (ssubMenuLeftGap - round));
            p.moveTo(x, smenuItemHeight + sfirstGap);
            p.lineTo(x, last - round);
            for (int i = 1; i < getComponentCount(); i++) {
                int com = getComponent(i).getY() + (ssubMenuItemHeight / 2);
                p.append(createCurve(round, x, com, ltr), false);
            }
            g2.setColor(getForeground());
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setStroke(new BasicStroke(UIScale.scale(1f)));
            g2.draw(p);
            g2.dispose();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (subMenusList != null && areSubMenusAvailable()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setColor(FlatUIUtils.getUIColor("Menu.arrowColor", getForeground()));
            int smenuItemHeight = UIScale.scale(menuItemHeight);
            boolean ltr = getComponentOrientation().isLeftToRight();
            g2.setStroke(new BasicStroke(UIScale.scale(1f)));
            if (menu.isMenuFull()) {
                int arrowWidth = UIScale.scale(10);
                int arrowHeight = UIScale.scale(5);
                int ax = ltr ? (getWidth() - arrowWidth * 2) : arrowWidth;
                int ay = (smenuItemHeight - arrowHeight) / 2;
                Path2D p = new Path2D.Double();
                p.moveTo(0, animate * arrowHeight);
                p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
                p.lineTo(arrowWidth, animate * arrowHeight);
                g2.translate(ax, ay);
                g2.draw(p);
            } else {
                int arrowWidth = UIScale.scale(4);
                int arrowHeight = UIScale.scale(8);
                int ax = ltr ? (getWidth() - arrowWidth - UIScale.scale(3)) : UIScale.scale(3);
                int ay = (smenuItemHeight - arrowHeight) / 2;
                Path2D p = new Path2D.Double();
                if (ltr) {
                    p.moveTo(0, 0);
                    p.lineTo(arrowWidth, arrowHeight / 2);
                    p.lineTo(0, arrowHeight);
                } else {
                    p.moveTo(arrowWidth, 0);
                    p.lineTo(0, arrowHeight / 2);
                    p.lineTo(arrowWidth, arrowHeight);
                }
                g2.translate(ax, ay);
                g2.draw(p);
            }
            g2.dispose();
        }
    }

    private Shape createCurve(int round, int x, int y, boolean ltr) {
        Path2D p2 = new Path2D.Double();
        p2.moveTo(x, y - round);
        p2.curveTo(x, y - round, x, y, x + (ltr ? round : -round), y);
        return p2;
    }

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
                Insets inset = parent.getInsets();
                int width = parent.getWidth();
                int height = inset.top + inset.bottom;
                int size = parent.getComponentCount();
                Component item = parent.getComponent(0);
                height += UIScale.scale(menuItemHeight);
                if (item.isVisible()) {
                    int subMenuHeight = size > 1 ? UIScale.scale(firstGap) + UIScale.scale(bottomGap) : 0;
                    for (int i = 1; i < size; i++) {
                        Component com = parent.getComponent(i);
                        if (com.isVisible()) {
                            subMenuHeight += UIScale.scale(subMenuItemHeight);
                        }
                    }
                    height += (subMenuHeight * animate);
                } else {
                    height = 0;
                }
                return new Dimension(width, height);
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
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int size = parent.getComponentCount();
                for (int i = 0; i < size; i++) {
                    Component com = parent.getComponent(i);
                    if (com.isVisible()) {
                        if (i == 0) {
                            int smenuItemHeight = UIScale.scale(menuItemHeight);
                            int sfirstGap = UIScale.scale(firstGap);
                            com.setBounds(x, y, width, smenuItemHeight);
                            y += smenuItemHeight + sfirstGap;
                        } else {
                            int ssubMenuLeftGap = UIScale.scale(subMenuLeftGap);
                            int subMenuX = ltr ? ssubMenuLeftGap : 0;
                            int ssubMenuItemHeight = UIScale.scale(subMenuItemHeight);
                            com.setBounds(x + subMenuX, y, width - ssubMenuLeftGap, ssubMenuItemHeight);
                            y += ssubMenuItemHeight;
                        }
                    }
                }
            }
        }
    }
}
