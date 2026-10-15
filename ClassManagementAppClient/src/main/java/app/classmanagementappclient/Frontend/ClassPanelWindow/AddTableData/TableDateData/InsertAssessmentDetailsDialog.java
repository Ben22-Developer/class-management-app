package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable.AssessmentDialogHepler;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertAssessmentDetailsDialog extends DateChooserDialog {
    
    private Object[] row;
    private int lessonNum;
    private int rowCount;
    
    public InsertAssessmentDetailsDialog (int lessonNum, int rowCount) {
        this.lessonNum = lessonNum;
        this.rowCount = rowCount;
    } 
    
    @Override
    public void drawJDialog() {
        setWindowTitle("New Assessment");
        setTitleLable("Add");
        setLocationPoint(new Point (650, 250));
        super.drawJDialog();
    }
    
    @Override
    protected void addCenterPanel() {
        
        JDialog dialog = getDialog();
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        var helper = new AssessmentDialogHepler();
        
        JPanel panel = helper.getPanel(this,"Assessment Name:", "");
        JPanel panel1 = helper.getPanel("Set Date:", super.getDateChooser());
        JPanel panel2 = helper.getPanel(this,"Total Marks:","");
        
        centerPanel.add(panel, BorderLayout.NORTH);
        centerPanel.add(panel1, BorderLayout.CENTER);
        centerPanel.add(panel2, BorderLayout.SOUTH);
        
        dialog.add(centerPanel, BorderLayout.CENTER);
    }
    
    public boolean submitHandler () {
        
        List<String> userInputs = super.getUserInputs();
        
        String assessmentName = userInputs.get(0);
        String totalMarks = userInputs.get(1);
        Date date = super.getDate();
        
        AssessmentMedium assessmentMedium = new AssessmentMedium();
        boolean res = assessmentMedium.insertAssessment(lessonNum, assessmentName, date, totalMarks);
        
        if (!res)
            return false;
        
        Object[] row = List.of( false, rowCount + 1, assessmentName, FrontEndUtility.getAppFormatDate(date), totalMarks, "View" ).toArray();
        setRow(row);
        super.setSubmitted(true);
        notifySleepers();
        return true;
    }
    
    public synchronized Object[] getObjects () {
        
        while (!super.isSubmitted()) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        System.out.println("end");
        return row;
    }
    
    @Override
    public synchronized void notifySleepers () {
        notifyAll();
    }

    public void setRow(Object[] row) {
        this.row = row;
    }
}
