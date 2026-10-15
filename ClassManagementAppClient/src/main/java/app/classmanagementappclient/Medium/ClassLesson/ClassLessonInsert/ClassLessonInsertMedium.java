package app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassLesson.InsertClassLesson;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassLessonInsertMedium {
        
    public boolean insertClassLesson (ClassLessonMedium classLessonMedium, ClassLesson classLesson) {
        
        InsertClassLesson insertClassLesson = null;
        boolean res = false;
        
        try (Connection connection = MediumUtility.getConnection()) {
            insertClassLesson = new InsertClassLesson(connection, classLesson);
            res = insertClassLesson.insert();
        } catch (SQLException ex) {
            System.getLogger(ClassLessonInsertMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        if (!res)
            classLessonMedium.setMsg(insertClassLesson.getErrorMsg());
        return res;
    }    
}
