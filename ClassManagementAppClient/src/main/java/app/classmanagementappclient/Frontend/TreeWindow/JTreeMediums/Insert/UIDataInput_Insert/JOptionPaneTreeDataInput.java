package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.UIDataInput_Insert;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies.InsertClassProxy;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy.InsertClassLessonProxy;
import app.classmanagementappclient.Medium.ClassTerm.InsertClassTerm.Proxies.InsertClassTermProxy;
import app.classmanagementappclient.Medium.ClassYear.InsertClassYear.Proxies.InsertClassYearProxy;
import app.classmanagementappclient.Medium.School.InsertSchool.Proxies.InsertSchoolProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.InsertTreeDataMedium;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class JOptionPaneTreeDataInput {
    
    public void insert (JTree jTree) {
        
        TreePath treePath = jTree.getSelectionPath();
        String txt = getTxt(treePath);
        String title = getTitle(treePath);
        String userInput = FrontEndUtility.getUserInput(txt, title);
        
        if (userInput == null)
            return;
        else if (!userInput.isBlank())
            insert(userInput, jTree, treePath.getPathCount());
        else if (userInput.isBlank())
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Text field is blank!", "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    private String getTxt (TreePath treePath) {
        int pathCount = treePath.getPathCount();
        switch (pathCount) {
            case 1 -> {
                return "Insert new school name:";
            }
            case 2 -> {
                return "Insert new class for school: " + treePath.getLastPathComponent();
            }            
            case 3 -> {
                return "Insert new academic year for class: " + treePath.getPath()[1]+"/"+treePath.getLastPathComponent();
            }
            case 4 -> {
                return "Insert new academic term for class: " + treePath.getPath()[1]+"/"+treePath.getPath()[2]+"/"+treePath.getLastPathComponent();
            }            
            case 5 -> {
                return "Insert new lesson for class: " + treePath.getPath()[1]+"/"+treePath.getPath()[2]+"/"+treePath.getPath()[3] + "/"+treePath.getLastPathComponent();
            }
            default -> throw new AssertionError();
        }
    }
    
    private String getTitle (TreePath treePath) {
        int pathCount = treePath.getPathCount();
        switch (pathCount) {
            case 1 -> {
                return "New school insert";
            }
            case 2 -> {
                return "New class insert";
            }            
            case 3 -> {
                return "Academic year insert";
            }
            case 4 -> {
                return "Academic term insert";
            }   
           case 5 -> {
                return "Lesson insert";
            }               
            default -> throw new AssertionError();
        }
    }
    
    private void insert (String userInput, JTree jTree, int pathCount) {
        switch(pathCount) {
            case 1 -> {
                insertSchool(userInput, jTree);
            }
            case 2 -> {
                insertClass(userInput, jTree);
            }
            case 3 -> {
                insertClassYear(userInput, jTree);
            }
            case 4 -> {
                insertClassTerm(userInput, jTree);
            } 
            case 5 -> {
                insertClassLesson(userInput, jTree);
            }            
        }
    }

    private void insertSchool (String schoolName, JTree jTree) {
        List<String> pathStr = new ArrayList<>();
        setPathStr(jTree, pathStr);
        new InsertTreeDataMedium(jTree, pathStr).insertSchool(schoolName, new InsertSchoolProxy());
    }
    
    private void insertClass (String className, JTree jTree) {
        List<String> pathStr = new ArrayList<>();
        setPathStr(jTree, pathStr);
        new InsertTreeDataMedium(jTree, pathStr).insertClass(className, new InsertClassProxy());
    } 
    
    private void insertClassYear (String year, JTree jTree) {
        List<String> pathStr = new ArrayList<>();
        setPathStr(jTree, pathStr);
        new InsertTreeDataMedium(jTree, pathStr).insertClassYear(year, new InsertClassYearProxy());
    }
    
    private void insertClassTerm (String term, JTree jTree) {
        List<String> pathStr = new ArrayList<>();
        setPathStr(jTree, pathStr);
        new InsertTreeDataMedium(jTree, pathStr).insertClassTerm(term, new InsertClassTermProxy());
    }
    
    private void insertClassLesson(String lesson, JTree jTree) {
        List<String> pathStr = new ArrayList<>();
        setPathStr(jTree, pathStr);
        new InsertTreeDataMedium(jTree, pathStr).insertClassLesson(lesson, new InsertClassLessonProxy());        
    }    
    
    private void setPathStr (JTree jTree, List<String> pathStr) {
        Object[] path = jTree.getSelectionPath().getPath();
        TreeWindowUtility.pathObjToProgramDS(path, pathStr);
    }
}
