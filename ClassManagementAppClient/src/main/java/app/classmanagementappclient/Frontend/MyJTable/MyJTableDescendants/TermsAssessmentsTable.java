package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.MyJTable.CustomCellEditor.BtnEditCellEditor;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.RowIdentifierRemoval.RowIdentifierRemoval;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs.TermAssessmentsTableDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TermsAssessmentsTable extends Table {
    
    private final List<Integer> assessmentNums = new ArrayList<>();
    private final AggregateWindow aggregateWindow;

    public TermsAssessmentsTable(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    public List<Integer> getAssessmentNums() {
        return assessmentNums;
    }

    @Override
    protected void setBtnEditCellEditor() {
        setBtnEditCellEditor(new BtnEditCellEditor(new JCheckBox(), this, getClmHeaders(), getClmsToCheck(), new TermAssessmentsTableDialog(aggregateWindow)));
    }
    
    public static TermsAssessmentsTable getTermsAssessmentsTable (AggregateWindow aggregateWindow, List<List<String>> data, int...clmWidth) {
        TermsAssessmentsTable table = new TermsAssessmentsTable(aggregateWindow);
        new RowIdentifierRemoval().populateIdentifierNums(data, table.getAssessmentNums(), data.get(0).size() - 1);
        table.setup(table, EditableTableType.IsAssessmentRecordsTable, false, true, "Set", List.of("Num", "Term", "Lesson Name", "Assessment Name", "Assessment Date", "Total Marks", "Set"), List.of(1,2,3,4,5), data, clmWidth);
        return table;
    }
}
