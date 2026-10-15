package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.UIDataInput_Insert;

import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author BEN
 */
public class TreeNodeUIInsert 
{
    
    public int addNewNode (JTree jt, DefaultMutableTreeNode parent, DefaultMutableTreeNode newNode, boolean ...dontDisplayMsg) 
    {
        
        ArrayList<TreeNode> children = Collections.list( parent.children() );
        
        Comparator<TreeNode> comparator = TreeWindowUtility.getTreeNodeComparator();
        
        boolean isUnique;
        if (dontDisplayMsg.length == 0)
            isUnique = (children.isEmpty()) ? true : TreeWindowUtility.checkNodeUniqueness(children, newNode, "You are trying to add already\nexisting data", comparator);
        else
            isUnique = (children.isEmpty()) ? true : TreeWindowUtility.checkNodeUniqueness(children, newNode, "You are trying to add already\nexisting data", comparator, dontDisplayMsg[0]);
        
        if (!isUnique) 
            return TreeWindowUtility.DUPLICATE_DATA; 
        
        children.add(newNode);
        int index = TreeWindowUtility.getNodeIndex(children, newNode, comparator);
        
        insertNewNode(jt, parent, newNode, index);
        jt.scrollPathToVisible(new TreePath(newNode.getPath()));
        
        return TreeWindowUtility.SUCCESSFUL_ADDED;        
    }
    
    public void insertNewNode (JTree jTree, DefaultMutableTreeNode parent, DefaultMutableTreeNode newNode, int index) {
        DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
        model.insertNodeInto(newNode, parent, index);
    }
}
