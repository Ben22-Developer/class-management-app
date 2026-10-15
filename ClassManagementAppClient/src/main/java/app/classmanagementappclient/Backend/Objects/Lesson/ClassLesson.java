package app.classmanagementappclient.Backend.Objects.Lesson;

import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

/**
 *
 * @author BEN
 */
public class ClassLesson 
{
    private ClassTerm term;
    private final String lessonName;
    private int classTermNum;
    private int classLessonNum;

    public ClassLesson(String lessonName) {
        this.lessonName = lessonName;
    }

    public ClassTerm getTerm() {
        return term;
    }

    public void setClassTerm(ClassTerm term) {
        this.term = term;
    }

    public void setClassTermNum(int classTermNum) {
        this.classTermNum = classTermNum;
    }
    
    public String getLessonName() {
        return lessonName;
    }

    public int getClassTermNum() {
        return classTermNum;
    }

    public int getClassLessonNum() {
        return classLessonNum;
    }

    public void setClassLessonNum(int classLessonNum) {
        this.classLessonNum = classLessonNum;
    }

    @Override
    public String toString() {
        return "Lesson{" + "term=" + term + ", lessonName=" + lessonName + ", classTermNum=" + classTermNum + '}';
    }
}
