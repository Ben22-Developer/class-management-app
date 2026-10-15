package app.classmanagementappclient.Frontend.MyJTable.CustomCellEditor;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BtnEditCellEditor extends DefaultCellEditor
{
    private final JButton btn = new JButton();
    private final Table table;
    private final List<String>clmHeaders;
    private final TableEditDialog editJDialog;
    private final List<Integer> clmIndexToCheck;
    private String btnText;
    
    public BtnEditCellEditor(JCheckBox checkBox, Table table, List<String>clmHeaders, List<Integer> clmIndexToCheck, TableEditDialog editJDialog) {
        super(checkBox);
        this.table = table;
        this.clmHeaders = clmHeaders;
        this.editJDialog = editJDialog;
        this.clmIndexToCheck = clmIndexToCheck;
        addEvent();
    }

    private void addEvent ()
    {   
        btn.addActionListener((l) ->
        {
            Runnable r = () -> {
                JTable jTable = this.table.getjTable();
                int row = jTable.getSelectedRow();
                ArrayList<String> values = new ArrayList<>();

                clmIndexToCheck.forEach(e -> {
                    values.add( String.valueOf(jTable.getValueAt(row, e)) );
                });   
                
                if (editJDialog == null) {
                    stopCellEditing();
                    return;
                }
                editJDialog.setHeaders(clmHeaders);
                editJDialog.setClmIndexToCheck(clmIndexToCheck);
                editJDialog.setTable(table);
                editJDialog.setRow(row);
                editJDialog.setValues(values);            

                editJDialog.drawJDialog();

                stopCellEditing();
            };
            new FrontEndUtility().runOneFixedThread(r);
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected,int row, int column)
    {
        this.btnText = String.valueOf(value);
        this.btn.setText(btnText);
        this.btn.setBackground(Color.WHITE);
        return this.btn;
    }

    @Override
    public Object getCellEditorValue() {
        return btnText;
    }

    public TableEditDialog getEditJDialog() {
        return editJDialog;
    }
}

