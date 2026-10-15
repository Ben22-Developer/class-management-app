package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.GenerateReport;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.GenerateReport.ReportForm.ReportForm;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.ExcelManipulators.Writer.ExcelWriter1;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author BEN
 */
public class GenerateAssessmentReport {
    
    private final AggregateWindow aggregateWindow;
    private final List<Integer> totalsPerRows = new ArrayList<>();
    private final List<Integer> totalsPerClms = new ArrayList<>();
    
    private List<Integer> classTermNums; 
    private List<List<String>> students;
    private final List<List<List<List<Integer>>>> assessmentNums = new ArrayList<>();
    
    private final List<String> title = new ArrayList<>();
    
    private enum printEnums {printClassTermNums, printStudentNums, printAssessmentData, printTitleData};
    
    private final Lock printLock = new ReentrantLock(true);
    
    public GenerateAssessmentReport(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    public void run () {
        
        List<List<List<List<String>>>> data = new ArrayList<>();
        
        if (!setReportStructure (data)) 
            return;
        
        if (!setFields(data))
            return;
        
        reportGenerate(data);
    }
    
    private boolean setReportStructure (List<List<List<List<String>>>> data) {
        
        List<List<Table>> reportStructure = aggregateWindow.getStudentReportStructure();
        
        if (reportStructure.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Can't generate report with an empty report structure", "Warning Message", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        StringBuilder missingData = new StringBuilder();
        JTable reportStructureTable = aggregateWindow.getReportStructureTable();
        
        int currRow = 1;
        
        for (List<Table> rowTables : reportStructure) { // get a row of a lesson (it's view)
            
            String lessonName = (String) reportStructureTable.getValueAt(currRow - 1, 1);
            
            int totalMarksPerRow = 0; // a lesson total marks
            int currClm = 1;
            
            List<List<List<String>>> rowData = new ArrayList<>();
            
            for (Table table : rowTables) { // get 1 table that u can see when u click a view
                
                boolean hasLooped = false;
                
                int totalMarksPerClm = 0;
                
                List<List<String>> tableData = new ArrayList<>();
                
                for (int i = 0; i < table.getjTable().getRowCount(); i++) { // loop in 1 table
                    
                    List<String> rowDataList = new ArrayList<>();
                    rowDataList.add(lessonName);
                    
                    for (String dataPiece : table.getRowDataAt(i))
                        rowDataList.add(dataPiece);
                    
                    totalMarksPerRow += Integer.parseInt(table.getRowDataAt(i).getLast());
                    totalMarksPerClm += Integer.parseInt(table.getRowDataAt(i).getLast());
                    
                    tableData.add(rowDataList);
                    hasLooped = true;
                }
                
                if (!hasLooped)
                    missingData.append("Lesson: ").append(lessonName).append("\n").append(" At row: ").append(currRow).append(" Column: ").append(currClm).append("\n\n");
                
                if (missingData.isEmpty())
                    rowData.add(tableData);
                
                if (totalsPerClms.size() <= (currClm - 1))
                    totalsPerClms.add(totalMarksPerClm);
                else
                    totalsPerClms.set(currClm - 1, totalsPerClms.get(currClm - 1) + totalMarksPerClm);
                
                currClm += 1;
            }
            
            totalsPerRows.add(totalMarksPerRow);
            data.add(rowData);
            currRow += 1;
        }
        
        if (!missingData.isEmpty()) {
            new ShowReport().showReport("For getting reports provide the missing information at: \n\n" + missingData.toString(), "", true);
            return false;
        }
        
        System.out.println("\n\n");
        data.forEach(System.out::println);
        System.out.println("\n\nclms:");
        totalsPerClms.forEach(e -> System.out.print(e +"\t"));
        System.out.println("\n\n\nrows:");
        totalsPerRows.forEach(System.out::println);
        
        return true;
    }
    
    private boolean setFields (List<List<List<List<String>>>> data) {
        
        Runnable runnable = () -> {
            setClassTermNums();
            setStudents();
        };

        Runnable runnable1 = () -> {
            setAssessmentNums(data);
        };

        Runnable runnable2 = () -> {
            setTitle();
        };        
        
        ExecutorService executors = Executors.newFixedThreadPool(3);
        executors.submit(runnable);
        executors.submit(runnable1);
        executors.submit(runnable2);
        executors.shutdown();
        
        boolean terminated = false;
        
        try {
            terminated = executors.awaitTermination(5, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.getLogger(GenerateAssessmentReport.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return terminated;
    }
    
    private void setClassTermNums () {
        
        String schoolName = getSchoolName();
        String className = getClassName();
        int year = getYear();        
        
        classTermNums = new ClassTermMedium().getOneClassTermNumV2(schoolName, className, year, aggregateWindow.getAssessmentClassTermNums());
        fieldPrint(printEnums.printClassTermNums);
    }

    private void setStudents () {
        students = new StudentClassMedium().getAllClassStudentsV1(classTermNums);
        fieldPrint(printEnums.printStudentNums);
    }      
    
    private void setAssessmentNums (List<List<List<List<String>>>> data) {
        
        String schoolName = getSchoolName();
        String className = getClassName();
        int year = getYear(); 
        
        for (List<List<List<String>>> bigTableRow : data) {
            
            List<List<List<Integer>>> assessmentBigTableRowInt = new ArrayList<>();
            assessmentNums.add(assessmentBigTableRowInt);
            
            for (List<List<String>> smallTable : bigTableRow) {
            
                List<List<Integer>> smallTableInt = new ArrayList<>();
                assessmentBigTableRowInt.add(smallTableInt);
                
                for (List<String> smallTableRow : smallTable) {
                    
                    List<Integer> smallTableRowInt = new ArrayList<>();
                    smallTableInt.add(smallTableRowInt);
                    
                    int term = Integer.parseInt(smallTableRow.get(1));
                    String lessonName = smallTableRow.get(0);
                    String assessmentName = smallTableRow.get(2);
                    Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate(smallTableRow.get(3));
                    int row = new AssessmentMedium().getAssessmentNums(schoolName, className, year, term, lessonName, assessmentName, date);
                    smallTableRowInt.add(row);
                }
            }
        }
        fieldPrint(printEnums.printAssessmentData);
    }
    
    private void setTitle () {
        
        JTableHeader header = aggregateWindow.getReportStructureTable().getTableHeader();
        int j = 0;
        int allClmsTotalMarks = 0;
        
        for (int i = 1; i < header.getColumnModel().getColumnCount(); i++) {
            
            String clmName = (String) header.getColumnModel().getColumn(i).getHeaderValue();
            
            if (i == 1) {
                title.add(clmName);
                continue;
            }
            
            int totalClmMarks = totalsPerClms.get(j);
            title.add(clmName + "       /" + totalClmMarks +" Marks");
            allClmsTotalMarks += totalClmMarks;
            j += 1;
        }
        
        totalsPerClms.add(allClmsTotalMarks);
        title.add("Total       /" + allClmsTotalMarks +" Marks");
        title.add("Percentage (%)");
        title.add("Rank");
        fieldPrint(printEnums.printTitleData);
    }

    private void fieldPrint (printEnums code) {
        
        printLock.lock();
        
        try {
            switch (code) {
                case printClassTermNums -> {
                    System.out.println("\n\nClass Term Nums:");
                    System.out.println(classTermNums);
                }
                case printStudentNums -> {
                    System.out.println("\n\nStudents:");
                    students.forEach(System.out::println);
                }
                case printAssessmentData -> {
                    System.out.println("\n\nAssessment nums:");
                    assessmentNums.forEach(System.out::println);                    
                }
                case printTitleData -> {
                    System.out.println("\n\nTitle Data:");
                    title.forEach(e -> { System.out.print(e + "\t"); });                    
                }                
                default -> throw new AssertionError();
            }
        } finally {
            printLock.unlock();
        }
    }   
    
    private void reportGenerate (List<List<List<List<String>>>> data) {
        
        Map<String, ReportForm> studentIDToReport = new HashMap<>();
        
        setReportForms(studentIDToReport);
        populateReportBody(studentIDToReport, data);
    }
    
    private void setReportForms (Map<String, ReportForm> studentIDToReport) {
        
        for (List<String> studentDetails : students) {
            
            var reportForm = new ReportForm(getSchoolName(), getClassName(), getYear(), aggregateWindow.getAssessmentClassTermNums(), studentDetails.get(0), studentDetails.get(1));
            studentIDToReport.put(studentDetails.get(0), reportForm);
        }        
    }
    
    private void populateReportBody (Map<String, ReportForm> studentIDToReport, List<List<List<List<String>>>> data) {
        
        int i =0;
        
        for (List<List<List<Integer>>> bigTableRow : this.assessmentNums) {
            
            String lessonName = data.get(i).get(0).get(0).get(0);
            List<List<Integer>> rowAssessmentNums = new ArrayList<>();
            
            for (List<List<Integer>> smallTable : bigTableRow) {
            
                List<Integer> smallTableRowsInt = new ArrayList<>();
                
                for (List<Integer> smallTableRows : smallTable) 
                
                    for (int smallTableRow : smallTableRows)
                        smallTableRowsInt.add(smallTableRow);
                
                rowAssessmentNums.add(smallTableRowsInt);
            }
            boolean isLast = assessmentNums.getLast().equals(bigTableRow);
            if (!setReportBody(studentIDToReport, rowAssessmentNums, lessonName, i, isLast))
                break;
            i += 1;
        }
        
//        System.out.println("\n\n");      
        
        List<ReportForm> sortedForms = getSortedReportForms (studentIDToReport.values().parallelStream().toList());
        
//        sortedForms.forEach(e -> {
//            e.getTitle().forEach(System.out::println);
//            title.forEach(f-> {
//                System.out.print(f +"\t");
//            });
//            System.out.println("\n");
//            e.getReportBody().forEach(System.out::println);
//            System.out.println("Position: "+e.getPosition());
//            
//            e.getAggregateLessonRow().forEach(f-> {
//                System.out.print(f +"\t");
//            });
//            System.out.println("\n\n");
//        });
        
        exportReports(sortedForms);
    }
    
    private boolean setReportBody (Map<String, ReportForm> studentIDToReport, List<List<Integer>> rowAssessmentNums, String lessonName, int currIndex, boolean isLast) {
        
//        System.out.println("\nUko biri kugenda bisa: "+rowAssessmentNums);
        
        List<List<String>> reportRow = new AssessmentMedium().getReportRow(rowAssessmentNums, classTermNums);
        
        ExecutorService service = Executors.newCachedThreadPool();
        
        for (List<String> row : reportRow) {
            
            String studentID = getStudentID(row);
            List<Double> studentMarks = getStudentRowMarks(row);
            
            Runnable runnable = () -> {
                processRowReport(studentIDToReport.get(studentID), lessonName, studentMarks, totalsPerRows.get(currIndex), isLast);
            };
            service.execute(runnable);
        }
        service.shutdown();
        boolean terminated = false;
        try {
            terminated = service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.getLogger(GenerateAssessmentReport.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return terminated;
    }

    private String getStudentID (List<String> rowDetails) {
        return rowDetails.get(0);
    }
    
    private List<Double> getStudentRowMarks (List<String> rowDetails) {
        
        List<Double> studentMarks = new ArrayList<>();
        
        for (int i = 2; i < rowDetails.size(); i++)
            studentMarks.add(Double.parseDouble(rowDetails.get(i)));
        
        return studentMarks;
    }
    
    private void processRowReport (ReportForm reportForm, String lessonName, List<Double> rowDetails, int rowAggregate, boolean isLast) {
        reportForm.addRow(lessonName, rowDetails, rowAggregate, isLast, totalsPerClms.getLast());
    }
    
    private List<ReportForm> getSortedReportForms (List<ReportForm> reportForms) {
        
        Comparator<ReportForm> comparator = (o1, o2) -> 
        {
            return o1.getTotalStudentPercentage() > o2.getTotalStudentPercentage() ? -1 : 1;
        };
        
        List<ReportForm> sortedReportForms = reportForms.parallelStream().sorted(comparator).toList();
        
        for (int i = 0; i < sortedReportForms.size(); i++) {
            sortedReportForms.get(i).setPosition(i + 1);
            sortedReportForms.get(i).setAggregateLessonRow(totalsPerRows);
        }
        return sortedReportForms;
    }
    
    private void exportReports (List<ReportForm> sortedReportForms) {
        
        ExcelWriter1 writer = null;
        
        for (ReportForm form : sortedReportForms)
            writer = form.excelExport(title, writer, form.equals(sortedReportForms.getLast()));
        
    }
    
    private String getSchoolName () {
        return (String)aggregateWindow.getRecordedSchoolNewStudentComboBox_AssessmentPanel().getSelectedItem();
    }
    
    private String getClassName () {
        return (String)aggregateWindow.getRecordedClassNewStudentComboBox_AssessmentPanel().getSelectedItem();
    }

    private int getYear () {
       return Integer.parseInt((String) aggregateWindow.getRecordedYearNewStudentComboBox_AssessmentPanel().getSelectedItem() ); 
    }    
}
