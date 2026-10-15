package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Delete;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.*;

/**
 *
 * @author BEN
 */
public class TreeNodeDeleteMedium 
{
    private final JTree tree;
    private final List<List<String>> selectedPaths = new ArrayList();
    private final TreeNodeUIDelete deleteJTreeNode = new TreeNodeUIDelete();
    
    public TreeNodeDeleteMedium(JTree tree) {
        this.tree = tree;
    }
    
    public TreeNodeDeleteMedium() {
        this(null);
    }    
    
    public void deleteTreeNode () 
    {
        tree.setEnabled(false);
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        List<String> pathStr = new ArrayList<>();
        
        setSelectedPathStrList(selectedNode, pathStr);
        
        setSelectedPaths(selectedNode, pathStr);
        
        deleteJTreeNode.deleteConfirmed(tree);
        tree.setEnabled(true);
    }
    
    public void delete () 
    {
        tree.setEnabled(false);
  
        Set<TreeNode> treeNodesSet = new HashSet<>();
        
        TreeWindowUtility.setMultiSelectDSA(tree, tree.getSelectionPaths(), treeNodesSet);
        
        tree.setSelectionPath(null);

        for (TreeNode node :treeNodesSet) {
            DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) node;
            tree.setSelectionPath(new TreePath(dmtn));
            List<String> pathStr = new ArrayList<>();            
            TreeWindowUtility.pathObjToProgramDS(dmtn.getUserObjectPath(), pathStr);
            deleteController(pathStr);
        }
        tree.setEnabled(true);
    }
    
    
    public boolean deleteController (List<String> path) {
        
        int level = path.size();
        switch (level) {
            case 1 -> {
                return deleteSchool(path);
            }
            case 2 -> {
                return deleteClass(path);
            }  
            case 3 -> {
                return deleteClassYear(path);
            }
            case 4 -> {
                return deleteClassTerm(path);
            }
            case 5 -> {
                return deleteClassLesson(path);
            }            
            default -> throw new AssertionError();
        }        
    }
    
    private boolean deleteSchool (List<String> path) {
        
        String schooName = path.get(0);
        SchoolMedium medium = new SchoolMedium();
        int schoolNum = medium.getSchoolNum(schooName);
        boolean res = medium.deleteSchool(schoolNum);
        if (res)
            deleteMultipleGuiTreePaths();
        return res;
    }

    private boolean deleteClass (List<String> pathStr) {
        ClassMedium medium = new ClassMedium();
        int classNum = medium.getOneClassNumV1(pathStr.get(0), pathStr.get(1));
        boolean res = medium.deleteClass(classNum);
        if (res)
            deleteMultipleGuiTreePaths();
        return res;
    }
    
    private boolean deleteClassYear (List<String> pathStr) {
        ClassYearMedium medium = new ClassYearMedium();
        int classYearNum = medium.getOneClassYearNumV1(pathStr.get(0), pathStr.get(1), Integer.parseInt(pathStr.get(2)));
        boolean res = medium.deleteClassYear(classYearNum);
        if (res)
            deleteMultipleGuiTreePaths();
        return res;
    }
    
    private boolean deleteClassTerm(List<String> pathStr) {
        ClassTermMedium medium = new ClassTermMedium();
        int classTermNum = medium.getOneClassTermNumV1(pathStr.get(0), pathStr.get(1), Integer.parseInt(pathStr.get(2)), Integer.parseInt(pathStr.get(3)));
        boolean res = medium.deleteClassTerm(classTermNum);
        if (res)
            deleteMultipleGuiTreePaths();    
        return res;
    }   
    
    private boolean deleteClassLesson(List<String> path) {
        ClassLessonMedium medium = new ClassLessonMedium();
        int classTermNum = medium.getOneClassLessonNum(path.get(0), path.get(1), Integer.parseInt(path.get(2)), Integer.parseInt(path.get(3)), path.get(4));
        return medium.deleteClassLesson(classTermNum);
    }    
    
    private void deleteMultipleGuiTreePaths () {
        
        if (tree == null)
            return;
        
        TreePath[] treePath = tree.getSelectionPaths();
        
        Set<TreeNode> toDeleteNodes = new HashSet<>();
        
        TreeWindowUtility.setMultiSelectDSA(tree, treePath, toDeleteNodes);

        Iterator<TreeNode> toDeleteNodesIterator = toDeleteNodes.iterator();
        
        while (toDeleteNodesIterator.hasNext()) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) toDeleteNodesIterator.next();
            List<String> pathStr = new ArrayList<>();
            setSelectedPathStrList(selectedNode, pathStr);
            setSelectedPaths(selectedNode, pathStr);
        }
        deleteJTreeNode.deleteConfirmed(tree);
    }
    
    private void setSelectedPathStrList (DefaultMutableTreeNode selectedNode, List<String> pathStr) {
        Object[] objPath = selectedNode.getUserObjectPath();
        for(int i = 1; i < objPath.length; i++)
            pathStr.add(String.valueOf(objPath[i]));
    }
    
    private void setSelectedPaths (DefaultMutableTreeNode selectedNode, List<String> pathStr) {
        
        if (selectedNode.isLeaf()) {
            selectedPaths.add(new ArrayList<>(pathStr));
            pathStr.removeLast();
            return;
        }
        
        Enumeration<TreeNode> treeNodes = selectedNode.children();
        
        while (treeNodes.hasMoreElements()) 
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNodes.nextElement();
            pathStr.add(node.toString());
            setSelectedPaths(node, pathStr);
        }
        pathStr.removeLast();
    }
}
