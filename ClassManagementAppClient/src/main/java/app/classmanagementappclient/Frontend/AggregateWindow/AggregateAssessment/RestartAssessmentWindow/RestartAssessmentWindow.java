package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.RestartAssessmentWindow;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;

/**
 *
 * @author BEN
 */
public class RestartAssessmentWindow {
    
    private final AggregateWindow aggregateWindow;

    public RestartAssessmentWindow(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    public void run () {
        aggregateWindow.emptifyAssessmentDSAs();
        uiUpdate();
    }
    
    private void uiUpdate () {
        splitPaneRevalidate();
        aggregateWindow.enableSelectTermBtnOnAssessment(true);
    }
    
    private void splitPaneRevalidate () {
        aggregateWindow.setJSplitPaneTopComponent(null);
        aggregateWindow.setJSplitPaneBottomComponent(null);
        FrontEndUtility.componentValidate(aggregateWindow.getSplitPane());
    }
}
