package app.classmanagementappclient.Medium.ClassLesson;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonDelete.ClassLessonDeleteMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonRead.ReadClassLessonMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonUpdate.ClassLessonUpdateMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassLesson.InsertClassLessonMediumInterface;
import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassLessonMedium {
    
    private final ClassLesson classLesson;
    private final Validation validation = new Validation();
    private String msg;

    public ClassLessonMedium(ClassLesson classLesson) {
        this.classLesson = classLesson;
    }

    public ClassLessonMedium() {
        this(null);
    }
    
    public int getOneClassLessonNum (String schoolName, String className, int year, int term, String lessonName) {
        return new ReadClassLessonMedium().getOneClassLessonNum(schoolName, className, year, term, lessonName);
    } 
    
    public int getOneClassLessonNum (int termNum, String lessonName) {
        return new ReadClassLessonMedium().getOneClassLessonNum(termNum, lessonName);
    } 
        
    public boolean insertClassLesson (InsertClassLessonMediumInterface proxy) {
        return proxy.insertClassLesson(this);
    }
    
    public List<List<Object>> getAllLessonsWithDetails () {
        return new ReadClassLessonMedium().getAllLessonsWithDetails();
    }
    
    public List<String> getAllLessons () {
        return new ReadClassLessonMedium().getAllLessons();
    }    

    public List<String> getAllLessonsInTerm (String schoolName, String className, int classYear, int classTerm) {
        return new ReadClassLessonMedium().getAllLessonsInTerm(schoolName, className, classYear, classTerm);
    }

    public List<List<Object>> getAllLessonsInTerm (int classTermNum, List<String> lessonNames) {
        return new ReadClassLessonMedium().getAllLessonsInTerm(classTermNum, lessonNames);
    } 
    
    public List<String> getAllLessonsInTerm (int classTermNum) {
        return new ReadClassLessonMedium().getAllLessonsInTerm(classTermNum);
    }
    
    public String getLessonName (int lessonNum) {
        return new ReadClassLessonMedium().getLessonName(lessonNum);
    }
    
    public List<ClassLesson> getClassLessonsInTerm (List<Integer> classTermNums) {
        return new ReadClassLessonMedium().getClassLessonsInTerm(classTermNums);
    }
    
    public boolean deleteClassLesson (int classLessonNum) {
        ClassLessonDeleteMedium medium = new ClassLessonDeleteMedium();
        boolean res = medium.deleteClassLesson(this, classLessonNum);
        if (!res)
            showErrMsg();
        return res;
    }      
    
    public boolean updateClassLessonData (ClassLesson currData, ClassLesson newData) {
        
        boolean res = validateLesson(newData);
        if (!res) {
            showErrMsg();
            return false;
        }
        res = new ClassLessonUpdateMedium().updateClassLessonData(this, currData, newData);
        
        if (!res)
            showErrMsg();
        return res;        
    }
        
    
    public boolean validateLesson() {
        boolean isValid = validation.isLessonNameValid(classLesson.getLessonName());
        if (!isValid)
            setMsg(validation.getErrorMsg());
        return isValid;
    }

    public boolean validateLesson(ClassLesson classLesson) {
        boolean isValid = validation.isLessonNameValid(classLesson.getLessonName());
        if (!isValid)
            setMsg(validation.getErrorMsg());
        return isValid;
    }    
    
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
    public void showErrMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    public ClassLesson getClassLesson() {
        return classLesson;
    }
}
