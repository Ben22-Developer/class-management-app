package app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Object;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadClassLessonObject extends ReadDataInterface {
    @Override
    public List<ClassLesson> getData();
}
