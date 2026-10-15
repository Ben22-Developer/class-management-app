package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class TreeNodeUIDelete 
{
    public void deleteTreeNode (JTree jTree) {
        
       int userChoice = FrontEndUtility.showConfirmDialog("This action will delete all underlying folders and files!\nPress 'Ok' to confirm", "Delete Confirmation", JOptionPane.WARNING_MESSAGE);
       
       if (userChoice == 0) 
           deleteConfirmed(jTree);  
    }
    
    public void deleteConfirmed (JTree jTree) 
    {
        DefaultTreeModel treeModel = (DefaultTreeModel) jTree.getModel();   
        Set <TreeNode> toDeleteNodes = new HashSet<>();
        setDeleteList(jTree, toDeleteNodes);
        
        Iterator<TreeNode> toDeleteNodesIterator = toDeleteNodes.iterator();
        
        while (toDeleteNodesIterator.hasNext()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) toDeleteNodesIterator.next();
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
            deleteFromParent(jTree, treeModel, parent, node);
        }
    }
    
    private void setDeleteList (JTree jt, Set <TreeNode> toDeleteNodes) 
    {
        TreePath[] treePaths = jt.getSelectionPaths();
        if (treePaths.length == 1) {
            toDeleteNodes.add ((TreeNode) treePaths[0].getLastPathComponent());
            return;
        }
        TreeWindowUtility.setMultiSelectDSA(jt, treePaths, toDeleteNodes);
    }   
    
    public void deleteFromParent (JTree jTree, DefaultTreeModel treeModel, DefaultMutableTreeNode parent, DefaultMutableTreeNode node) {
        treeModel.removeNodeFromParent(node);
        treeModel.reload(parent);
        TreeWindowUtility.reUpdateTreePath(jTree);        
    }
}
