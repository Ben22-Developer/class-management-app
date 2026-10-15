package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Edit;

import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TreeNodeUIEdit {
    
    public void jTreeNodeEdit (JTree jTree, String newTreeNodeVal, DefaultMutableTreeNode currTreeNode)
    {
        currTreeNode.setUserObject(newTreeNodeVal);
        sortEditedSiblings(currTreeNode, jTree);
        reUpdateTreePath(jTree);
    }
    
    
    private void sortEditedSiblings (DefaultMutableTreeNode editedNode, JTree jTree)
    {   
        if (!hasSiblings(editedNode))
            return;
        
        
        List<TreeNode> defaultNodesArrangement = (List<TreeNode>) Collections.list(  editedNode.getParent().children() );
        ArrayList<TreeNode> newNodesArrangement = new ArrayList<> ( defaultNodesArrangement.stream().toList() );
        TreeWindowUtility.getSortedTreenodeList(newNodesArrangement);

        int index = canUpdateUI(defaultNodesArrangement, newNodesArrangement);
        
        if (index == -1) {
            return;
        }

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) editedNode.getParent();

        addNodesSortedly(parent, newNodesArrangement, index);
        reload(parent, jTree);
    }
    
    private boolean hasSiblings (DefaultMutableTreeNode defaultMutableTreeNode) {
        return defaultMutableTreeNode.getSiblingCount() > 1;
    }
    
    private int canUpdateUI (List<TreeNode> defaultNodesArrangement, List<TreeNode> newNodeArrangement) {
        int index = -1;
        for (int i = 0; i < defaultNodesArrangement.size(); i++) 
        {
            boolean isEqual = defaultNodesArrangement.get(i).toString().equalsIgnoreCase(newNodeArrangement.get(i).toString());
            if (!isEqual)
                return i;
        }
        return index;
    }
    
    private void addNodesSortedly (DefaultMutableTreeNode parent, ArrayList<TreeNode> newNodesArrangement, int elementAt)
    {
        int count = parent.getChildCount();
        int loopTimes = elementAt;      
        
        while (loopTimes < count) {
            parent.remove(elementAt);
            loopTimes ++;
        }
        
        for (int i = elementAt; i < newNodesArrangement.size(); i++) {
            parent.insert((DefaultMutableTreeNode) newNodesArrangement.get(i), i);
        }
    }
    
    private void reload (DefaultMutableTreeNode parent, JTree jTree) {
        DefaultTreeModel treeModel = (DefaultTreeModel) jTree.getModel();
        treeModel.reload(parent);
    }
    
    private void reUpdateTreePath (JTree jTree) {
        TreeWindowUtility.reUpdateTreePath(jTree);
    }
}
