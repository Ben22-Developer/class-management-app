package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTermsDialog;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.AggregateAssessmentPanelMaker;
import app.classmanagementappclient.Frontend.AggregateWindow.TermsDialog.TermsDialog;
import app.classmanagementappclient.Frontend.Common.TermsDialogSubmitHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class AssessmentTermsDialog extends TermsDialog {

    public AssessmentTermsDialog(String schoolName, String className, int year, TermsDialogSubmitHandler aggregateWindow) {
        super(schoolName, className, year, aggregateWindow);
    }

    @Override
    public String setClassLessonsTable(ConcurrentHashMap<Integer, Integer> termNumsToTerm) {
        return new AggregateAssessmentPanelMaker(termNumsToTerm, super.getAggregateWindow()).run();
    }
}
