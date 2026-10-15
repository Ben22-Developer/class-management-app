package app.classmanagementappclient.Medium.ClassLesson.ClassLessonUpdate;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Database.Update.ClassLesson.ClassLessonUpdate;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassLessonUpdateMedium {

    public boolean updateClassLessonData (ClassLessonMedium classLessonMedium, ClassLesson currData, ClassLesson newData) {
        
        ClassLessonUpdate dataUpdate = null;
        boolean bool = false;
        try {
            dataUpdate= new ClassLessonUpdate(currData, newData);
            bool = dataUpdate.updateData();
        } 
        catch (SQLException ex) {
            System.getLogger(ClassLessonUpdateMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        if (!bool)
            classLessonMedium.setMsg( dataUpdate.getErrorMsg() );
        return bool;
    }    
}
