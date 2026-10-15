package app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.ClassLessonInsertMedium;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.Interfaces.ClassLesson.InsertClassLessonMediumInterface;

/**
 *
 * @author BEN
 */
public class InsertClassLessonProxyV1 implements InsertClassLessonMediumInterface {

    public int isInserted;

    @Override
    public boolean insertClassLesson(ClassLessonMedium medium) {
        
        boolean res = medium.validateLesson();
        if (!res) {
            medium.showErrMsg();
            return false;
        }
        ClassLessonInsertMedium lessonInsertmedium = new ClassLessonInsertMedium();
        res = lessonInsertmedium.insertClassLesson(medium, medium.getClassLesson());
        
        if (res)
            isInserted = 1;         
        return true;
    }

    public int getIsInserted() {
        return isInserted;
    }
}
