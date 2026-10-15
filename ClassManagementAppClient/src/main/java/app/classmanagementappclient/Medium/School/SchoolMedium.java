package app.classmanagementappclient.Medium.School;

import app.classmanagementappclient.Medium.Interfaces.Schools.InsertSchoolMediumInterface;
import app.classmanagementappclient.Medium.School.ReadSchool.ReadSchoolMedium;
import app.classmanagementappclient.Medium.School.SchoolDelete.SchoolDeleteMedium;
import app.classmanagementappclient.Medium.School.UpdateSchool.UpdateSchoolMedium;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SchoolMedium {
    
    private String msg;
    private final School school;
    
    public SchoolMedium(School school) {
        this.school = school;
    }

    public SchoolMedium() {
        this(null);
    }
    
    public boolean insertSchool (InsertSchoolMediumInterface proxy) {
        return proxy.insertSchool(this);
    }
    
    public boolean updateSchool (School currSchoolDetails, School newSchoolDetails) {
        
        boolean res = validateSchool (newSchoolDetails);
        if (!res) {
            showErrMsg();
            return false;
        }
        
        UpdateSchoolMedium medium = new UpdateSchoolMedium();
        res = medium.updateSchoolDetails(this, currSchoolDetails, newSchoolDetails);
        
        if (!res) 
            showErrMsg();
        
        return res;
    }
    
    public boolean deleteSchool (int schoolNum) {
        SchoolDeleteMedium medium = new SchoolDeleteMedium();
        boolean res = medium.deleteSchool(this, schoolNum);
        if (!res)
            showErrMsg();
        return res;
    }
    
    public void setSchoolNum () {
        int schoolNum = getSchoolNum(school.getSchoolName());
        school.setSchoolNum(schoolNum);
    }
    
    public int getSchoolNum (String schoolName) {
        return new ReadSchoolMedium().getSchoolNum(schoolName);
    }
    
    public List<String> getAllSchools () {
        return new ReadSchoolMedium().getAllSchools();
    }
    
    public boolean validateSchool () {
        Validation validation = new Validation();
        boolean res = validation.isNameValid(school.getSchoolName());
        if (!res) 
            setMsg(validation.getErrorMsg());
        return res;
    }
    
    private boolean validateSchool (School school) {
        Validation validation = new Validation();
        boolean res = validation.isNameValid(school.getSchoolName());
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
    
    public School getSchool() {
        return school;
    }
    
    
}
