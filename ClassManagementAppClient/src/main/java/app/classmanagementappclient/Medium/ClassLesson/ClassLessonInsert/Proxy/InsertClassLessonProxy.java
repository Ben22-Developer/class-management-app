package app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.ClassLessonInsertMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassLesson.InsertClassLessonMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassLessonProxy implements InsertClassLessonMediumInterface {

    @Override
    public boolean insertClassLesson(ClassLessonMedium medium) {
        
        boolean res = medium.validateLesson();
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        ClassLessonInsertMedium lessonInsertmedium = new ClassLessonInsertMedium();
        res = lessonInsertmedium.insertClassLesson(medium, medium.getClassLesson());
        if (!res) 
            medium.showErrMsg();
        return res;    
    }
}
