package app.classmanagementappclient.Medium.ClassTerm;

import app.classmanagementappclient.Medium.ClassTerm.DeleteClassTerm.ClassTermDeleteMedium;
import app.classmanagementappclient.Medium.ClassTerm.ReadClassTerm.ClassTermReadMedium;
import app.classmanagementappclient.Medium.ClassTerm.UpdateClassTerm.ClassTermUpdateMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassTerm.InsertClassTermMediumInterface;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassTermMedium 
{
    private final ClassTerm classTerm;
    private final Validation validation = new Validation();
    private String msg;

    public ClassTermMedium(ClassTerm classTerm) {
        this.classTerm = classTerm;
    }

    public ClassTermMedium() {
        this(null);
    }
    
    public boolean insertClassTerm (InsertClassTermMediumInterface proxy) {
        return proxy.insertClass(this);
    }    
    
    public boolean updateTerm (ClassTerm currData, ClassTerm newData) {
        boolean res = validationTest(newData);
        
        if (!res){
            showErrMsg();
            return false;
        }
        
        ClassTermUpdateMedium medium = new ClassTermUpdateMedium();
        res = medium.updateTerm(this, currData, newData); 
        
        if (!res)
            showErrMsg();
        
        return res;
    }      
    
    public int getOneClassTermNumV1 (String schoolName, String className, int year, int term) {
        return new ClassTermReadMedium().getOneClassTermNumV1(schoolName, className, year, term);
    }   
    
    public List<Integer> getOneClassTermNumV2 (String schoolName, String className, int classYear, List<Integer> classTerms) {
        return new ClassTermReadMedium().getOneClassTermNumV2(schoolName, className, classYear, classTerms);
    }    
    
    public List<List<String>> getClassesDetails (List<Integer> classTermNums) {
        return new ClassTermReadMedium().getClassesDetails(classTermNums);
    }
    
    public boolean deleteClassTerm(int classTermNum) {
        ClassTermDeleteMedium medium = new ClassTermDeleteMedium();
        boolean res = medium.deleteClassTerm(this, classTermNum);
        if (!res)
            showErrMsg();
        return res;
    }    
    
    public List<Integer> getAllTerms () {
        return new ClassTermReadMedium().getAllTerms();
    }
    
    public List<Integer> getClassYearTerms (String schoolName, String className, int year) {
        return new ClassTermReadMedium().getClassYearTerms(schoolName, className, year);
    }
    
    public boolean validateTerm () {
        int term = classTerm.getTerm();
        boolean res = validation.isTermValid(term);
        if (!res) 
            setMsg(validation.getErrorMsg());
        return res;
    }
    
   public boolean validationTest(ClassTerm classTerm) {
        int term = classTerm.getTerm();
        boolean res = validation.isTermValid(term);
        if (!res) 
            setMsg(validation.getErrorMsg());
        return res;   
   }    
    
    public void showErrMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }    

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ClassTerm getClassTerm() {
        return classTerm;
    }    
}
