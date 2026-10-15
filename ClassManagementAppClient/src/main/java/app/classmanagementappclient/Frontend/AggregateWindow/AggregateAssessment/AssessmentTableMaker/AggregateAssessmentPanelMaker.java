package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.ReportClms.ReportClmsHandler;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.TermsAssessmentsTableContainer.TermsAssessmentsTablePanel;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class AggregateAssessmentPanelMaker {
    
    private final ConcurrentHashMap<Integer, Integer> termNums;
    private final AggregateWindow aggregateWindow;
    
    public AggregateAssessmentPanelMaker(ConcurrentHashMap<Integer, Integer> termNums, AggregateWindow aggregateWindow) {
        this.termNums = termNums;
        this.aggregateWindow = aggregateWindow;
    }
    
    public String run () {
        
        String string = new TermsAssessmentsTablePanel(termNums, aggregateWindow).run();
        if (!string.isBlank())
            return string;
        
        addClmsHandler();
        return "";
    }
    
    private void addClmsHandler () {
        
        var handler = new ReportClmsHandler(aggregateWindow);
        handler.run();
    }
}
