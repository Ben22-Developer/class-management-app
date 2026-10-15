package app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataValidate.Implement;

import app.classmanagementappclient.Frontend.Exception.InvalidDataException;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataValidate.Contract.ChartValidatorContract;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

/**
 *
 * @author BEN
 */
public class ChartDataValidator implements ChartValidatorContract {

    private final StatisticsWindowDialog window;

    public ChartDataValidator(StatisticsWindowDialog window) {
        this.window = window;
    }        
    
    @Override
    public void validate() throws InvalidDataException {
        
        var sb = new StringBuilder();
        
        if (window.getSchoolBox().getSelectedItem() == null)
           sb.append("No school selected!\n");

        if (window.getClassBox().getSelectedItem() == null)
           sb.append("No class selected!\n");        
        
        if (window.getAcademicYearBox().getSelectedItem() == null)
           sb.append("No academic year selected!\n");

        if (window.getClassTerm() == null)
           sb.append("No term selected!\n");

        if (window.getSelectedClassLessons() == null)
           sb.append("No lesson selected!\n");

        if (window.getSelectedStudents() == null)
           sb.append("No student(s) selected!\n");

        if (window.getChartBox().getSelectedItem() == null)
           sb.append("No graph selected!\n");
        
        if (!sb.isEmpty())
            throw new InvalidDataException(sb.toString());
    }
    
}
