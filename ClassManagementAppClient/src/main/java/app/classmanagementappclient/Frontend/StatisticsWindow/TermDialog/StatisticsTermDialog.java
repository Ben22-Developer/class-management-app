package app.classmanagementappclient.Frontend.StatisticsWindow.TermDialog;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.TermsDialog.TermsDialog;
import app.classmanagementappclient.Frontend.Common.TermsDialogSubmitHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author BEN
 */
public class StatisticsTermDialog extends TermsDialog {

    private List<List<String>> classLesson;
    private List<List<String>> classStudent;
    
    public StatisticsTermDialog(String schoolName, String className, int year, TermsDialogSubmitHandler window) {
        super(schoolName, className, year, window);
    }

    @Override
    public String setClassLessonsTable(ConcurrentHashMap<Integer, Integer> termNumsToTerm) {
        
        fetchAllClassLessons_AllClassStudents(termNumsToTerm.entrySet().stream().map(e -> e.getKey()).toList());
        
        String fetchReport = getFetchReport();
        
        if (fetchReport.length() != 0)
            return fetchReport;
        
        setWindowFields();
        
        return "";
    }
    
    private String getFetchReport () {
        
        var sb = new StringBuilder();
        sb.append(classLesson.isEmpty() ? "The class selected has no lessons!\n" : "");
        sb.append(classStudent.isEmpty() ? "The class selected has no students!" : "");
        
        return sb.toString();
    }
    
    private void setWindowFields () {
        
        var window = super.getStatisticsWindow();
        window.setAllClassLessons(classLesson);
        window.setAllClassStudents(classStudent);
    }

    private void fetchAllClassLessons_AllClassStudents (List<Integer> classTermNums)  {
        
        ExecutorService es = Executors.newFixedThreadPool(2);
        Runnable classLessonRunnable = () -> fetchAllClassLessons(classTermNums);
        Runnable classStudentRunnable = () -> fetchAllClassStudents(classTermNums);

        es.execute(classLessonRunnable);
        es.execute(classStudentRunnable);
        es.shutdown();
        
        try {
            es.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.getLogger(StatisticsTermDialog.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void fetchAllClassLessons (List<Integer> classTermNums) {
        classLesson = new ClassLessonMedium().getClassLessonsInTerm(classTermNums).parallelStream().map(e -> List.of(e.getLessonName())).toList();
    }
    
    private void fetchAllClassStudents (List<Integer> classTermNums) {
        classStudent = new StudentClassMedium().getAllClassStudentsV1(classTermNums);
    }    
     
}
