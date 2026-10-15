package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs;

import app.classmanagementappclient.Frontend.AggregateWindow.AggregateWindow;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author BEN
 */
public class ReportStructureTableView extends TableEditDialog {

    private final AggregateWindow aggregateWindow;
    private JTable jt;
    private Object identifier;
    private int clmIndex;
    
    public ReportStructureTableView(AggregateWindow aggregateWindow) {
        this.aggregateWindow = aggregateWindow;
    }
    
    @Override
    public void drawJDialog() {
        
        initDialog();
        btnListenerRenew();
        
        jt = getTable().getjTable();
        identifier = jt.getTableHeader().getColumnModel().getColumn(getTable().getjTable().getSelectedColumn()).getHeaderValue();
        clmIndex = jt.getTableHeader().getColumnModel().getColumnIndex(identifier) - 2;
        
//        System.out.println(super.getClmIndexToCheck());
//        System.out.println(identifier);
//        System.out.println("At row: " + super.getRow() + ",\tColumn: " + jt.getTableHeader().getColumnModel().getColumnIndex(identifier));
        
        addTitleLabel();
        addCenterPanel();
        setSouthPanelComponents();
        setJDialogProp();
    }
    
    @Override
    protected void addTitleLabel() {
        
        JDialog dialog = super.getDialog();
        String lessonName = (String) super.getTable().getjTable().getValueAt(super.getRow(), super.getClmIndexToCheck().get(0));
        String title = lessonName +" / " + identifier;
        
        JLabel label = new JLabel(title);
        JPanel panel = new JPanel();
        
        FrontEndUtility.setJComponentCustomFont(label, new Font("Arial", Font.BOLD, 18));
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { label });
        dialog.add(panel, BorderLayout.NORTH);
    }

    @Override
    protected void addCenterPanel() {

        Table table = aggregateWindow.getReportStructureItem(super.getRow(), clmIndex);
        JDialog dialog = super.getDialog();
        
        dialog.add(new JScrollPane(table.getjTable()), BorderLayout.CENTER);
    }

    @Override
    protected void setSouthPanelComponents() {
        
        Table table = aggregateWindow.getReportStructureItem(super.getRow(), clmIndex);
        JButton selectAll = new JButton("Select All");  
        JButton selectNone = new JButton("Select None");  
        JButton removeSelected = new JButton ("Remove Selected");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        
        removeSelectedListener(removeSelected, table);
        Table.setCommonEventHandlers(new JButton[] { selectAll,selectNone }, table);
        
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { selectAll,selectNone, removeSelected });
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { selectAll,selectNone, removeSelected });
        
        super.getDialog().add(panel, BorderLayout.SOUTH);
    }

    @Override
    protected void setJDialogProp() {
        
        JDialog dialog = super.getDialog();
        dialog.setPreferredSize(new Dimension(700, 500));
        dialog.setMinimumSize(new Dimension(700, 500));
        super.setLocationPoint(new Point(350, 100));
        super.setWindowTitle("Column Components");
        super.setJDialogProp();
    }

    private void removeSelectedListener(JButton removeSelected,Table table) {
        removeSelected.addActionListener(l -> {
            SwingUtilities.invokeLater(() ->
            {
                addBackTermsAssessmentTableRows(table);
                table.removeAllTableCheckedRows();
            });
        });
    }
    
    private void addBackTermsAssessmentTableRows (Table table) {
        
        List<List<String>> checkedRowsData = table.getCheckedRowsData();
        MyDefaultTableModel termAssessmentModel = (MyDefaultTableModel) aggregateWindow.getTermAssessmentsTable().getModel();
        JTable reportStructureTable = aggregateWindow.getReportStructureTable();
        
        int selectedRow = reportStructureTable.getSelectedRow();
        String lessonName = (String) reportStructureTable.getValueAt(selectedRow, 1);

        System.out.println(termAssessmentModel.getRowCount());

        for (List<String> checkedRowData : checkedRowsData) {
            
            Vector<Object> data = new Vector<>();
            int rowNbr = termAssessmentModel.getRowCount() == 0 ? 1 : Integer.parseInt((String)termAssessmentModel.getValueAt(termAssessmentModel.getRowCount()-1 ,0)) + 1;
            data.add(String.valueOf(rowNbr));
            
            int i = 0;
            
            for (String dataPiece : checkedRowData) {
                
                i += 1;
                
                if (i == 2)
                    data.add(lessonName);
                
                data.add(dataPiece);
            }
            
            data.add("Set");
            termAssessmentModel.addRow(data);
            System.out.println(checkedRowData);
            System.out.println(data);
        }
    }
}
