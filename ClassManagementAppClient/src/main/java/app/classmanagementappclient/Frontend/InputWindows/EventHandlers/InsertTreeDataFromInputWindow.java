package app.classmanagementappclient.Frontend.InputWindows.EventHandlers;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies.InsertClassProxyV1;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy.InsertClassLessonProxyV1;
import app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm.Proxies.InsertClassTermProxyV1;
import app.classmanagementappclient.Medium.ClassYear.InsertClassYear.Proxies.InsertClassYearProxyV1;
import app.classmanagementappclient.Medium.School.InsertSchool.Proxies.InsertSchoolProxyV1;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.InsertTreeDataMedium;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BEN
 */
public class InsertTreeDataFromInputWindow {

    private final JTree jTree;
    private int isInserted;
    private boolean canDisplayMsg;

    public InsertTreeDataFromInputWindow(JTree jTree) {
        this.jTree = jTree;
    }
    
    public boolean dataInsert (String schoolName, String className, String year, String term, Set<String> newLessons) {
        
        DefaultMutableTreeNode root = null;
        
        if (jTree != null)
            root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        
        List<String> pathStr = new ArrayList<>();
        InsertTreeDataMedium medium = new InsertTreeDataMedium (jTree, pathStr);
        boolean res = false;
        int i = 0;
        
        for (String input : new String[] { schoolName, className, year, term }) {
            
            selectPath(root);
            pathStr.add(input);
            res = insertData(medium, pathStr);
            
            if (!res)
                return false;
            
            String nextNodeName;
            
            if (i == 2)
                nextNodeName = "Year " + year;
            else if (i == 3)
                nextNodeName = "Term " + term;
            else
                nextNodeName = input;
            
            root = getNextNodeToSelect(root, nextNodeName);
            i += 1;
        }
        
        for (String lesson : newLessons) {
            pathStr.add(lesson);
            res = insertData(medium, pathStr);
            if (!res)
                return false;            
            pathStr.removeLast();
        }
        
        return res;
    }
    
    private void selectPath (DefaultMutableTreeNode node) {
        if (jTree == null)
            return;
        jTree.setLeadSelectionPath (new TreePath(node));
    }
    
    private boolean insertData (InsertTreeDataMedium medium, List<String> pathStr) {
        
        int pathCount = pathStr.size();
        String userInput = pathStr.getLast();
        boolean res = false;
        
        switch(pathCount) {
            case 1 -> {
                InsertSchoolProxyV1 proxyV1 = new InsertSchoolProxyV1();
                res = medium.insertSchool(userInput, proxyV1);
                setIsInserted(proxyV1.isInserted);
            }
            case 2 -> {
                InsertClassProxyV1 proxyV1 = new InsertClassProxyV1();
                res = medium.insertClass(userInput,proxyV1);
                setIsInserted(proxyV1.isInserted);
            }
            case 3 -> {
                InsertClassYearProxyV1 proxyV1 = new InsertClassYearProxyV1();
                res = medium.insertClassYear(userInput, proxyV1);
                setIsInserted(proxyV1.isInserted);
            }
            case 4 -> {
                InsertClassTermProxyV1 proxyV1 = new InsertClassTermProxyV1();
                res = medium.insertClassTerm(userInput, proxyV1);
                setIsInserted(proxyV1.isInserted);
            } 
            case 5 -> {
                InsertClassLessonProxyV1 proxyV1 = new InsertClassLessonProxyV1();           
                res = medium.insertClassLesson(userInput, proxyV1);
                setIsInserted(proxyV1.isInserted);
            }
        }
        setCanDisplayMsg(res);
        return res;
    }

    public void setIsInserted(int isInserted) {
        this.isInserted = this.isInserted == 0 ? isInserted : this.isInserted;
    }
    
    private DefaultMutableTreeNode getNextNodeToSelect (DefaultMutableTreeNode root, String nodeName) {
        return (jTree == null) ? null : TreeWindowUtility.getChildNode(root, nodeName);
    }    

    public int getIsInserted() {
        return isInserted;
    }

    public boolean isCanDisplayMsg() {
        return canDisplayMsg;
    }

    public void setCanDisplayMsg(boolean canDisplayMsg) {
        this.canDisplayMsg = canDisplayMsg;
    }
}
