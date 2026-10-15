package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassLesson.InsertClassLessonMediumInterface;
import app.classmanagementappclient.Medium.Interfaces.ClassTerm.InsertClassTermMediumInterface;
import app.classmanagementappclient.Medium.Interfaces.ClassYear.InsertClassYearMediumInterface;
import app.classmanagementappclient.Medium.Interfaces.Classes.InsertClassMediumInterface;
import app.classmanagementappclient.Medium.Interfaces.Schools.InsertSchoolMediumInterface;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.UIDataInput_Insert.TreeNodeUIInsert;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertTreeDataMedium 
{
    private final JTree jTree;
    private final List<String> path;
    
    public InsertTreeDataMedium(JTree jTree, List<String> path) {
        this.jTree = jTree;
        this.path = path;
    }
    
    public InsertTreeDataMedium(List<String> path) {
        this(null, path);
    }
    
    public boolean insertSchool (String schoolName, InsertSchoolMediumInterface proxy) {
        
        School school = new School(schoolName);
        SchoolMedium schoolMedium = new SchoolMedium (school);
        boolean res = schoolMedium.insertSchool (proxy);
        
        if (res && jTree != null){
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) jTree.getLeadSelectionPath().getLastPathComponent();
            new TreeNodeUIInsert().addNewNode(jTree, parent, new DefaultMutableTreeNode(schoolName), true);
        }    
        return res;
    }
    
    public boolean insertClass (String className, InsertClassMediumInterface proxy) {        
        
        String schoolName = path.get(0);
        School school = getSchool(schoolName);
        Classs classs = getClasss(className, school);
        ClassMedium medium = new ClassMedium(classs);
        boolean res = medium.insertNewClass (proxy);
        
        if (res && jTree != null) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) jTree.getLeadSelectionPath().getLastPathComponent();
            new TreeNodeUIInsert().addNewNode(jTree, parent, new DefaultMutableTreeNode(className), true);
        }   
        return res;
    } 
    
    public boolean insertClassYear (String year, InsertClassYearMediumInterface proxy) {
        
        int intYear = getInt(year, "A year must be an integer greater than 1000");
        if (intYear == Integer.MIN_VALUE)
            return false;
        
        String schoolName = path.get(0);
        String className = path.get(1);
        ClassYear classYear = getClassYear(schoolName, className, intYear);
        ClassYearMedium classYearMedium = new ClassYearMedium(classYear);
        boolean res = classYearMedium.insertClassYear(proxy);
        
        if (res && jTree != null) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) jTree.getLeadSelectionPath().getLastPathComponent();
            new TreeNodeUIInsert().addNewNode(jTree, parent, new DefaultMutableTreeNode("Year " + classYear.getYear()), true);
        }
        return res;
    }
    
    public boolean insertClassTerm (String term, InsertClassTermMediumInterface proxy) {
        int termInt = getInt(term, "A term must be an integer greater than 0");
        if (termInt == Integer.MIN_VALUE)
            return false;        
        
        String schoolName = path.get(0);
        String className = path.get(1);
        int year = Integer.parseInt(path.get(2));
        
        ClassTerm classTerm = getClassTerm(schoolName, className, year, termInt);
        ClassTermMedium classTermMedium = new ClassTermMedium(classTerm);
        boolean res = classTermMedium.insertClassTerm(proxy);
        
        if (res && jTree != null) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) jTree.getLeadSelectionPath().getLastPathComponent();
            new TreeNodeUIInsert().addNewNode(jTree, parent, new DefaultMutableTreeNode("Term " +termInt), true);
        }    
        return res;
    }
    
    public boolean insertClassLesson(String lessonName, InsertClassLessonMediumInterface proxy) {
        
        String schoolName = path.get(0);
        String className = path.get(1);
        int year = Integer.parseInt(path.get(2));
        int term = Integer.parseInt(path.get(3));
        
        ClassLesson classLesson = getClassLesson(schoolName, className, year, term, lessonName);
        ClassLessonMedium medium = new ClassLessonMedium(classLesson);
        return medium.insertClassLesson(proxy); 
    }    
    
    private int getInt (String numString, String msg) {
        int intYear = 0;
        try {
            intYear = Integer.parseInt(numString);
        }
        catch (Exception e) {
            intYear = Integer.MIN_VALUE;
        }
        
        if (intYear == Integer.MIN_VALUE) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        return intYear;
    }   
    
    private ClassLesson getClassLesson (String schoolName, String className, int year, int term, String lessonName) {
        ClassLesson classLesson = new ClassLesson(lessonName);
        ClassTerm classTerm = getClassTerm(schoolName, className, year, term);
        int classTermNum = getClassTermNum(classTerm);
        classLesson.setClassTerm(classTerm);
        classLesson.setClassTermNum(classTermNum);
        return classLesson;
    }
    
    private int getClassTermNum (ClassTerm classTerm) {
        String schoolName = classTerm.getClassYear().getClasss().getSchool().getSchoolName();
        String className = classTerm.getClassYear().getClasss().getClassName();
        int year = classTerm.getClassYear().getYear();
        int term = classTerm.getTerm();
        return new ClassTermMedium().getOneClassTermNumV1(schoolName, className, year, term);
    }    
    
    private ClassTerm getClassTerm (String schoolName, String className, int year, int term) {
        ClassTerm classTerm = new ClassTerm(term);
        ClassYear classYear = getClassYear(schoolName, className, year);
        int classYearNum = getClassYearNum(classYear);
        classTerm.setClassYear(classYear);
        classTerm.setClassYearNum(classYearNum);
        return classTerm;
    }
    
    private int getClassYearNum (ClassYear classYear) {
        ClassYearMedium classYearMedium = new ClassYearMedium(classYear);
        return classYearMedium.getOneClassYearNum();
    }
    
    private ClassYear getClassYear (String schoolName, String className, int year) {
        School school = getSchool(schoolName);
        Classs classs = getClasss(className, school);
        ClassYear classYear = new ClassYear(year);
        int classNum = getClassNum(classs);
        classYear.setClassNum(classNum);
        classYear.setClasss(classs);
        return classYear;
    }    
    
    private Classs getClasss (String className, School school) {
        Classs classs = new Classs(className);
        classs.setSchool(school);
        classs.setSchoolNum(school.getSchoolNum());
        return classs;
    }
    
    private int getClassNum (Classs classs) {
        ClassMedium medium = new ClassMedium(classs);
        return medium.getOneClassNum();
    }    
    
    private School getSchool (String schoolName) {
        School school = new School(schoolName);
        SchoolMedium medium = new SchoolMedium(school);
        medium.setSchoolNum();
        return school;
    }
}
