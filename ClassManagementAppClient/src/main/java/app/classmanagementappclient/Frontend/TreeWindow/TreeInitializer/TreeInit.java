package app.classmanagementappclient.Frontend.TreeWindow.TreeInitializer;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassWindowUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.Handlers.KeyBoardEventHandler;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.Handlers.MouseEventHandler;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.TreePopUpHandler;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.TransferListener.MyTransferHandler;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete.TreeNodeDeleteMedium;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Edit.TreeDataEditMedium;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.UIDataInput_Insert.JOptionPaneTreeDataInput;
import app.classmanagementappclient.Frontend.TreeWindow.TreeCustomRender.TreeCustomRender;
import app.classmanagementappclient.Frontend.TreeWindow.TreeLoading.TreeLoader;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author BEN
 */
public class TreeInit {
    
    public void init (MainWindow mainWindow) 
    {
        //1. schools (root), 2. school, 3. class, 4. year, 5. term
        
        SwingWorker<Object, JTree> sw = new SwingWorker<Object, JTree>() {
            @Override
            protected Object doInBackground() throws Exception {
//                FrontEndUtility.sleep(10_000);
                JPopupMenu[] popupMenus = { new JPopupMenu(), new JPopupMenu(), new JPopupMenu(), new JPopupMenu(), new JPopupMenu() };
                JPopupMenu multiPopupMenu =  new JPopupMenu();
                JTree jt = loadJTree();
                setJTreeRender(jt);
                setTreeNodesPopupMenus(popupMenus, jt, mainWindow);
                setMultiSelectPopupMenus(jt, multiPopupMenu);
                TreePopUpHandler handler = getTreePopUpHandler(popupMenus, multiPopupMenu);
                addJTreeEventListeners(jt, handler, mainWindow);
                addDrag_Drop(jt);
                mainWindow.setjTree(jt);
                return null;
            }
            
            @Override
            public void done () {
                addJTreeToMainWindowTreePanel(mainWindow);
            }
        };
        sw.execute();
        try {
            sw.get();
        } catch (Exception e) {}
    }
    
    public void addJTreeToMainWindowTreePanel (MainWindow mainWindow) {
        mainWindow.initTreePanel();
        JPanel treePanel = mainWindow.getTreePanel();
        treePanel.add(new JScrollPane(mainWindow.getjTree()), BorderLayout.CENTER);
        FrontEndUtility.updateUI();
        mainWindow.getTreeDisplayMenuItem().setEnabled(false);
        mainWindow.addToSplitPanelLeftSide(treePanel);
    } 
    
    
    private JTree loadJTree () {
        TreeLoader treeLoading = new TreeLoader();
        treeLoading.loadJTree();
        return treeLoading.getjTree();
    }

    private void setJTreeRender (JTree jt) {
        TreeCustomRender cr = new TreeCustomRender();
        jt.setCellRenderer (cr);
    }
    
    private void setTreeNodesPopupMenus (JPopupMenu[] popupMenus, JTree jt, MainWindow mainWindow) 
    { 
        JMenuItem[][] allItems = 
        {
            { new JMenuItem("New School") }, 
            { new JMenuItem("New Class"), new JMenuItem("Edit"), new JMenuItem("Delete")},
            { new JMenuItem("New Year"), new JMenuItem("Edit"), new JMenuItem("Delete")},
            { new JMenuItem("New Term"), new JMenuItem("Edit"), new JMenuItem("Delete")},
            { new JMenuItem("New Lesson"), new JMenuItem("Edit"), new JMenuItem("Delete"),  new JMenuItem("Show")}
        };
        
        for (int i = 0; i < popupMenus.length; i++) 
        {
            JMenuItem[] items = allItems[i];
            for (int j = 0; j < items.length; j++) 
            {
                items[j].setFont(new Font("Segoe UI 18 Plain", Font.PLAIN, 14));
                popupMenus[i].add(items[j]);
                
                setJMenuItemEventListeners(items[j], jt, j, mainWindow);
            }
        }
    }
    
