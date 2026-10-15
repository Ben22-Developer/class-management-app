package app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.TransferListener;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies.InsertClassProxy;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy.InsertClassLessonProxy;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm.Proxies.InsertClassTermProxy;
import app.classmanagementappclient.Medium.ClassYear.InsertClassYear.Proxies.InsertClassYearProxy;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete.TreeNodeDeleteMedium;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete.TreeNodeUIDelete;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.InsertTreeDataMedium;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.UIDataInput_Insert.TreeNodeUIInsert;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BEN
 */
public class MyTransferHandler extends TransferHandler
{
    private DefaultMutableTreeNode draggedTreeNode;
    
    private final List<List<String>> newPathsList = new ArrayList<>();
    private final List<List<String>> currPathsList = new ArrayList<>();
    
    @Override
    public Transferable createTransferable (JComponent component) {
        
        
        JTree tree = (JTree) component;
        draggedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        clearList();
        setCurrPathList(draggedTreeNode);
        tree.setSelectionPath(new TreePath(draggedTreeNode));
        String componentName = draggedTreeNode.toString();
        StringSelection stringSelection = new StringSelection(componentName);
        return stringSelection;
    }
    

    @Override
    public int getSourceActions (JComponent c) {
        return TransferHandler.MOVE;
    }
    
    @Override
    public boolean importData(TransferSupport support) 
    {  
        if (!canImport(support)) 
            return false;
        
        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) tree.getModel();      
        
        tree.setEnabled(false);
        
        JTree.DropLocation dropLocation = (JTree.DropLocation) support.getDropLocation();
        
        Point point = dropLocation.getDropPoint(); 
        
        DefaultMutableTreeNode newTreeNodeParent = (DefaultMutableTreeNode) tree.getPathForLocation(point.x, point.y).getLastPathComponent();
        
        DefaultMutableTreeNode draggedTreeNodeParent = (DefaultMutableTreeNode) draggedTreeNode.getParent();
        
        if (newTreeNodeParent.equals(draggedTreeNodeParent)) {
            tree.setEnabled(true);
            return false;
        }
        
        boolean isUnique = TreeWindowUtility.checkNodeUniqueness(Collections.list( newTreeNodeParent.children() ), draggedTreeNode, "You are trying to duplicate the existing data!\n"+draggedTreeNode.toString() + " already exists in " + newTreeNodeParent.toString(), TreeWindowUtility.getTreeNodeComparator());
        if (!isUnique) {
            tree.setEnabled(true);
            return false;
        }
        List<String> lastPath = getFormattedPath(draggedTreeNode.getUserObjectPath());
        
        TreeNodeUIDelete deleteJTreeNode = new TreeNodeUIDelete();
        TreeNodeUIInsert addNewJTreeNode = new TreeNodeUIInsert();        
        
        TreeWindowUtility.getPathHandler().removeTreePath(new TreePath(draggedTreeNode));
        
        deleteJTreeNode.deleteFromParent(tree, defaultTreeModel, draggedTreeNodeParent, draggedTreeNode);
        
        int res = addNewJTreeNode.addNewNode(tree, newTreeNodeParent, draggedTreeNode);
        
        dbProcess(lastPath);
        
        tree.setEnabled(true);
        return (res == TreeWindowUtility.SUCCESSFUL_ADDED);
    }

    @Override    
    public boolean canImport(TransferSupport support) 
    {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor))
            return false;
        
        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) tree.getModel();
        
        JTree.DropLocation dropLocation = (JTree.DropLocation) support.getDropLocation();
        TreePath treePath = dropLocation.getPath();
        
        if (treePath == null)
            return false;
        
        DefaultMutableTreeNode currTreeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        
        return defaultTreeModel.getPathToRoot(currTreeNode).length == (defaultTreeModel.getPathToRoot(draggedTreeNode).length - 1);
    }
    
    private void dbProcess (List<String> lastPath) {
        
        insert(draggedTreeNode);
        insertClassLessons();
        new TreeNodeDeleteMedium().deleteController(lastPath);
    }
    
    private void insert (DefaultMutableTreeNode node) {
        List<String> currPath = getFormattedPath (node.getUserObjectPath());
        List<TreeNode> children = Collections.list( node.children() );
        InsertTreeDataMedium insertTreeData = new InsertTreeDataMedium(currPath);
        int level = currPath.size();
        
        if (level == 4)
            newPathsList.add(currPath);
        
        insert(insertTreeData, level, currPath.getLast());        
        
        for (TreeNode child : children) {
            currPath.add(child.toString());
            insert((DefaultMutableTreeNode) child);
        }
    }
    
    private void insert (InsertTreeDataMedium insertTreeData, int level, String nodeName)
    {
        switch (level) {
            case 2 -> {
                insertTreeData.insertClass(nodeName, new InsertClassProxy());
            }
            case 3 -> {
                insertTreeData.insertClassYear(nodeName, new InsertClassYearProxy());
            }
            case 4 -> {
                insertTreeData.insertClassTerm(nodeName, new InsertClassTermProxy());
            }            
        }        
    }    
    
    private void setCurrPathList (DefaultMutableTreeNode node) {
        
        List<String> currPath = getFormattedPath (node.getUserObjectPath());
        List<TreeNode> children = Collections.list( node.children() );
        int level = currPath.size();
        
        if (level == 4)
            currPathsList.add(currPath);        
        
        for (TreeNode child : children) {
            currPath.add(child.toString());
            setCurrPathList((DefaultMutableTreeNode) child);
        }        
    }
   
    private void insertClassLessons () 
    {
        if (currPathsList.isEmpty())
            return;
     
        int i = 0;
        
        for (List<String> currPath : currPathsList) {
            
            String schoolName = currPath.get(0);
            String className = currPath.get(1);
            int year = Integer.parseInt ( currPath.get(2) );
            int term = Integer.parseInt ( currPath.get(3) );
            
            List<String> classLessons = new ClassLessonMedium().getAllLessonsInTerm(schoolName, className, year, term);
            List<String> newPath = newPathsList.get(i);
            
            for (String lesson : classLessons) {
               boolean res =  new InsertTreeDataMedium(newPath).insertClassLesson(lesson, new InsertClassLessonProxy());
                System.out.println("lesson insert: " + res + "\t\t" + newPath + " "+ lesson);
            }
            i += 1;
        }
    }    
    
    private List<String> getFormattedPath (Object[] userPath) 
    {
        List<String> pathList = new ArrayList<>();
        TreeWindowUtility.pathObjToProgramDS(userPath, pathList);
        return pathList;
    }    
    
    private void clearList () {
        currPathsList.clear();
        newPathsList.clear();
    }
}
