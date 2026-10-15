package app.classmanagementappclient.Frontend.TreeWindow.TreeLoading;

import app.classmanagementappclient.Frontend.AllObjectsLoad.AllObjectsLoad;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TreeLoader 
{  
    private  JTree jTree;
    private int currRowIndex;
    private boolean isIncrement;
    
    public void loadJTree () {
        
        String query = """
                SELECT schoolData.SchoolName, classData.ClassName, classYearData.year, classTermData.term
                FROM schoolData 
                LEFT JOIN teacherPersonalData ON teacherPersonalData.TeacherNum = schoolData.teacherNum 
                LEFT JOIN classData ON classData.schoolNum = schoolData.schoolNum
                LEFT JOIN classyearData ON classData.ClassNum = classyearData.ClassNum
                LEFT JOIN classtermData ON classtermData.ClassYearNum = classyearData.ClassYearNum
                WHERE teacherPersonalData.teacherNum = ? ORDER BY schoolData.schoolName, classData.ClassName, classyearData.year, classTermData.term
               """;        
        AllObjectsLoad treeObjectsLoad = new AllObjectsLoad();
        List<List<String>> data = treeObjectsLoad.load(query);
        
        for (List<String> d : data) {
            if (d.size() > 2)
                d.set(2, "Year " + d.get(2));
            if (d.size() > 3)
                d.set(3, "Term " + d.get(3));
        }
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Schools");
        jTree = new JTree(root);
        FrontEndUtility.setJComponentDefaultFont(jTree);
        constructTree(root, data);
//        printTree(root, i);
        System.out.println("\nSet & Counts Logs: At Frontend.TreeWindow.TreeLoading.loadJTree");
        System.out.println("Set.Size: " + data.size());
        System.out.println("Total counts: " + (currRowIndex));
        System.out.println("\n");
    }
    
    private void constructTree (DefaultMutableTreeNode root, List<List<String>> data) {
        
        DefaultMutableTreeNode node = null;
        List <DefaultMutableTreeNode> school = new ArrayList<>();
        
        while (currRowIndex < data.size()) {
            
            school.clear();
            
            if (node == null || !node.toString().equals(data.get(currRowIndex).get(0))) {
                node = new DefaultMutableTreeNode (data.get(currRowIndex).get(0));
                root.add(node);
            }
            school.add(node);
            
//            System.out.println("in constructTree " + school);
//            System.out.println("currRowIndex " + currRowIndex);            
            
            constructClass(school, data);
            currRowIndex = isIncrement ? currRowIndex + 1 : currRowIndex;
        }
    }
    
    private int constructClass (List <DefaultMutableTreeNode> school_class, List<List<String>> data) {
        
        isIncrement = true;
        int prevAncestorNum = 0;
        
        while (currRowIndex < data.size()) {
            
            while (school_class.size() > 1)
                school_class.removeLast();
            
            int i = 0;
            
            while (i < school_class.size() && school_class.get(i).toString().equals(data.get(currRowIndex).get(i)))
                i ++;
            
            if (i < school_class.size()) {
                isIncrement = false;
                return i;            
            }
            
            if (data.get(currRowIndex).size() < 2) { // if the attribute for class is empty return
                isIncrement = true;
                return prevAncestorNum;
            }
            
            DefaultMutableTreeNode classs = new DefaultMutableTreeNode(data.get(currRowIndex).get(1));
            school_class.getLast().add(classs);
            school_class.add (classs);
            
//            System.out.println("in constructClass " + school_class);
//            System.out.println("currRowIndex " + currRowIndex);
            
            int res = constructClassYear(school_class, data);
            if (res == 1)
                currRowIndex = isIncrement ? currRowIndex + 1 : currRowIndex;
            else
                return res;
        }   
        return 0;
    }
    
    private int constructClassYear (List <DefaultMutableTreeNode> school_class_year, List<List<String>> data) {
        
        isIncrement = true;
        int prevAncestorNum = 1;
        
        while (currRowIndex < data.size()) {
            
            while (school_class_year.size() > 2)
                school_class_year.removeLast();
            
            int i = 0;
            
            while (i < school_class_year.size() && school_class_year.get(i).toString().equals(data.get(currRowIndex).get(i))) 
                i ++;
            
            if (i < school_class_year.size()) {
                isIncrement = false;
                return i;
            }
            
            if (data.get(currRowIndex).size() < 3) { // if the attribute for year is empty return
                isIncrement = true;
                return prevAncestorNum;
            }
            
            DefaultMutableTreeNode year = new DefaultMutableTreeNode(data.get(currRowIndex).get(2));
            school_class_year.getLast().add(year);
            school_class_year.add (year);
            
//            System.out.println("in constructClassYear " + school_class_year);
//            System.out.println("currRowIndex " + currRowIndex);            
            
            int res = constructClassTerm(school_class_year, data);
            
            if (res == 2)
                currRowIndex = isIncrement ? currRowIndex + 1 : currRowIndex;
            else
                return res;
        }
        return 0;
    }
    
    private int constructClassTerm (List <DefaultMutableTreeNode> school_class_year_term, List<List<String>> data) {
        
        isIncrement = true;
        int prevAncestorNum = 2;
        
        while (currRowIndex < data.size()) {
            
            while (school_class_year_term.size() > 3)
                school_class_year_term.removeLast();
            
            int i = 0;
            
            while (i < school_class_year_term.size() && school_class_year_term.get(i).toString().equals(data.get(currRowIndex).get(i)))
                i ++;
            
            if (i < school_class_year_term.size()) {
                isIncrement = false;
                return i;
            }
            
            if (data.get(currRowIndex).size() < 4) { // if the attribute for term is empty return
                isIncrement = true;
                return prevAncestorNum;
            }
            DefaultMutableTreeNode term = new DefaultMutableTreeNode(data.get(currRowIndex).get(3));
            school_class_year_term.getLast().add(term);
            school_class_year_term.add (term);
            
//            System.out.println("in constructClassTerm " + school_class_year_term);
//            System.out.println("currRowIndex " + currRowIndex);            
            
            currRowIndex = isIncrement ? currRowIndex + 1 : currRowIndex;
        }        
        return 0;
    }   

    public void setjTree(JTree jTree) {
        this.jTree = jTree;
    }
    
    public JTree getjTree() {
        return jTree;
    }
    
    private void printTree (DefaultMutableTreeNode root, int stackNbr) {
        
        List<TreeNode> children = Collections.list(root.children());
        Iterator<TreeNode> iterator = children.iterator();
        
        int i = 0;
        while (i < stackNbr) {
            System.out.print("\t");
            i++;
        }
        System.out.println("--> "+root);
        
        while (iterator.hasNext()) {
            int nextStack = stackNbr + 1;
            printTree((DefaultMutableTreeNode) iterator.next(), nextStack);
        }
    }    
}
