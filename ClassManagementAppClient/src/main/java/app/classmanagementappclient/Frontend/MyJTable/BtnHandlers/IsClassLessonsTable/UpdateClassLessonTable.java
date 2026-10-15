package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsClassLessonsTable;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class UpdateClassLessonTable {

    private final List<String> currLessonData;
    private final List<String> newLessonData;
    private final Table table;
    private final int row;
    
    public UpdateClassLessonTable (Table table, int row, List<String> oldStudentData, List<String> newStudentData) {
        this.currLessonData = oldStudentData;
        this.newLessonData = newStudentData;
        this.table = table;
        this.row = row;
    }

    public boolean run () {

        final Table myTable = table;
        
        SwingWorker sw = new SwingWorker<Boolean, List<String>>() 
        {
            @Override
            protected Boolean doInBackground() throws Exception {
               
                ClassLessonsTable table = (ClassLessonsTable) myTable;
                
                int classTermNum = table.getClassTermNum();
                ClassLesson currLesson = new ClassLesson (currLessonData.get(0));
                ClassLesson newLesson = new ClassLesson (newLessonData.get(0));
                
                for (ClassLesson lesson : new ClassLesson[] { currLesson, newLesson })
                    lesson.setClassTermNum(classTermNum);
                boolean res = new ClassLessonMedium().updateClassLessonData(currLesson, newLesson);
                if (res)    
                    publish(List.of(newLessonData.get(0)));
                return res;
            }

            @Override
            protected void process(List<List<String>> chunks) { 
                for (List<String> chunk : chunks)
                    table.setValueAt(row, chunk);
            }
        };
        
        sw.execute();
        Boolean bool = null;
        try {
            bool = (Boolean) sw.get();
        } catch (Exception ex) {} 
        
        return bool;
    }    
}
