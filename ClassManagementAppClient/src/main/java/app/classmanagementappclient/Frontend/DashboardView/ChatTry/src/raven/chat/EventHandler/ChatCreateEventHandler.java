/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.EventHandler;

import app.classmanagementappclient.Frontend.Common.AddNewThingPanel.AddPanel;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.BackendConnector.BackendToMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.ChatMenu.ChatSubMenuUtility;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ChatCreateEventHandler {

    
    private final Menu menu;

    public ChatCreateEventHandler(Menu menu) {
        this.menu = menu;
    }
    
    public void chatPanelHandler (MenuItem item) {
        
        if (item.areSubMenusAvailable()) 
            item.showSubMenus();
        else
            createNewChatPanel(item);
    }     
    
    private void createNewChatPanel (MenuItem item) {
        
        var addChatPanel = new AddPanel();
        JButton btn = addChatPanel.getNewBtn();
        btn.setText("Add New Chat");
        btn.addActionListener(l -> {
            createNewChat(item);
        });
        menu.displayPanel(addChatPanel);
    }
    
    public void createNewChat (MenuItem item) {
        
        String chatName = "Chat "+ (LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).replaceAll("[-:.]", " ") ;
        

        if (!createNewChatCredentials(chatName))
            return;
        
        List<String> chats = menu.getMenuItemsMap().get("Chat");
        int index = chats.size();
        createNewChatSubBranch(chats, chatName);
        ChatSubMenuUtility chatMenuItemUtility = createNewChatMenuItemUtility(item, index);
        item.createSubMenuItem_Event(chats.getLast());
        item.createPopup(chats.getLast());
        item.refresh();
        menu.displayPanel(chatMenuItemUtility.getChatAreaPanel());
    }
    
    private boolean createNewChatCredentials (String chatName) {
        return BackendToMenuUtility.insertNewChatCredentials(chatName);
    }
    
    private void createNewChatSubBranch (List<String> chatSubBranch, String chatName) {        
        chatSubBranch.add(chatName);
    }
    
    public ChatSubMenuUtility createNewChatMenuItemUtility (MenuItem item, int index) {
        
        var chatMenuItemUtility = new ChatSubMenuUtility(item, index, menu.getConnectionLabel());
        menu.addChatMenuUtility(chatMenuItemUtility);
        return chatMenuItemUtility;
    }    
}
