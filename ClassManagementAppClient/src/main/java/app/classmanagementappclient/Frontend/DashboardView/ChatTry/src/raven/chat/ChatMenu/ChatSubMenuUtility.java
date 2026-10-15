package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.ChatMenu;

import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector.BackendToMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.ChatPanels.ChatAreaPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.EventHandler.ChatCreateEventHandler;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.component.ChatArea;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;
import app.classmanagementappclient.Frontend.Exception.SubMenuCollapsedException;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ChatSubMenuUtility {

    private final ChatArea chatArea;
    private final ChatAreaPanel chatAreaPanel;
    private final MenuItem menuItem;
    private String chatName;
    private int index; 

    public ChatSubMenuUtility(MenuItem menuItem, int index, JLabel connectionLabel) {
        
        this.menuItem = menuItem;
        this.index = index;
        this.chatName = menuItem.getSubMenusList().get(index); // name of chat sub menu
        
        chatArea = new ChatArea(teacher_BotChats(), connectionLabel);
        chatAreaPanel = new ChatAreaPanel(this, chatArea);        
        otherInit();
    }
    
    private List<List<Object>> teacher_BotChats () {
        return BackendToMenuUtility.teacher_BotChats(chatName);
    }
    
    private void otherInit () {
        updateUITitle();
    }
    
    // updating the chat title as well as the name of the chat to custom user needs
    public void updateChatTitle () {
        
        String newSubMenuName = FrontEndUtility.getUserInput("New chat title name","New Chat Name");
        
        if (newSubMenuName == null || newSubMenuName.isBlank())
            return;
        
        if (!BackendToMenuUtility.updateChatName(chatName, newSubMenuName))
            return;
        
        
        String oldSubMenuName = menuItem.getSubMenu(index);
        menuItem.updateSubMenuNameList(newSubMenuName, index);
        menuItem.updateSubMenuNameUIComponent(oldSubMenuName, newSubMenuName);
        menuItem.refresh();
        menuItem.getPopup().updatePopupUIComponent(oldSubMenuName, newSubMenuName);
        menuItem.getPopup().refresh();
        updateUITitle();
        setChatName(newSubMenuName);
    }
    
    public void delete () {
        
        int response = FrontEndUtility.showConfirmDialog("Are you sure you want to delete this chat?", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
        
        if (response != 0)
            return;
        
        if (!BackendToMenuUtility.deleteChat( BackendToMenuUtility.getChadIDByChatName(chatName)))
            return;
        
        new SwingWorker<Object, Object> () {
            @Override
            protected Object doInBackground() throws Exception {
                deleteThis();
                return null;
            }
        
        }.execute();
    }
    
    public void deleteThis () {
        
        String subMenuName = menuItem.getSubMenu(index);
        
        //database delete here
        
        menuItem.getMenu().removeChatMenuUtility(this);
        menuItem.getMenu().updateChatMenuUtilityIndex(index);
        
        menuItem.getPopup().deletePopupUIComponent(subMenuName);
        menuItem.getPopup().refresh();
        
        menuItem.deleteSubMenu(index);
        
        try {
            menuItem.deleteUIComponent(subMenuName);
        }catch (SubMenuCollapsedException ex) {
            menuItem.deleteUIComponent(index);
        }
        
        menuItem.refresh();
        menuItem.getMenu().panelBodyRemoveAll();
        FrontEndUtility.refresh( menuItem.getMenu() );      
    }
    
    public void display () {
        
        Menu menu = menuItem.getMenu();
        
        menu.setDisplayingChatMenuIndex(index);
        menu.displayPanel(chatAreaPanel);
    }
    
    private void updateUITitle () {
        chatArea.setTitle(menuItem.getSubMenusList().get(index));
    }
    
    public void newChat () {
        new ChatCreateEventHandler(menuItem.getMenu()).createNewChat(menuItem);
    }
    
    public ChatArea getChatArea() {
        return chatArea;
    }

    public ChatAreaPanel getChatAreaPanel() {
        return chatAreaPanel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }   

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
