package app.classmanagementappclient.Frontend.TreeWindow;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.ExpansionListener.JTreeExpansionPathHandler;
import app.classmanagementappclient.Frontend.TreeWindow.TreeInitializer.TreeInit;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.*;

/**
 *
 * @author BEN
 */
public class TreeWindowUtility
{
    public static int SUCCESSFUL_ADDED = -100;
    public static int BLANK_FIELD = -101;
    public static int DUPLICATE_DATA = -102;
    private static JTreeExpansionPathHandler pathHandler;

    public static void initializeTree (MainWindow mainWindow) 
    {
        if (!FrontEndUtility.isTeacherLoggedIn()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("To access this functionality you need to log in or sign in\nGo to file > setings or press 'Ctrl+Alt+S' ", "Informational message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (mainWindow.getjTree() != null) {
            new TreeInit().addJTreeToMainWindowTreePanel(mainWindow);
            return;
        }
        
        pathHandler = new JTreeExpansionPathHandler();
        
        TreeInit treeInit = new TreeInit();
        treeInit.init(mainWindow);
    }
    
    public static void destroyTree (MainWindow mainWindow)
    {
        JPanel treePanel = mainWindow.getTreePanel();
        treePanel.removeAll();
        mainWindow.getTreeDisplayMenuItem().setEnabled(true);
        mainWindow.getSplitPane().remove(treePanel);
        mainWindow.setTreePanel(null);
        mainWindow.getTreeDisplayMenuItem().setEnabled(true);
    }

    public static JTreeExpansionPathHandler getPathHandler() {
        return pathHandler;
    }
    
    public static<T> boolean checkNodeUniqueness (List<T> list, T newNode, String errMsg, Comparator<T> c, boolean...displayMsg) { 
        int res = Collections.binarySearch(list, newNode, c);
        boolean isUnique = (res < 0);
        if (!isUnique && displayMsg.length == 0) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(errMsg, "Duplicate Error", JOptionPane.ERROR_MESSAGE);     
        }
        return isUnique;
    }
    
    public static void getSortedTreenodeList (List<TreeNode> unsortedList) {
        Collections.sort(unsortedList, getTreeNodeComparator());
    }
    
    public static Comparator<TreeNode> getTreeNodeComparator () 
    {
        return (TreeNode o1, TreeNode o2) -> {
            return o1.toString().compareToIgnoreCase(o2.toString());
        };
    }
    
    public static<T> int getNodeIndex (List<T> list, T newNode, Comparator<T> comparator) {
        Collections.sort(list, comparator);
        return list.indexOf (newNode);
    }
    
    public static void reUpdateTreePath (JTree jTree) 
    {
        Map<TreePath, Boolean> treePathMap = pathHandler.getTreePathMap();
        Map<TreePath, Boolean> treePathMap1 = pathHandler.getTreePathMap();
        
        for (Map.Entry<TreePath, Boolean>entrySet : treePathMap.entrySet()) {
            TreePath treePath = entrySet.getKey();
            jTree.expandPath(treePath);
            treePathMap1.remove(treePath);
        }        
    }
    
    public static DefaultMutableTreeNode getChildNode (DefaultMutableTreeNode rootNode, String childNodeName)
    {
        List <TreeNode> children = Collections.list ( rootNode.children());
        int index = Collections.binarySearch(children, new DefaultMutableTreeNode(childNodeName), TreeWindowUtility.getTreeNodeComparator());
        return (index >= 0) ? (DefaultMutableTreeNode) children.get(index) : null;
    }
    
    public static <T> DefaultMutableTreeNode getOwnTreeHierarchy (T[] classDetails) 
    {
        String schoolName = String.valueOf( classDetails[0] );
        String className = String.valueOf( classDetails[1] );
        String year = String.valueOf( classDetails[2] );
        String term = String.valueOf( classDetails[3] );
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Schools");
        DefaultMutableTreeNode schoolNode = new DefaultMutableTreeNode(schoolName);
        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(className);
        DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode(year);
        DefaultMutableTreeNode termNode = new DefaultMutableTreeNode(term);
        
        rootNode.add(schoolNode);
        schoolNode.add(classNode);
        classNode.add(yearNode);
        yearNode.add(termNode);   
        
        return rootNode;
    }
    
