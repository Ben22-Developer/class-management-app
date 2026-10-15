package app.classmanagementappclient.Medium.ClassYear;

import app.classmanagementappclient.Medium.ClassYear.ClassYearDelete.ClassYearDeleteMedium;
import app.classmanagementappclient.Medium.ClassYear.ReadClassYear.ReadClassYearMedium;
import app.classmanagementappclient.Medium.ClassYear.UpdateClassYear.ClassYearUpdateMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassYear.InsertClassYearMediumInterface;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassYearMedium {
    
    private final ClassYear classYear;
    private final Validation validation = new Validation();
    private String msg;

    public ClassYearMedium (ClassYear classYear) {
        this.classYear = classYear;
    }
    
    public ClassYearMedium () {
        this(null);
    }
    
    public boolean insertClassYear (InsertClassYearMediumInterface proxy) {
        return proxy.insertClass(this);
    }
    
    public int getOneClassYearNum () {
        ReadClassYearMedium readClassYearMedium = new ReadClassYearMedium();
        return readClassYearMedium.getOneClassYearNum(classYear);
    }
    
    public int getOneClassYearNumV1 () {
        String schoolName = classYear.getClasss().getSchool().getSchoolName();
        String className  = classYear.getClasss().getClassName();
        int year = this.classYear.getYear();
        ReadClassYearMedium readClassYearMedium = new ReadClassYearMedium();
        return readClassYearMedium.getOneClassYearNumV1(schoolName, className, year);
    }

    public int getOneClassYearNumV1 (String schoolName, String className, int year) {
        return new ReadClassYearMedium().getOneClassYearNumV1(schoolName, className, year);
    }    
    
    public List<Integer> getAllYears () {
        return new ReadClassYearMedium().getAllYears();
    }        
    
    public boolean updateYear (ClassYear currData, ClassYear newData) {
        boolean res = validationTest(newData);
        
        if (!res){
            showErrMsg();
            return false;
        }
        
        ClassYearUpdateMedium medium = new ClassYearUpdateMedium();
        res = medium.updateYear(this, currData, newData); 
        
        if (!res)
            showErrMsg();
        
        return res;
    }    
    
    public boolean deleteClassYear(int classYearNum) {
        ClassYearDeleteMedium medium = new ClassYearDeleteMedium();
        boolean res = medium.deleteClassYear(this, classYearNum);
        if (!res)
            showErrMsg();
        return res;
    }    
    
    public boolean validateYear () {
        int year = classYear.getYear();
        boolean res = validation.isYearValid(year);
        if (!res) 
            setMsg(validation.getErrorMsg());
        return res;
    }
    
    public boolean validationTest(ClassYear classYear) {
        int year = classYear.getYear();
        boolean res = validation.isYearValid(year);
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

    public ClassYear getClassYear() {
        return classYear;
    }
}
