/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Load.TreeView;

import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class LoadTreeView {
    
    private final MainWindow mainWindow;
    
    private boolean shownTree;

    public LoadTreeView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    public void showTree () {
        mainWindow.treeViewActionPerform();
        setShownTree(true);
    }

    public void expandTreePath (List<List<String>> pathList) {
       
        JTree jTree = mainWindow.getjTree();
        
        for (List<String> path : pathList)
            TreeWindowUtility.expandTreePath(jTree, path);
    }

    public boolean hasShownTree() {
        return shownTree;
    }

    public void setShownTree(boolean shownTree) {
        this.shownTree = shownTree;
    }
}
