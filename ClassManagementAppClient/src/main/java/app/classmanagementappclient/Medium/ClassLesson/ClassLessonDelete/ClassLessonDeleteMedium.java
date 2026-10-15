package app.classmanagementappclient.Medium.ClassLesson.ClassLessonDelete;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Database.Delete.ClassLesson.ClassLessonDelete;

import java.sql.SQLException;

/**
 *
 * @author BEN
 */
public class ClassLessonDeleteMedium {
    
    public boolean deleteClassLesson (ClassLessonMedium classLessonMedium, int classLessonNum) {
        
        ClassLessonDelete classLessonDelete = null;
        boolean res = false;
        
        try {
            classLessonDelete = new ClassLessonDelete(classLessonNum);
            res = classLessonDelete.deleteData();
        }catch (SQLException ex) {
            System.getLogger(ClassLessonDeleteMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if (!res)
            classLessonMedium.setMsg(classLessonDelete.getErrorMsg());

        return res;
    }     
}
