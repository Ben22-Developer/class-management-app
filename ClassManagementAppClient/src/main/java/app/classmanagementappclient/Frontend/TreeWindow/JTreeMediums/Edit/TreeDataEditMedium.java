package app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Edit;

import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.School.SchoolMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TreeDataEditMedium extends ADialog
{
    private final JTree jTree;
    private final Validation validation = new Validation();

    public TreeDataEditMedium(JTree jTree) {
        this.jTree = jTree;
    }
    
    @Override
    public void drawJDialog () {
        setSuperFields(jTree);
        super.drawJDialog();
    }
    
    private void setSuperFields (JTree jTree) {
        Object[] paths = jTree.getLeadSelectionPath().getPath();
        setSuperHeaders(paths.length - 1); // -1 in order to remove 'Schools'
        setSuperValues(paths);
    }
    
    private void setSuperHeaders (int headersNum) 
    {
        String[] allHeaders = {"Schools", "Class name", "Year", "Term"};
        
        List<String>  headers = new ArrayList<>();
        int i = 0;
        
        do {
            headers.add(allHeaders[i]);
            i ++;
        }
        while (i < headersNum);
        super.setHeaders(headers);
    }
    
    private void setSuperValues (Object[] paths) {
        List<String> values = new ArrayList<>();
        TreeWindowUtility.pathObjToProgramDS(paths, values);
        super.setValues(values);
    }
    
    @Override
    protected void addCenterPanel ()
    {
        String labelTxt = super.getHeaders().getLast();
        String value = super.getSelectedRowData().getLast();
        JLabel label = new JLabel(labelTxt);
        JTextField textField = new JTextField (value);
        setJComponent(label,  new Dimension(120,40));
        FrontEndUtility.setJTextField(textField);
        Dimension dimension = new Dimension(500,50);
        JPanel jPanel = new JPanel(new FlowLayout(SwingConstants.CENTER, 50, 10));
        setJPanel(jPanel, new JComponent[] { label, textField}, dimension);
        super.getTextFields().add(textField);
        super.getDialog().add(jPanel);
    }
    
    @Override
    protected void handleSubmitEvent () 
    {   
        List<String> userInputs = super.getUserInputs();
        List<JTextField> textFields = super.getTextFields();
        List<String> values = super.getSelectedRowData();
        
        for (int i = 0; i < values.size() - 1; i++)
            userInputs.add(values.get(i));
        
        for (JTextField textField : textFields) {
            String txt = textField.getText();
            if (txt.isBlank()) {
                userInputs.clear();
                return;
            }
            userInputs.add(txt);
        }
        
        super.finalTouch();
    }    
    
    @Override
    public boolean handler() 
    {
        boolean res = false;
        List<String> newData = super.getUserInputs();
        List<String> oldData = super.getSelectedRowData();      
        int level = newData.size();
        if (newData.getLast().equals(oldData.getLast())) 
            return false;
        
        switch (level) {
            case 1 -> {
                res = schoolEdit(oldData, newData);
            }
            case 2 -> {
                res = classEdit(oldData, newData);
            }
            case 3 -> {
                res = classYearEdit(oldData, newData);
            }  
            case 4 -> {
                res = classTermEdit(oldData, newData);
            }
            default -> throw new AssertionError();
        }
        return res;
    }
    
    private boolean schoolEdit (List<String> oldData, List<String> newData) {
        School currSchoolDetails = new School (oldData.get(0));
        School newSchoolDetails = new School (newData.get(0));
        SchoolMedium schoolMedium = new SchoolMedium();
        boolean res = schoolMedium.updateSchool(currSchoolDetails, newSchoolDetails);
        if (res)
            updateJTree(oldData, newData);
        return res;
    }
    
    private boolean classEdit (List<String> oldData, List<String> newData) {
        
        School school = getSchool(oldData.get(0));
        
        Classs currClassDetails = getClasss(school, oldData.get(1));
        Classs newClassDetails = getClasss(school, newData.get(1));
        
        ClassMedium classMedium = new ClassMedium();
        boolean res = classMedium.updateClass(currClassDetails, newClassDetails);
        
        if (res) 
            updateJTree(oldData, newData);
        
        return res;
    }   
    
    private boolean classYearEdit (List<String> oldData, List<String> newData) {
        
        School school = getSchool(oldData.get(0));
        
        Classs classs = getClasss(school, oldData.get(1));
        setClassNum(classs);
        
        int currYear = Integer.parseInt( oldData.get(2) );
        int newYear = Integer.parseInt( newData.get(2) );
        
        ClassYear currClassYear = getClassYear(classs, currYear);
        ClassYear newClassYear = getClassYear(classs, newYear); 
        
        ClassYearMedium medium = new ClassYearMedium();
        boolean res = medium.updateYear(currClassYear, newClassYear); 
        
        if (res) 
            updateJTree(oldData, newData);
        
        return res;
    }      
    
    private boolean classTermEdit(List<String> oldData, List<String> newData) {
        
        School school = getSchool(oldData.get(0));
        Classs classs = getClasss(school, oldData.get(1));
        ClassYear classYear = getClassYear(classs, Integer.parseInt(oldData.get(2))); 
        setClassYearNum(classYear);
        
        ClassTerm currTerm = getClassTerm(classYear, Integer.parseInt(oldData.get(3)));
        ClassTerm newTerm = getClassTerm(classYear, Integer.parseInt(newData.get(3)));
        
        ClassTermMedium medium = new ClassTermMedium();
        boolean res = medium.updateTerm(currTerm, newTerm);
        
        if (res) 
            updateJTree(oldData, newData);
        
        return res;        
    }
    
    private ClassTerm getClassTerm (ClassYear classYear, int term) {
        ClassTerm classTerm = new ClassTerm(term);
        classTerm.setClassYear(classYear);
        classTerm.setClassYearNum( classYear.getClassYearNum() );
        return classTerm;
    }    
    
    private void setClassYearNum (ClassYear classYear) {
        ClassYearMedium medium = new ClassYearMedium(classYear);
        classYear.setClassYearNum(  medium.getOneClassYearNumV1() );
    }    
    
    private ClassYear getClassYear (Classs classs, int year) {
        ClassYear classYear = new ClassYear(year);
        classYear.setClasss(classs);
        classYear.setClassNum(classs.getClassNum());        
        return classYear;
    }
    
    private void setClassNum (Classs classs) {
        ClassMedium medium = new ClassMedium(classs);
        int num = medium.getOneClassNumV1();
        classs.setClassNum(num);
    }
    
    private Classs getClasss (School school, String className) {
        Classs classs = new Classs(className);
        classs.setSchool(school);
        classs.setSchoolNum(school.getSchoolNum());        
        return classs;
    }
    
    private School getSchool (String schoolName) {
        School school = new School (schoolName);
        SchoolMedium schoolMedium = new SchoolMedium(school);
        schoolMedium.setSchoolNum();
        return school;
    }
    
    private void updateJTree (List<String> oldData, List<String> newData) 
    {
        // get parent node
        // check if parent node has changed the value, if so edit it (do this in loop)
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        
        for (int i = 0; i < oldData.size(); i++) 
        {
            String childNodeCurrName = oldData.get(i);
            String childNodeNewName = newData.get(i);

            if (i == 2) {
                childNodeCurrName = "Year " + childNodeCurrName;
                childNodeNewName = "Year " + childNodeNewName;
            }    
            if (i == 3) {
                childNodeCurrName = "Term " + childNodeCurrName;
                childNodeNewName = "Term " + childNodeNewName;
            }            
            
            if (!childNodeCurrName.equals(childNodeNewName))
                childNodeCurrName = updateTreeChildNode(rootNode, childNodeCurrName, childNodeNewName);
            
            rootNode = TreeWindowUtility.getChildNode(rootNode, childNodeCurrName);
        }
    }
    
    private String updateTreeChildNode (DefaultMutableTreeNode rootNode, String childNodeCurrName, String childNodeNewName)
    {
        DefaultMutableTreeNode childNode = TreeWindowUtility.getChildNode(rootNode, childNodeCurrName);
        new TreeNodeUIEdit().jTreeNodeEdit(jTree, childNodeNewName, childNode);
        return childNodeNewName;
    }
}