    public static void programDSToTreeDS (Object[] programPath, List<String> treePathStr) {
        
        for (int i = 0; i < programPath.length; i++) 
        {
            String val = switch (i) {
                case 2 -> "Year " + String.valueOf(programPath[i]);
                case 3 -> "Term " + String.valueOf(programPath[i]);
                default -> String.valueOf(programPath[i]);
            };
            treePathStr.add (val);
        }          
    }
    
    public static void pathObjToProgramDS (Object[] pathObj, List<String> pathStr, boolean...startBy0)
    {
        int init = (startBy0.length != 0) ? 0 : 1;
        int yearOrTermSplit = (startBy0.length != 0) ? 2 : 3;
        
        for (int i = init; i < pathObj.length; i++) 
        {
            String val = String.valueOf(pathObj[i]);
            if (i >= yearOrTermSplit)
                val = val.split(" ")[1];
            pathStr.add (val);
        }  
    }
    
    public static void expandTreePath (JTree jTree, List<String> path) {

        DefaultMutableTreeNode dmtn = getTreeDescendant(jTree, path);
        
        TreeNode[] treeNode = ((DefaultMutableTreeNode) dmtn.getParent() ).getPath(); // getting dmtn parent and get the parent's path
        
        jTree.expandPath(new TreePath(treeNode));
             
    }
    
    public static DefaultMutableTreeNode getTreeDescendant (JTree tree, List<String> pathList) 
    {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        int i = 0;
        
        while (i < pathList.size()) { 
            treeNode = getChildNode(treeNode, pathList.get(i));
            i+=1;
        }
        return treeNode;
    }
    
    public static void setMultiSelectDSA (JTree jTree, TreePath[] treePaths, Set <TreeNode> treeNodesSet )
    {
        for (TreePath treePath : treePaths) {
            int row = jTree.getRowForPath(treePath);
            if (row > 0)
                setMultiSelectTreeNodesSet(treeNodesSet, (DefaultMutableTreeNode) treePath.getLastPathComponent());
        }        
    }
    
    private static void setMultiSelectTreeNodesSet (Set <TreeNode> treeNodesSet, DefaultMutableTreeNode newNode)
    {
        if (isAncestorNodeInSet(treeNodesSet, newNode))
            return;
        
        descendantsNodesRemoval(treeNodesSet, newNode);
        treeNodesSet.add(newNode);
    }
    
    private static boolean isAncestorNodeInSet (Set <TreeNode> treeNodesSet, DefaultMutableTreeNode node) 
    {
        while (node.getParent() != null) 
        {
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
            if (treeNodesSet.contains(parentNode))
                return true;
            node = parentNode;
        }
        return false;
    }
    
    private static void descendantsNodesRemoval (Set <TreeNode> treeNodesSet, DefaultMutableTreeNode newNode) 
    {
        Enumeration<TreeNode> childNodes = newNode.children();
        while (childNodes.hasMoreElements()) 
        {
            DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) childNodes.nextElement();
            if (treeNodesSet.contains(dmtn))
                treeNodesSet.remove(dmtn);
            else
                descendantsNodesRemoval(treeNodesSet, dmtn);
        }        
    }
    
    /**
     *While it sets the selection Path, note that the current selected path are erased and it sets only 1 selected path u passed as a list of string
     * @param jTree
     * @param path
     */
    public static void setSelectedPath (JTree jTree, List<String> path) {
        
        jTree.setSelectionPath(null);
        
        List<String> treePath = new ArrayList<>();
        programDSToTreeDS(path.toArray(), treePath);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        
        int i = 0;
        
        while (i <= treePath.size()- 1) {
            String childName = treePath.get(i);
            root = getChildNode(root, childName);
            i ++;
        }
        
        jTree.setSelectionPath(new TreePath(root));
    }
}
