package app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.ExpansionListener;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class JTreeExpansionPathHandler implements TreeExpansionListener
{
    private final Map<TreePath, Boolean> treePathMap  = new ConcurrentHashMap<>();
    private TreePath treePath;
    
    @Override
    public void treeExpanded(TreeExpansionEvent event){
        treePath = event.getPath();
        treePathMap.put(treePath, true);
//        System.out.println("At: Frontend.TreeWindow.JTreeMouseListener.ExpansionListener "+ treePathMap);
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        treePath = event.getPath();
        treePathMap.remove(treePath);
//        System.out.println("At: Frontend.TreeWindow.JTreeMouseListener.ExpansionListener " + treePathMap);
    }
    
    public boolean isLeafPressed (JTree jTree) {
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
        return dmtn.isLeaf();
    }
    
    public void removeTreePath (TreePath path) {
        
        if (treePathMap.containsKey(path))
            treePathMap.remove(path);
    }

    public Map<TreePath, Boolean> getTreePathMap() {
        return treePathMap;
    }
}
