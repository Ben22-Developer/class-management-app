package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.AddStudentViaExcel;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.ExcelManipulators.ExcelManipulators;
import app.classmanagementappclient.Frontend.ExcelManipulators.Reader.ExcelReader;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.SavedStudentsUtility;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AddExcelStudentsData extends SwingWorker<Object, Object>{
    
    private final SavedStudentsUtility utility;
    private final StringBuilder report = new StringBuilder();
    private List<List<String>> data;
    
    public AddExcelStudentsData(SavedStudentsUtility utility) {
        this.utility = utility;
    }
    
    @Override
    protected Object doInBackground() throws Exception {
        runn();
        dataPopulate();
        return null;
    }    
    
    private void runn () {
        File file = ExcelManipulators.getExcelFile();
        if (file != null)
            readExcelFile(file);
    }

    private void readExcelFile(File file) {
        
        var reader = ExcelReader.readExcelFile(file, 3);
        
        if (!reader.getReport().toString().isBlank())
            report.append (reader.getReport().toString()).append("\n");
        
        data = reader.getData();
        
        data.parallelStream().forEach(e -> e.removeFirst());
        
        if (data.get(0).get(0).equalsIgnoreCase("no"))
            data.removeFirst();
    }
    
    private void dataPopulate () {
        
        var tracker = utility.getProgressTracker();
        tracker.run();
        int i = 0;
        
        for (List<String> dataPiece : data) {
            
            var medium = new StudentMedium( new Student(dataPiece.get(0), dataPiece.get(1)));
            boolean res = medium.insertStudentNoShowMsg();
            
            if (res)
                utility.populateUserInputs(dataPiece);
            else
                report.append(medium.getMsg()).append("\n");
            
            i += 1;
            tracker.setLoadingPercentage(i, dataPiece.size());
        }
        tracker.endProgress();        
    }

    @Override
    protected void done() {
        new ShowReport().showReport(report.toString(), "All students inserted successfully!");
    }
}
