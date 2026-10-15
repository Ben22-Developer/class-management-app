package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable;

import app.classmanagementappclient.Medium.AssessmentRecord.AssessmentRecordMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Tables.AssessmentRecordsTableManager;
import app.classmanagementappclient.Frontend.ExcelManipulators.ExcelManipulators;
import app.classmanagementappclient.Frontend.ExcelManipulators.Reader.ExcelReader;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarner;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarning;
import app.classmanagementappclient.Frontend.ExcelManipulators.Writer.ExcelWriter;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 *
 * @author BEN
 */
public class MultipleAssessmentRecordsInsert implements ExcelWarner {
    
    private final AssessmentRecordsTableManager tableManager;

    public MultipleAssessmentRecordsInsert(AssessmentRecordsTableManager tableManager) {
        this.tableManager = tableManager;
    }

    public void run () {
        
        if (FrontEndUtility.canDisplayWarningMsg("MultipleStudentAssessmentRecordWarning"))
            showWarning();
        else
            afterWarnRun();
        
    }
    
    private void showWarning () {
        
        JCheckBox noWarningCheckBox = new JCheckBox("Understood please don't display this message again!");
        JLabel warningLabel = new JLabel("<html><div style=\"display:flex; align-items: center;\"><p style=\"text-align:center\"><br><span style=\"color:red;\"><ins>Warning:</ins></span> An excel file containing students assessments records<br>must be in 4 columns <br><br> <span style=\"font-weight:bold\">Format example</span></p><br><table style=\"border: 1px solid; border-collapse: collapse; margin:auto;\"><th style=\"border: 1px solid; border-collapse: collapse; width:50px\">No.</th><th style=\"border: 1px solid; border-collapse: collapse; width:150px\">Student Identity</th><th style=\"border: 1px solid; border-collapse: collapse; width:120px\">Student Names</th><th style = \"border: 1px solid; border-collapse: collapse; width:100px\">Student Marks/30</th><tr style=\"border: 1px solid;\"><td style=\"border: 1px solid; text-align:center;\">1</td><td style=\"border: 1px solid; text-align:center;\">Nursery1/2026/001</td><td style=\"border: 1px solid; text-align:center;\">Aldo Wise</td><td style=\"border: 1px solid; text-align:center;\">30</td></tr></table><br><p style=\"text-align:center;\">The file extension must end up with '.xlsx' (MS Excel extension)</p><br></div></html>");
        JButton okButton = new JButton("Ok");
        JButton viewImageBtn = new JButton("An Image Example");        
        
        var excelWarning = new ExcelWarning(this, new JComponent[] { warningLabel }, new JComponent[] { noWarningCheckBox }, new JComponent[] { viewImageBtn, okButton });
        excelWarning.addConfirmBtnEvent(okButton, noWarningCheckBox, "MultipleStudentAssessmentRecordWarning");
        excelWarning.addDisposeListener();
        excelWarning.execute();        
    }
    
    @Override
    public void afterWarnRun() 
    {
        SwingUtilities.invokeLater(() -> {
        
            JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER, 30, 20));
            MyJDialog dialog = new MyJDialog("I\\O Assessments Record");
            JRadioButton input = new JRadioButton("Input Excel File");
            JRadioButton output = new JRadioButton("Output Unrecorded Students");
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(input);
            buttonGroup.add(output);
            setInputListener(input, dialog);
            setOutputListener(output, dialog);
            FrontEndUtility.setJComponentsCustomFont(new JComponent[] { input,output }, new Font("Arial", Font.PLAIN, 16));
            FrontEndUtility.addJComponentToPanel(panel, new JComponent[] {input, output});
            dialog.add(panel);
            dialog.setSize(new Dimension(420, 150));
            dialog.setLocation(550, 250);
            dialog.setVisible(true);            
        
        });
    }
    
    private void setOutputListener (JRadioButton output, MyJDialog dialog) {
        output.addActionListener(l -> {
            dialog.dispose();
            outputStudents();
        });
    }
    
    private void setInputListener (JRadioButton input, MyJDialog dialog) {
        input.addActionListener(l -> {
            dialog.dispose();
            inputStudents();
        });
    }

    private void inputStudents () {
        File file = ExcelManipulators.getExcelFile();
        if (file != null)
            readExcelFile(file);
    }
    
    private void readExcelFile (File file) 
    {
        if (!ExcelManipulators.isExcelFile(file))
            return;
        
        ExcelReader reader = ExcelReader.readExcelFile(file, 4);
        reader.getData().removeFirst();
        if (reader.getData().isEmpty())
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(reader.getEmptyListMsg(), "Warning Message", JOptionPane.WARNING_MESSAGE);
        else
            tableManager.multipleInsertion(reader.getData(), reader.getReport().toString());
    }    
    
    private void outputStudents () {
        
        List<List<String>> data = new AssessmentRecordMedium().getUnRecordedStudentsOnSpecificAssessment(tableManager.getIdentifierNum());
        if (data.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no student in this class", "Information Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        data.addFirst(List.of("No", "Student ID", "Student Name", "Student Marks"));
        ExcelWriter.writeToExcelFile("Test", data, "Test 1");        
    }
}
