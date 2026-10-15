package app.classmanagementappclient.Frontend.MyJTable.TableStructure;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentRecordsTable.AssessmentRecordUpdateDialog;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.CustomCellEditor.BtnEditCellEditor;
import app.classmanagementappclient.Frontend.MyJTable.CustomTableRenders.ButtonRender;
import app.classmanagementappclient.Frontend.MyJTable.CustomTableRenders.LabelRender;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs.ScheduleEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewDialogs.SubClasses.ArchiveViewDialog;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewDialogs.ViewDialogs;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses.*;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.util.*;

public class Table
{
    private EditableTableType tableType;
    private int labelClmWidth = 150;
    private String btnText;
    private List<String> clmHeaders;
    private boolean makeCheckBoxesClm;
    private boolean makeBtnsClm;
    private JTable jTable;
    private MyDefaultTableModel defaultTableModel;
    private final Set<Integer> checkedRows = new TreeSet<>();
    private BtnEditCellEditor btnEditCellEditor;
    private int checkedRowsToRemoveSynch = 0;
    private List<Integer> clmsToCheck;
    private TableRowSorter<TableModel> tableRowSorter;
    
    public void constructorTable (List<List<String>> rowsData) {

        Object[] tableClmHeader = getTableClmHeader();
        Object[][] tableCRowsDatas = getTableRowsData(rowsData);
        setDefaultTableModel(tableCRowsDatas, tableClmHeader);
        jTable = new JTable(defaultTableModel);
        setColumnProperties ();
        setOtherTableProperties();
        addTableModelEventListener ();
        setTableRowSorterProp();
    }

    private Object[] getTableClmHeader ()
    {
        List<Object> tableClmHeader = new ArrayList<>();
        boolean bool = tableClmHeader.addAll(clmHeaders);

        if (!bool)
            System.out.println("In Table.getTableClmHeader, clms aren't added");

        return tableClmHeader.toArray();
    }

    protected Object[][] getTableRowsData (List<List<String>> rowsData)
    {   
        if (rowsData.isEmpty())
            return new Object[0][0];

        int totalRowsNum =  rowsData.size();
        int aRowSize =  getRowSize (rowsData);
        
        Object[][] tableRowsData = new Object [totalRowsNum][aRowSize];
        
        for (int i = 0; i < rowsData.size(); i++) {
            
            int startingPt = 0; // counter for tableRowsData[][...this...]
            int k = 0;
            
            if (makeCheckBoxesClm) {
                tableRowsData[i][startingPt] = Boolean.FALSE;
                startingPt ++;
            }

            while (k < rowsData.get(i).size()) {
                
                tableRowsData[i][startingPt] = rowsData.get(i).get(k);
                k ++;
                startingPt ++;
            }
            
            if (makeBtnsClm) 
                tableRowsData[i][startingPt] = getBtnTxt();
            
        }
        return tableRowsData;
    }
    
    protected void setDefaultTableModel (Object[][] tableCRowsDatas, Object[] tableClmHeader) {
        defaultTableModel = new MyDefaultTableModel(tableCRowsDatas, tableClmHeader);
    }

    protected void setColumnProperties () {
        
        TableColumnModel columnModel = jTable.getColumnModel();
        int clmCount = columnModel.getColumnCount();
        
        for (int i = 0; i < clmCount; i++)
        {
            TableColumn column = columnModel.getColumn(i);
            
            if (makeCheckBoxesClm && i == 0) {
                column.setPreferredWidth(50);
                continue;
            }
            if (makeBtnsClm && i == clmCount - 1) {
                setBtnClmRendering_CellEditor(columnModel, i);
                column.setPreferredWidth(100);
                continue;
            }
            
            column.setPreferredWidth(labelClmWidth); //default = 150
            setLabelCellRender(columnModel, i);
        }
    }


    protected void setBtnClmRendering_CellEditor (TableColumnModel columnModel, int clmIndex) {
        setBtnEditCellEditor();
        columnModel.getColumn(clmIndex).setCellRenderer(new ButtonRender());
        columnModel.getColumn(clmIndex).setCellEditor(btnEditCellEditor);
    }
    
