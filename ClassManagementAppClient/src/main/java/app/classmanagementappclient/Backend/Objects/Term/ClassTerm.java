package app.classmanagementappclient.Backend.Objects.Term;

import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassTerm 
{
    private ClassYear year;
    private final int term;
    private int classYearNum;
    private int classTermNum;
    private final List<ClassLesson> classLessonList = new ArrayList();

    public ClassTerm(int term) {
        this.term = term;
    }

    public void setClassYear(ClassYear year) {
        this.year = year;
    }

    public void setClassYearNum(int classYearNum) {
        this.classYearNum = classYearNum;
    }

    public void setClassTermNum(int classTermNum) {
        this.classTermNum = classTermNum;
    }

    public ClassYear getClassYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }

    public int getClassYearNum() {
        return classYearNum;
    }

    public int getClassTermNum() {
        return classTermNum;
    }
    
    public void addLesson (ClassLesson lesson) {
        classLessonList.add(lesson);
    }  

    public List<ClassLesson> getClassLessonList() {
        return classLessonList;
    }

    @Override
    public String toString() {
        return "Term{" + "year=" + year + ", term=" + term + ", classYearNum=" + classYearNum + '}';
    }
}
