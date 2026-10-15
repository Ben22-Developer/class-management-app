package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.GenerateReport.ReportForm;

import app.classmanagementappclient.Frontend.ExcelManipulators.Writer.ExcelWriter1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReportForm {
    
    private final String schoolName; 
    private final String className; 
    private final int year; 
    private final List<Integer> terms; 
    private final String studentID;
    private final String studentName;
    private int position;
    private double totalStudentPercentage;
    private final List<String> aggregateLessonRow = new ArrayList<>();
    private final List<List<String>> reportBody = new ArrayList<>();
    private final String directory;
    private final String sheetName = "Position ";

    public ReportForm(String schoolName, String className, int year, List<Integer> terms, String studentID, String studentName) {
        this.schoolName = schoolName;
        this.className = className;
        this.year = year;
        this.terms = terms;
        this.studentID = studentID;
        this.studentName = studentName;
        this.directory = "Report for " + schoolName+" " + " "+ className+ " " + year+ " " + terms;
    }
    
    public List<List<String>> getTitle () {
        
        List<List<String>> title = new ArrayList<>();
        
        title.add(List.of("School", schoolName));
        title.add(List.of("Class", className));
        title.add(List.of("Year", String.valueOf(year)));
        title.add(List.of("Term(s)", terms.toString()));
        title.add(List.of("Report for 'Mr/Mrs' ", studentName, studentID));
        
        return title;
    }
    
    public void addRow (String lessonName, List<Double> rowDetails, int rowAggregate, boolean isLast, int totalOfTotal) {
        
        double totalStudentMarks = rowDetails.getLast();
        double percentage = (totalStudentMarks/rowAggregate) * 100;
        rowDetails.add(percentage);
        
        List<String> row = new ArrayList<>();
        row.add(lessonName);
        
        rowDetails.forEach(e -> {
            row.add(String.format("%.2f", e));
        });
        
        row.add(getRank(percentage));
        reportBody.add(row);
        if (isLast)
            computeLastRow(totalOfTotal);
    }
    
    private void computeLastRow (int totalOfTotal) {

        List<String> total = new ArrayList<>();
        double studTotalOfTotal = 0;
        total.add("Totals");
        
        for (int clm = 1; clm < reportBody.get(0).size() - 3; clm++) { // -3 rows the rows are for totals, percentage & grade
            
            double clmTotal = 0;
            
            for (int row = 0; row < reportBody.size(); row++)
                clmTotal += Double.parseDouble(reportBody.get(row).get(clm));
            
            studTotalOfTotal += clmTotal;
            total.add(String.valueOf(clmTotal));
        }

        totalStudentPercentage = (studTotalOfTotal/totalOfTotal) * 100;
        
        total.add(String.valueOf(studTotalOfTotal));
        total.add(String.format("%.2f", totalStudentPercentage));
        total.add(getRank(totalStudentPercentage));
        reportBody.add(total);
    }
    
    private String getRank (double percentage) {
        
        if (percentage >= 80)
            return "A";
        
        else if (percentage >= 70 && percentage < 80)
            return "B";
        
        else if (percentage >= 60 && percentage < 70) 
            return "C";
        
        else if (percentage >= 50 && percentage < 60) 
            return "D";
        
        else if (percentage >= 40 && percentage < 50)
            return "E";
        
        else if (percentage >= 30 && percentage < 40)
            return "F";
        
        else 
            return "U";
    }

    public void setAggregateLessonRow (List<Integer> totalMarksPerRow) {
        for (int i = 0; i < totalMarksPerRow.size(); i++)
            aggregateLessonRow.add(reportBody.get(i).get(0) + " /"+ totalMarksPerRow.get(i) + " marks");
    }
    
    
    public ExcelWriter1 excelExport (List<String> title, ExcelWriter1 writer1, boolean isLast) {
        
        ExcelWriter1 writer = writeTitle(writer1);
        
        writeReportClmsTitle(writer, title);
        writeReportBody(writer);
        writePosition(writer);
        writer.setOpenExcelFile(isLast); // u must set openExcelFile operation at the last point
        writeAggregateLessonRow(writer);
        
        return writer;
    }
    
    private ExcelWriter1 writeTitle (ExcelWriter1...writer) {
        
        if (position == 1)
            return ExcelWriter1.writeToExcelFile("Reports Docs", null, getTitle(), sheetName+" " + position, 0, true, directory);
        
        writer[0].setStartingRow(0);
        writer[0].setContent(getTitle());
        writer[0].setSheetName(sheetName + " " + position);
        writer[0].setSheet(null);
        writer[0].runProcess();
        return writer[0];
    }
    
    private void writeReportClmsTitle (ExcelWriter1 writer, List<String> title) {
        writer.setStartingRow(writer.getStartingRow() + 1);
        writer.setContent(List.of(title));
        writer.runProcess();        
    }
    
    private void writeReportBody (ExcelWriter1 writer) {
        writer.setStartingRow(writer.getStartingRow() + 1);
        writer.setContent(reportBody);
        writer.runProcess();        
    }
    
    private void writePosition (ExcelWriter1 writer) {
        writer.setStartingRow(writer.getStartingRow() + 1);
        writer.setContent(List.of(List.of("Position", String.valueOf(position))));
        writer.runProcess();        
    }    
    
    private void writeAggregateLessonRow (ExcelWriter1 writer) {
        writer.setStartingRow(writer.getStartingRow() + 1);
        writer.setContent(List.of(aggregateLessonRow));
        writer.runProcess();
    }
    
    public List<List<String>> getReportBody() {
        return reportBody;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getTotalStudentPercentage() {
        return totalStudentPercentage;
    }

    public List<String> getAggregateLessonRow() {
        return aggregateLessonRow;
    }

    public void setTotalStudentPercentage(double totalStudentPercentage) {
        this.totalStudentPercentage = totalStudentPercentage;
    }

}
