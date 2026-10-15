package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAttendance.AggregateTableMaker;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AggregateClassLessonsTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class AggregateAttendanceClassLessonsTableMaker {
   
    private final ConcurrentHashMap<Integer, Integer> termNumsToTerm;
    private final AggregateWindow aggregateWindow;
    
    public AggregateAttendanceClassLessonsTableMaker(ConcurrentHashMap<Integer, Integer> termNumsToTerm, AggregateWindow aggregateWindow) {
        this.termNumsToTerm = termNumsToTerm;
        this.aggregateWindow = aggregateWindow;
    }
    
    public String drawClassLessonsTable () {
        
        List<List<String>> data = getData();
        if (!data.isEmpty()) {
            displayTable(data);
            aggragateWindowDSAemptify();
        }
        return data.isEmpty() ? "The class selescted has no lessons to display!" : "";
    }
    
    private List<List<String>> getData () {
        
        List<Integer> termNums = Collections.list(termNumsToTerm.keys());
        
        List<ClassLesson> classLessons = new ClassLessonMedium().getClassLessonsInTerm(termNums);
        
        List<List<String>> data = new ArrayList<>();
        int i = 1;
        
        for (ClassLesson e : classLessons) {
            
            int lessonNum = e.getClassLessonNum();
            int term = termNumsToTerm.get( e.getClassTermNum() );
            String lessonName = e.getLessonName();
            
            List<String> dataPiece = new ArrayList<>();
            dataPiece.add(String.valueOf(i));
            dataPiece.add(String.valueOf(term));
            dataPiece.add(lessonName);
            dataPiece.add("0"); 
            dataPiece.add(String.valueOf(lessonNum));
            
            data.add(dataPiece);
            i += 1;
        }
        
        return data;
    }
    
    private void displayTable (List<List<String>> data) {
        
        AggregateClassLessonsTable table = AggregateClassLessonsTable.getAssessmentRecordsTable(data, aggregateWindow, 200);
        aggregateWindow.getAttendanceScrollPane().setViewportView(table.getjTable());
        FrontEndUtility.componentValidate(aggregateWindow.getAttendanceTablePanel());
    }
    
    private void aggragateWindowDSAemptify () {
        
        Set<Integer> tempAttendanceNums = aggregateWindow.getTempAttendanceNums();
        Set<Integer> attendanceNums = aggregateWindow.getAttendanceNums();
        
        tempAttendanceNums.clear();
        attendanceNums.clear();
    }
    
}