    private void setMultiSelectPopupMenus (JTree jTree, JPopupMenu popupMenu) 
    {
        JMenuItem menuItem = new JMenuItem("Delete Selected") ;
        menuItem.setFont(new Font("Segoe UI 18 Plain", Font.PLAIN, 14));
        setDeleteJTreeNodesEvent(jTree, menuItem);
        popupMenu.add(menuItem);
    }    
    
    private void setJMenuItemEventListeners (JMenuItem menuItem, JTree jt, int menuIndexType, MainWindow mainWindow)
    {
        
        switch (menuIndexType)
        {
            case 0 -> { // new
                addJMenuItemsEvent(menuItem, jt);
            }
            case 1 -> { // edit
                editJMenuItemsEvent(menuItem, jt);
            }            
            case 2 -> { // delete
                setDeleteJTreeNodesEvent(jt, menuItem);
            }      
            case 3 -> {
                showClass(mainWindow, menuItem);
            }
        }
    }   
    
    private void addJMenuItemsEvent (JMenuItem menuItem, JTree jTree) 
    {
        menuItem.addActionListener((ActionEvent e) -> {
            JOptionPaneTreeDataInput insertion = new JOptionPaneTreeDataInput();
            insertion.insert(jTree);
        });
    }
    
    private void editJMenuItemsEvent (JMenuItem menuItem, JTree jTree) 
    {
        menuItem.addActionListener((ActionEvent e) -> {
            TreeDataEditMedium treeEditDialog = new TreeDataEditMedium(jTree);
            treeEditDialog.drawJDialog();
        });
    }
    
    private void setDeleteJTreeNodesEvent (JTree jTree, JMenuItem jMenuItem) {
        
        jMenuItem.addActionListener((ActionEvent l) -> {
            TreeNodeDeleteMedium deleteMedium = new TreeNodeDeleteMedium(jTree);
            deleteMedium.delete();
        });
    }    
    
    private void showClass (MainWindow mainWindow, JMenuItem jMenuItem) {
        
        jMenuItem.addActionListener((l) -> {
            ClassWindowUtility.showClass(mainWindow);
        });
        
    }
    
    private TreePopUpHandler getTreePopUpHandler (JPopupMenu[] popupMenus, JPopupMenu multiPopupMenu) {
        TreePopUpHandler treePopUpHandler = new TreePopUpHandler();
        treePopUpHandler.setPopupMenus(popupMenus);
        treePopUpHandler.setMultiPopupMenus(multiPopupMenu);       
        return treePopUpHandler;
    }
    
    private void addJTreeEventListeners (JTree jTree, TreePopUpHandler handler, MainWindow mainWindow) {
        jTree.addTreeExpansionListener(TreeWindowUtility.getPathHandler());
        MouseEventHandler listener = new MouseEventHandler(handler, mainWindow);
        jTree.addMouseListener(listener);
        addJTreeKeyBoardEvents(jTree, handler, mainWindow);
    }
    
    private void addJTreeKeyBoardEvents (JTree jTree, TreePopUpHandler handler,MainWindow mainWindow)
    {   
        jTree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased (KeyEvent e) {
                
                if (e.getKeyCode() == 113) 
                    new TreeDataEditMedium(jTree).drawJDialog();
                
                if (e.getKeyCode() == 525) 
                    new KeyBoardEventHandler(handler).keyReleased(e);
                
                if (e.getKeyCode() == 127) 
                    new TreeNodeDeleteMedium(jTree).delete();
                
                if (e.getKeyCode() == 10) {
                    if (TreeWindowUtility.getPathHandler().isLeafPressed(jTree))
                        ClassWindowUtility.showClass(mainWindow);
                }
                    
            }
        });
    }
    
    private void addDrag_Drop (JTree jt) {
        jt.setDragEnabled(true);
        jt.setTransferHandler(new MyTransferHandler());
        jt.setOpaque(false);        
        jt.expandRow(0);
    }    
}
