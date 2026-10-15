package app.classmanagementappclient.Backend.Objects.School;

import app.classmanagementappclient.Backend.Objects.Class.Classs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class School 
{
    private final String schoolName;
    private int schoolNum;
    private final List<Classs> schoolClassList = new ArrayList();
    
    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolNum(int schoolNum) {
        this.schoolNum = schoolNum;
    }
    
    public void addClass (Classs classs) {
        schoolClassList.add(classs);
    }    

    public int getSchoolNum() {
        return schoolNum;
    }
    
    public String getSchoolName() {
        return schoolName;
    }

    public List<Classs> getSchoolClassList() {
        return schoolClassList;
    }

    @Override
    public String toString() {
        return "School{" + "schoolName=" + schoolName + '}';
    }
}
