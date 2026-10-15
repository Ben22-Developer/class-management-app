package app.classmanagementappclient.Backend.Objects.Year;

import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassYear 
{
    private final int year;
    private Classs classs;
    private int classNum; 
    private int yearNum;
    private final List<ClassTerm> classTermList = new ArrayList();

    public ClassYear(int year) {
        this.year = year;
    }

    public void setClasss(Classs classs) {
        this.classs = classs;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setClassYearNum(int yearNum) {
        this.yearNum = yearNum;
    }
    
    public void addTerm (ClassTerm term) {
        classTermList.add(term);
    }
    
    public int getYear() {
        return year;
    }

    public Classs getClasss() {
        return classs;
    }

    public int getClassNum() {
        return classNum;
    }

    public int getClassYearNum() {
        return yearNum;
    }

    public List<ClassTerm> getClassTermList() {
        return classTermList;
    }
    
    @Override
    public String toString() {
        return "Year{" + "year=" + year + ", class=" + classs + ", classNum=" + classNum + '}';
    }
}
