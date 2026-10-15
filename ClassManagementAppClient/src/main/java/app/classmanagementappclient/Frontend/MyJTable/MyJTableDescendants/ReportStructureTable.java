package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.MyJTable.CustomCellEditor.BtnEditCellEditor;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs.ReportStructureTableView;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.SubClasses.MyDefaultTableModel1;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReportStructureTable extends Table{
    
    private int reportClms;
    private int otherHelperClms;
    private final AggregateWindow aggregateWindow;

    public ReportStructureTable(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    private List<String> getClmHeaders (int clmsNbr) {
        
        List<String> headers = new ArrayList<>();
        
        headers.add("");
        headers.add("Lesson Name");
        
        otherHelperClms = 2;
        
        for (int i = 1; i <= clmsNbr; i++) {
            headers.add("Column " + i);
            reportClms += 1;
        }
        
        return headers;
    }

    @Override
    protected void setDefaultTableModel(Object[][] tableCRowsDatas, Object[] tableClmHeader) {
        super.setDefaultTableModel( new MyDefaultTableModel1(tableCRowsDatas, tableClmHeader) ); 
    }
    
    @Override
    protected void setColumnProperties ()
    {
        TableColumnModel columnModel = this.getjTable().getColumnModel();
        int clmCount = columnModel.getColumnCount();
        
        for (int i = 0; i < clmCount; i++) {
            
            TableColumn column = columnModel.getColumn(i);
            
            if (i == 0) {
                column.setPreferredWidth(50);
                continue;
            }
            
            if (i > 1) {
                setBtnClmRendering_CellEditor(columnModel, i);
                column.setPreferredWidth(80);
                continue;
            }
            
            column.setPreferredWidth(getLabelClmWidth()); //default = 150
            setLabelCellRender(columnModel, i);
        }
    }   
    
    @Override
    protected void setBtnEditCellEditor () {
        super.setBtnEditCellEditor( new BtnEditCellEditor(new JCheckBox(), this, null, getClmsToCheck(), new ReportStructureTableView(aggregateWindow)) );
    }

    @Override
    protected void setTableRowSorterProp() {
        getjTable().setRowSorter(null);
    }

    public int getReportClms() {
        return reportClms;
    }

    public int getOtherHelperClms() {
        return otherHelperClms;
    } 
    
    public static ReportStructureTable getReportStructureTable ( AggregateWindow aggregateWindow, int clmsNbr) {
        
        ReportStructureTable table = new ReportStructureTable (aggregateWindow);
        List<String> headers = table.getClmHeaders(clmsNbr);
        table.setup(table, EditableTableType.IsReportStructureTable, true, true, "", headers, List.of(1), new ArrayList<>(), new int[] {450});
        return table;
    }
}
