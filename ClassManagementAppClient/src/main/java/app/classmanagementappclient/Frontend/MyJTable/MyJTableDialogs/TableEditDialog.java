package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAddNewLessonTable.NewLessonTableBtnCellEditorHandler;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAddNewStudentsTable.AddNewStudentTableBtnCellEditorHandler;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAssessmentRecordsTable.UpdateAssessmentRecordsTable;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsClassLessonsTable.UpdateClassLessonTable;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsScheduleTable.UpdateScheduleTable;
import app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsStudentsTable.UpdateStudentTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class TableEditDialog extends ADialog
{
    private Table table;
    private int row;
    private List<Integer> clmIndexToCheck;
    
    @Override
    protected void addCenterPanel ()
    {   
        List<JPanel> smallPanels = new ArrayList<>();
        
        List<String> headers = super.getHeaders();
        List<String> values = super.getSelectedRowData();
        List<JTextField> textFields = super.getTextFields();
        JDialog dialog = super.getDialog();
        
        for (int i = 0; i < clmIndexToCheck.size(); i++) 
        {
            int index = clmIndexToCheck.get(i);
            String labelTxt = headers.get(index);
            String value = values.get(i);
            JLabel label = new JLabel(labelTxt);
            JTextField textField = new JTextField (value);
            setJComponent(label,  new Dimension(120,40));
            FrontEndUtility.setJTextField(textField);
            Dimension dimension = new Dimension(500,50);
            JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
            setJPanel(jPanel, new JComponent[] { label, textField}, dimension);
            textFields.add(textField);
            smallPanels.add(jPanel);
        }
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(smallPanels.size(), 1, 0, 5));
        smallPanels.forEach((JPanel panel) -> {
            bigPanel.add(panel);
        });
        
        dialog.add(bigPanel, BorderLayout.CENTER);
    }
    
    @Override
    protected void handleSubmitEvent () 
    {
        List<String> txtFieldRes = super.getUserInputs();
        List<JTextField> textFields = super.getTextFields();
        JDialog dialog = super.getDialog();        
        
        textFields.forEach((textField) -> {
            txtFieldRes.add(textField.getText());
        });
        
        if (!handler()) 
            return;
        
        dialog.dispose();
        dialog = null;
    }
    
    @Override
    public boolean handler()
    {
        List<String> userInputs = super.getUserInputs();
        EditableTableType editableTableType = table.getTableType();
        boolean isValid;
        
        isValid = switch (editableTableType) {
            case IsAddNewLessonTable -> new NewLessonTableBtnCellEditorHandler().addNewLessonTable(userInputs.getFirst(), row, clmIndexToCheck.getFirst(), table.getjTable());
            case IsAddNewStudentsTable -> new AddNewStudentTableBtnCellEditorHandler(table, row, clmIndexToCheck, userInputs).validate();
            case IsSavedStudentsTable -> new UpdateStudentTable (table, row,super.getSelectedRowData(), userInputs).run();
            case IsClassLessonsTable ->  new UpdateClassLessonTable (table, row,super.getSelectedRowData(), userInputs).run();
            case IsAssessmentRecordsTable -> new UpdateAssessmentRecordsTable (table, row, super.getSelectedRowData(), userInputs).run();
            case IsScheduleTable -> new UpdateScheduleTable(getSelectedRowData(), userInputs, table, row).run();
            default -> throw new AssertionError("Waguan wtf bro\nAt TableEditDialog.handler!");
        };
            
        userInputs.clear();
        return isValid;
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<Integer> getClmIndexToCheck() {
        return clmIndexToCheck;
    }

    public void setClmIndexToCheck(List<Integer> clmIndexToCheck) {
        this.clmIndexToCheck = clmIndexToCheck;
    }
}
