package app.classmanagementappclient.Frontend.ExcelManipulators.Warning.Warners;

import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarner;
import app.classmanagementappclient.Frontend.ExcelManipulators.Warning.ExcelWarning;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class ExcelStudentFileWarning {
    
    private final ExcelWarner warner;

    public ExcelStudentFileWarning(ExcelWarner warner) {
        this.warner = warner;
    }
    
    public void displayWarningMsg () {
        
        JCheckBox noWarningCheckBox = new JCheckBox("Understood please don't display this message again!");
        JLabel warningLabel = new JLabel("<html><div style=\"display:flex; align-items: center;\"><p style=\"text-align:center\"><br><span style=\"color:red;\"><ins>Warning:</ins></span> An excel file containing students must be in 3 columns <br><br> <span style=\"font-weight:bold\">Format example</span></p><br><table style=\"border: 1px solid; border-collapse: collapse; margin:auto;\"><th style=\"border: 1px solid; border-collapse: collapse; width:50px\">No.</th><th style=\"border: 1px solid; border-collapse: collapse; width:200px\">Student Identity</th><th style=\"border: 1px solid; border-collapse: collapse; width:200px\">Student Names</th><tr style=\"border: 1px solid;\"><td style=\"border: 1px solid; text-align:center;\">1</td><td style=\"border: 1px solid; text-align:center;\">Nursery1/2026/001</td><td style=\"border: 1px solid; text-align:center;\">Aldo Wise</td></tr></table><br><p style=\"text-align:center;\">The file extension must end up with '.xlsx' (MS Excel extension)</p><br></div></html>");
        JButton okButton = new JButton("Ok");
        JButton viewImageBtn = new JButton("An Image Example");        
        
        var excelWarning = new ExcelWarning(warner, new JComponent[] { warningLabel }, new JComponent[] { noWarningCheckBox }, new JComponent[] { viewImageBtn, okButton });
        excelWarning.addConfirmBtnEvent(okButton, noWarningCheckBox, "ExcelStudentFileFormatWarning");
        excelWarning.addImageExampleListener(viewImageBtn);
        excelWarning.addDisposeListener();
        excelWarning.execute();
    }    
}
