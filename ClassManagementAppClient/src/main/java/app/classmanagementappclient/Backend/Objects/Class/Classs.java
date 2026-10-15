package app.classmanagementappclient.Backend.Objects.Class;

import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class Classs 
{   
    private final String className;
    private int schoolNum;
    private int classNum;    
    private School school;
    private final List<ClassYear> classYearList = new ArrayList();
    
    public Classs(String className) {
        this.className = className;
    }
    
    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setSchoolNum(int schoolNum) {
        this.schoolNum = schoolNum;
    }
    
    public void setSchool(School school) {
        this.school = school;
    }

    public void addYear (ClassYear year) {
        classYearList.add(year);
    }
    
    public int getClassNum() {
        return classNum;
    }

    public String getClassName() {
        return className;
    }

    public School getSchool() {
        return school;
    }

    public int getSchoolNum() {
        return schoolNum;
    }

    public List<ClassYear> getClassYearList() {
        return classYearList;
    }

    @Override
    public String toString() {
        return "Classs{" + "className=" + className + ", school=" + school + ", schoolNum=" + schoolNum + '}';
    }
}
