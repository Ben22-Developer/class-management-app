package app.classmanagementappclient.Medium.Class;

import app.classmanagementappclient.Medium.Class.ClassDelete.ClassDeleteMedium;
import app.classmanagementappclient.Medium.Class.ClassRead.ClassReadMedium;
import app.classmanagementappclient.Medium.Class.ClassUpdate.ClassUpdateMedium;
import app.classmanagementappclient.Medium.Interfaces.Classes.InsertClassMediumInterface;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassMedium 
{
    private String msg;
    private final Classs classs;
    private final Validation validation = new Validation();

    public ClassMedium(Classs classs) {
        this.classs = classs;
    }
    
    public ClassMedium() {
        this(null);
    }    
    
    public boolean insertNewClass (InsertClassMediumInterface proxy) {
        return proxy.insertClass(this);
    }
    
    public boolean updateClass (Classs currClassDetails, Classs newClassDetails) {
        boolean res = validationTest(newClassDetails);
        
        if (!res){
            showErrMsg();
            return false;
        }
        
        ClassUpdateMedium medium = new ClassUpdateMedium();
        res = medium.updateClass(this, currClassDetails, newClassDetails); 
        
        if (!res)
            showErrMsg();
        
        return res;
    }
    
    public boolean deleteClass (int classNum) {
        ClassDeleteMedium medium = new ClassDeleteMedium();
        boolean res = medium.deleteClass(this, classNum);
        if (!res)
            showErrMsg();
        return res;
    }    
    
    public int getOneClassNum () {
        ClassReadMedium readMedium = new ClassReadMedium();
        return readMedium.getOneClassNum(classs);
    }
    
    public int getOneClassNumV1 () {
        ClassReadMedium readMedium = new ClassReadMedium();
        return readMedium.getOneClassNumV1( classs.getSchool().getSchoolName(), classs.getClassName());
    }    

    public int getOneClassNumV1 (String schoolName, String className) {
        ClassReadMedium readMedium = new ClassReadMedium();
        return readMedium.getOneClassNumV1( schoolName, className);
    }  
    
    public List<String> getAllClasses () {
        return new ClassReadMedium().getAllClasses();
    }    
    
    public boolean validationTest () { 
        boolean res = validation.isClassNameValid(classs.getClassName());
        if (!res) 
            setMsg (validation.getErrorMsg());
        return res;
    }

    private boolean validationTest (Classs classs) { 
        boolean res = validation.isClassNameValid(classs.getClassName());
        if (!res) 
            setMsg (validation.getErrorMsg());
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

    public Classs getClasss() {
        return classs;
    }
}
