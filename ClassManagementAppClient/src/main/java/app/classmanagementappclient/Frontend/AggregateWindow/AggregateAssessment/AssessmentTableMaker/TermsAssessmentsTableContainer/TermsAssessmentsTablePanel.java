package app.classmanagementappclient.Frontend.AggregateWindow.AggregateAssessment.AssessmentTableMaker.TermsAssessmentsTableContainer;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.TermsAssessmentsTable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public class TermsAssessmentsTablePanel {
    
    private final ConcurrentHashMap<Integer, Integer> termNums;
    private final AggregateWindow aggregateWindow;
    
    public TermsAssessmentsTablePanel (ConcurrentHashMap<Integer, Integer> termNums, AggregateWindow aggregateWindow) {
        this.termNums = termNums;
        this.aggregateWindow = aggregateWindow;
    }

    
    public String run () {
        
        SwingWorker sw = new SwingWorker<String,  List<List<String>>>  () {
            @Override
            protected String doInBackground() throws Exception {
                
                List<List<String>> data = getAssessmentsInTerms();
                
                if (data.isEmpty())
                    return "The class selected has no assessments!";
                
                data.forEach(e -> {
                    e.set (4, FrontEndUtility.getAppFormatDate(e.get(4)));
                });
                
                setTermsAssessmentsTable(data);
                return "";
            }
        };
        
        sw.execute();
        try {
            return String.valueOf(sw.get());
        } catch (Exception e){}
        return "";
    }
    
    private List<List<String>> getAssessmentsInTerms () {
        List<Integer> values = Collections.list(termNums.keys());
        return new AssessmentMedium().getAssessmentsInTerms(values);
    }
    
    private void setTermsAssessmentsTable ( List<List<String>> data ) {
        
        var table = TermsAssessmentsTable.getTermsAssessmentsTable(aggregateWindow, data, 200);
        JPanel termsAssessmentsTablePanel = getTermsAssessmentsTablePanel(table);
        aggregateWindow.setJSplitPaneBottomComponent(termsAssessmentsTablePanel);
        FrontEndUtility.componentValidate(aggregateWindow.getSplitPane());
        aggregateWindow.enableSelectTermBtnOnAssessment(false);
    }
    
    private JPanel getTermsAssessmentsTablePanel (TermsAssessmentsTable table) {
        
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table.getjTable());    
        panel.add(scrollPane);
        return panel;
    }    
}
