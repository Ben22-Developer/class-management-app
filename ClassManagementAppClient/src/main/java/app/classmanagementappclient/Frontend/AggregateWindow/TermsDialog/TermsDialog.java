package app.classmanagementappclient.Frontend.AggregateWindow.TermsDialog;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.Common.TermsDialogSubmitHandler;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */
public abstract class TermsDialog {
    
    private final String schoolName;
    private final String className;
    private final int year;
    private final TermsDialogSubmitHandler window;
    
    public TermsDialog(String schoolName, String className, int year, TermsDialogSubmitHandler window) {
        this.schoolName = schoolName;
        this.className = className;
        this.year = year;
        this.window = window;
    }
    
    public void classTermDialog () {
        
        List<Integer> terms = new ClassTermMedium().getClassYearTerms(schoolName, className, year);
        
        if (terms.isEmpty()) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("The class selected dont't exist\nor does not have a registered term", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        load(terms);
    }    
    
    public void load ( List<Integer> terms ) {
        SwingUtilities.invokeLater(() ->{
            classTermDialogLoad(new TableDisplay(), terms);
        });
    }
    
    private void classTermDialogLoad (TableDisplay display, List<Integer> terms) {
        
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("", "Terms"), List.of(1), getFormattedTermList(terms));
        
        display.getTitleLabel().setText("Select Class Terms");
        display.getScrollPaneContainer().setViewportView(table.getjTable());
        JComponent[] components = Table.getCommonBtnsViewWithEvent(table);
        JButton submitBtn = getSubmitButton(table, display);
        FrontEndUtility.addJComponentToPanel(display.getSouthPanel(), new JComponent[] { components[0], components[1], submitBtn });
        display.setCustomTableDisplayProperties(400, 100, new Dimension(600, 500));
        display.setTitle("Class Terms");
        display.useJDialog(table);
    }
    
    private List<List<String>> getFormattedTermList (List<Integer> terms) {
        return terms.parallelStream().map(e ->  {
            
            List<String> a = new ArrayList<>();
            a.add(String.valueOf(e));
            return a;
        }).toList();
    }
    
    private JButton getSubmitButton (Table table, TableDisplay display) {
        
        JButton submitButton = new JButton("Submit");
        FrontEndUtility.setJComponentDefaultFont(submitButton);
        
        submitButton.addActionListener(l -> {
            
            List<List<String>> list = table.getCheckedRowsData();
            if (list.isEmpty())
                new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("You have not selected at least 1 term!", "Warning Message", JOptionPane.WARNING_MESSAGE);
            else
                fetchClassTermNums(list, display);
        });
        return submitButton;
    }
    
    private void fetchClassTermNums (List<List<String>> list, TableDisplay display) {
        
        var thisObj = this;
        
        new SwingWorker<Object, Object> () {
            
            private String msg = "";
            private final ConcurrentHashMap<Integer, Integer> termNumsToTerm = new ConcurrentHashMap<>();
            
            @Override
            protected Object doInBackground() throws Exception {
                
                list.parallelStream().forEach(e -> {
                    
                    int term = Integer.parseInt(e.get(0));
                    
                    termNumsToTerm.put(new ClassTermMedium().getOneClassTermNumV1(schoolName, className, year, term), term);
                });
                
                msg = window.setDetails(termNumsToTerm, display, thisObj);
                return null;
            }

            @Override
            protected void done() {
                if (!msg.isEmpty())
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(msg, "Informational Message", JOptionPane.INFORMATION_MESSAGE);
                else
                    window.setTerm(termNumsToTerm.values().iterator());
                
                
            }
        }.execute();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getClassName() {
        return className;
    }

    public int getYear() {
        return year;
    }

    public AggregateWindow getAggregateWindow() {
        return (AggregateWindow) window;
    }
    
    public StatisticsWindowDialog getStatisticsWindow() {
        return (StatisticsWindowDialog) window;
    }
    
    public abstract String setClassLessonsTable(ConcurrentHashMap<Integer, Integer> termNumsToTerm);
}