    protected void setLabelCellRender (TableColumnModel columnModel, int clmIndex) {
        columnModel.getColumn(clmIndex).setCellRenderer(new LabelRender ());
    }
    
    protected void setBtnEditCellEditor () { // set Btn Event Handler
        
        btnEditCellEditor = switch (getTableType()) {
            case IsAttendanceTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ViewAttendanceRecordsDialog());
            case IsShowingClassWithStudentsTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ViewClassStudentsDialog());
            case IsShowingClassWithLessonsTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ViewClassLessonsDialog());
            case IsAssessmentTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ViewAssessmentRecordsDialog());
            case IsAssessmentRecordsTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new AssessmentRecordUpdateDialog());
            case IsAggregateClassLessonsTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ViewAllClassAttendanceDialog());
            case IsScheduleTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new ScheduleEditDialog());
            case IsArchivedScheduleTable -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, getArchiveScheduleViewDialog());
            default -> new BtnEditCellEditor(new JCheckBox(), this, clmHeaders, clmsToCheck, new TableEditDialog());
        };  
    }
    
    private ViewDialogs getArchiveScheduleViewDialog () {
        return new ArchiveViewDialog();
    }
    
    protected void setTableRowSorterProp () {
        tableRowSorter = new TableRowSorter<>(defaultTableModel);
        jTable.setRowSorter(tableRowSorter);
    }

    private void setOtherTableProperties() {
        jTable.setRowHeight(40);
        jTable.setFont(FrontEndUtility.getDefaultAppFont());
        jTable.getTableHeader().setFont(FrontEndUtility.getDefaultAppFont());
        jTable.getTableHeader().setReorderingAllowed(false);
    }

    protected int getRowSize (List<List<String>> rowsData)
    {
        int initialRowSize = rowsData.getFirst().size();
        
        if (makeCheckBoxesClm && makeBtnsClm)
            return initialRowSize + 2;
        else if (makeCheckBoxesClm || makeBtnsClm)
            return initialRowSize + 1;

        return initialRowSize;
    }

    private void addTableModelEventListener ()
    {
        if (!makeCheckBoxesClm)
            return;

        defaultTableModel.addTableModelListener((l) -> {
            if (l.getColumn() == 0)
                checkedRowFn (l.getLastRow());
            
        });
    }
    
    /**
     *This method returns the content of a given row , note that it returns information about the clmsToCheck you have specified while initializing the table.
     * @param row
     * @return
     */
    public List<String> getRowDataAt (int row) {
        
        List<String> content = new ArrayList<>();
        
        for (int clm : clmsToCheck)
            content.add(String.valueOf(jTable.getValueAt(row, clm)));
        
        return content;
    }
    
    public void setTableCheckedRows (boolean select) {
        
        new SwingWorker<Object, Integer> () {
            @Override
            protected Object doInBackground() throws Exception {
                for (int i = 0; i < jTable.getRowCount(); i++)
                    publish(i);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int chunk : chunks)
                    jTable.setValueAt(select, chunk, 0);
            }
        }.execute();
                
    }
    
    private void checkedRowFn (int row)
    {
        boolean rowChecked = isRowChecked (row);
        if (rowChecked)
            insertIntoCheckedRow(row);
        else
            removeFromCheckedRowNonSynch(row);
    }
    
    public void removeAllTableCheckedRows () 
    {   
        setCheckedRowsToRemoveSynch (getCheckedRows().size());
        
        new SwingWorker<Object, Integer>() 
        {
            @Override
            protected Object doInBackground() throws Exception 
            {
                List <Integer> checkedRowList = getSortedCheckedRowsList();
                int i = 0;
                
                if (checkedRows.isEmpty()) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("There's no selected rows!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                
                while (!checkedRows.isEmpty())
                {
                    
                    int rowVal = tableRowSorter != null ? tableRowSorter.convertRowIndexToView( checkedRowList.get(i) ) : checkedRowList.get(i);
                    if (rowVal < 0) {
                        i ++;
                        continue;
                    }
                    removeFromCheckedRowSynch(rowVal);
                    publish(rowVal);
                    i ++;
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int chunk : chunks)
                    removeTableRow(chunk);
            }
        }.execute();
    }
    
    private synchronized void removeFromCheckedRowSynch (int row) 
    {   
        while (getCheckedRowsToRemoveSynch() <= 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                System.getLogger(Table.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        
        checkedRows.remove(row);
        calculateCheckedRowToRemoveSynch(-1);
    }
    
    public void setClmsToCheck(List<Integer> clmsToCheck) {
        this.clmsToCheck = clmsToCheck;
    }    
    
    /**
     *Row is currently selected row, while newData list is the list having content for only the clmsToCheck
     * @param row
     * @param newData
     */
    public void setValueAt (int row, List<String> newData) {
        for (int i = 0; i < clmsToCheck.size(); i++)
            jTable.setValueAt(newData.get(i), row, clmsToCheck.get(i));
    }
    
    public void removeTableRow (int tableRowIndex) {
        defaultTableModel.removeRow(tableRowIndex);
    }

    public void setCheckedRowsToRemoveSynch (int checkedRowToRemoveSynch) {
        this.checkedRowsToRemoveSynch = checkedRowToRemoveSynch;
    }
    
    public synchronized void calculateCheckedRowToRemoveSynch(int num) {
        this.checkedRowsToRemoveSynch += num;
        if (this.checkedRowsToRemoveSynch > 0)
            notifyAll();
    }

    public synchronized int getCheckedRowsToRemoveSynch() {
        return checkedRowsToRemoveSynch;
    }

    public void insertTableARow (int row, List<Object> rowData) {
        defaultTableModel.insertRow(row, new Vector<>(rowData));
    }
    
    public void emptifyCheckedRowSet () {
        checkedRows.clear();
    }
    
    private void removeFromCheckedRowNonSynch (int row) {
        checkedRows.remove(row);
    }

    private void insertIntoCheckedRow (int row) {
        checkedRows.add(row);
    }    
    
    protected boolean isRowChecked (int row) {
        return (Boolean) defaultTableModel.getValueAt(row,0);
    }
    
    /**
     *Get data in the rows 
     * @return
     */
    public  List<List<String>> getDataInRows () {
        
        List<List<String>> data = new ArrayList<>();
        JTable table = getjTable();
        
        for (int i = 0; i < table.getRowCount(); i++) {
            
            List<String> row = getRowDataAt(i);
            
            data.add(row);
        }
        return data;
    }
    
    public List<List<String>> getCheckedRowsData () {
        
        List<List<String>> data = new ArrayList<>();
        Set<Integer> checkedRows = getCheckedRows();
        Iterator<Integer> iterator = checkedRows.iterator();
        
        while (iterator.hasNext()) {
            List<String> row = getRowDataAt(jTable.convertRowIndexToModel(iterator.next()));
            data.add(row);
        }
        return data;
    }
    
    public List<List<String>> getOnViewCheckedRowsData () {
        
        List<List<String>> data = new ArrayList<>();
        Set<Integer> checkedRows = getCheckedRows();
        Iterator<Integer> iterator = checkedRows.iterator();
        
        while (iterator.hasNext()) {
            
            int index = tableRowSorter.convertRowIndexToView( iterator.next() );
            if (index < 0)
                continue;
            
            List<String> row = getRowDataAt(index);
            data.add(row);
        }
        return data;
    }
    
    public Set<Integer> getCheckedRows () {
        return checkedRows;
    }
    
    public List<Integer> getSortedCheckedRowsList () {
        Comparator<Integer> comparator = (o1,o2) -> o1 > o2 ? -1 : 1;
        return checkedRows.parallelStream().sorted(comparator).toList();        
    }

    public String getBtnTxt () {
        return btnText;
    }

    public void setBtnEditCellEditor(BtnEditCellEditor btnEditCellEditor) {
        this.btnEditCellEditor = btnEditCellEditor;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public void setClmHeaders(List<String> clmHeaders) {
        this.clmHeaders = clmHeaders;
    }

    public void setMakeCheckBoxesClm(boolean makeCheckBoxesClm) {
        this.makeCheckBoxesClm = makeCheckBoxesClm;
    }

    public void setMakeBtnsClm(boolean makeBtnsClm) {
        this.makeBtnsClm = makeBtnsClm;
    }
    
    public void setLabelClmWidth(int labelClmWidth) {
        this.labelClmWidth = labelClmWidth;
    }

    public BtnEditCellEditor getBtnEditCellEditor() {
        return btnEditCellEditor;
    }

    public JTable getjTable() {
        return jTable;
    }

    public EditableTableType getTableType() {
        return tableType;
    }

    public void setTableType(EditableTableType tableType) {
        this.tableType = tableType;
    }

    public List<Integer> getClmsToCheck() {
        return clmsToCheck;
    }

    public List<String> getClmHeaders() {
        return clmHeaders;
    }

    public MyDefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    public void setDefaultTableModel(MyDefaultTableModel defaultTableModel) {
        this.defaultTableModel = defaultTableModel;
    }

    public int getLabelClmWidth() {
        return labelClmWidth;
    }

    public String getBtnText() {
        return btnText;
    }

    public boolean isMakeCheckBoxesClm() {
        return makeCheckBoxesClm;
    }

    public boolean isMakeBtnsClm() {
        return makeBtnsClm;
    }

    public TableRowSorter<TableModel> getTableRowSorter() {
        return tableRowSorter;
    }
    
    public void setup (Table table, EditableTableType tableType, boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int[]clmWidth) {
        if (makeBtnClm) {
            table.setBtnText(btnClmText);
            table.setMakeBtnsClm(makeBtnClm);
            table.setTableType(tableType);
        }
        
        if (makeCheckBoxClm) {
            table.setMakeCheckBoxesClm(makeCheckBoxClm);
        }
        
        if (clmWidth.length != 0) {
            table.setLabelClmWidth( clmWidth[0] );
        }
        table.setClmHeaders(clmHeaders);
        table.setClmsToCheck(clmsToCheck);
        table.constructorTable(rowsData);        
    }
    
    public static Table getTable (EditableTableType tableType, boolean  makeCheckBoxClm, boolean makeBtnClm, String btnClmText, List<String> clmHeaders, List<Integer> clmsToCheck, List<List<String>> rowsData, int...clmWidth) 
    {
        Table table = new Table();
        
        table.setup(table, tableType, makeCheckBoxClm, makeBtnClm, btnClmText, clmHeaders, clmsToCheck, rowsData, clmWidth);
        
        return table;
    }
    
    public static JButton[] getCommonBtnsViewWithEvent (Table table) {
        JButton[] btns = getCommonBtnsView  ();
        setCommonEventHandlers(btns, table);
        return btns;
    }
    
    public static JButton[] getCommonBtnsView () {
        
        JButton selectAll = new JButton("Select All");
        JButton selectNone = new JButton("Select None");
        JButton[] btns = { selectAll, selectNone };
        FrontEndUtility.setJComponentsDefaultFont(btns);
        return btns;
    }
    
    public static void setCommonEventHandlers (JButton[] buttons, Table table) 
    {
        JButton selectAllBtn = buttons[0];
        JButton selectNoneBtn = buttons[1];
        
        selectAllBtn.addActionListener((ActionEvent e) -> {
            table.setTableCheckedRows(true);
        });
        
        selectNoneBtn.addActionListener((ActionEvent e) -> {
            table.setTableCheckedRows(false);
        });
    }
    
    public static void removeTableRowsWithProgressTracker (Table table, ProgressTracker tracker, int lastIndex, int [] progressTrackerArr, int totalProgress) {
        tracker.run();
        for (int i = lastIndex; i >= 0; i--) {
            table.removeTableRow(table.getjTable().convertRowIndexToModel(i));
            tracker.setLoadingPercentage(progressTrackerArr[0], totalProgress);
            progressTrackerArr[0] += 1;
        }       
        tracker.endProgress();
    }
}