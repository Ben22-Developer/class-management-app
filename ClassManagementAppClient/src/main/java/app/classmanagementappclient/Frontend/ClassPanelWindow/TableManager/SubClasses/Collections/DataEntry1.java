package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections;

import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author BEN
 */
abstract public class DataEntry1 extends TableManager {
    

    private final String lessonName;
    private final Table parentTable;
    private final List<Integer> clmIndexToCheck;
    private final int selectedRow;
    
    private final int standardWidth = 900;
    
    private final JLabel dateLabel = new JLabel();
    private final JButton editDateButton = new JButton("Edit");
    
    private final JRadioButton oneByOneInsert = new JRadioButton("One by one");
    private final JRadioButton multipleInsert = new JRadioButton("Multiple");
    private final ButtonGroup buttonGroup = new ButtonGroup();
    
    public DataEntry1(List<List<String>> data, int attendanceNum, Table table, JPanel tablePanel, String lessonName, Table parentTable, List<Integer> clmIndexToCheck, int selectedRow, DataEntryActions proxy) {
        
        super(data, attendanceNum, null, table, tablePanel, proxy);
        
        this.lessonName = lessonName;
        this.parentTable = parentTable;
        this.clmIndexToCheck = clmIndexToCheck;
        this.selectedRow = selectedRow;
    }

    @Override
    protected void addComponentsEventListeners() {
        
        super.addComponentsEventListeners();
        
        editDateButton.addActionListener(l -> 
        {
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    edit();
                    return null;
                }
            }.execute();
        });
        
        ActionListener action = super.getAddNew().getActionListeners()[0];
        super.getAddNew().removeActionListener(action);
        oneByOneInsert.addActionListener(action);
        
        multipleInsert.addActionListener(l -> {
            multipleInsertion();
        });
    }
    
    @Override
    protected void setComponentsProp() {
        super.setComponentsProp();
        super.setTitle();
        FrontEndUtility.setJComponentsDefaultFont( new JComponent[] {oneByOneInsert, multipleInsert} );
        buttonGroup.add(oneByOneInsert);
        buttonGroup.add(multipleInsert);
    }

    @Override
    protected void addJComponentsToUI() {
        Runnable doRun = () -> 
        {
            JPanel myPanel = super.getMyPanel();
            myPanel.add(getNorthPanel(), BorderLayout.NORTH);
            myPanel.add(getCenterPanel(), BorderLayout.CENTER);
            myPanel.add(getSouthPanel(), BorderLayout.SOUTH);
            MyJDialog dialog = new MyJDialog();
            dialog.setTitle(getWindowTitle());
            dialog.add(myPanel);
            dialog.setSize(new Dimension(standardWidth, 600));
            dialog.setLocation(250, 50);
            dialog.setVisible(true);
        };      
        
        SwingUtilities.invokeLater(doRun);
    }
    
    private JPanel getNorthPanel () {
        
        JPanel northPanel = new JPanel (new BorderLayout(0,5));
        
        JPanel lessonTitlePanel = getLessonTitlePanel();
        JPanel dateChooserPanel = getDateTitlePanel();
        
        northPanel.add(lessonTitlePanel, BorderLayout.NORTH);
        northPanel.add(dateChooserPanel, BorderLayout.CENTER);
        
        return northPanel;
    }
    
    private JPanel getLessonTitlePanel () {
        JPanel lessonTitlePanel = new JPanel();
        lessonTitlePanel.add (super.getTitleLabel());
        return lessonTitlePanel;
    }
    
    private JPanel getDateTitlePanel () {
        
        JPanel dateChooserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,30, 0));
        setDateTitle();
        
        FrontEndUtility.setJComponentsCustomFont(new JComponent[] { dateLabel, editDateButton }, new Font("Arial", Font.BOLD, 16));
        FrontEndUtility.addJComponentToPanel(dateChooserPanel, new JComponent[] { dateLabel, editDateButton });
        
        return dateChooserPanel;
    } 
    
    private JPanel getCenterPanel () {
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        JPanel tablePanel = new JPanel();
        JPanel componentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        
        JScrollPane scrollPane = new JScrollPane(super.getTable().getjTable());
        scrollPane.setPreferredSize(new Dimension(standardWidth - 50, 370));
        
        FrontEndUtility.addJComponentToPanel(componentsPanel, new JComponent[] { super.getSelectAllBtn(), super.getSelectNoneBtn(), super.getReloadBtn(), super.getDeleteBtn(), super.getTextField() });
        FrontEndUtility.addJComponentToPanel(componentsPanel, new JComponent[] {super.getProgressBar()});
        tablePanel.add(scrollPane, BorderLayout.NORTH);
        tablePanel.add(componentsPanel, BorderLayout.SOUTH);
        
        centerPanel.add(tablePanel, BorderLayout.NORTH);
        centerPanel.add(componentsPanel, BorderLayout.SOUTH);
        return centerPanel;
    }
    
    private JPanel getSouthPanel () {
        
        JPanel southPanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 50, 20));
        
        southPanel.add(oneByOneInsert);
        southPanel.add(multipleInsert);
        
        return southPanel;
    }

    @Override
    protected void addNewData () {
        
        var proxy = (DataEntry1Int) super.getProxy();
        var thisObj = this;
        
        new SwingWorker<Object, Object[]> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                proxy.addNewData(thisObj.getTable(), thisObj.getIdentifierNum());
                
                while (proxy.isNextRecord()) {
                     Object[] chunks = proxy.getNewRecords();
                     
                     if (chunks == null) 
                        break;
                     
                     publish(chunks);
                     proxy.setNextRecord(true);
                }
                
                return null;
            }

            @Override
            protected void process(List<Object[]> chunks) {
                
                Table table = thisObj.getTable();
                
                for (Object[] objects : chunks) {
                    
                    Vector <Object> vector = new Vector<>();
                    Arrays.stream(objects).forEach(vector::add);
                    table.insertTableARow(table.getjTable().getRowCount(), vector);
                }
                setTitle("Total Records: " + table.getjTable().getRowCount());
            }            
        }.execute();
    }    
    
    @Override
    protected JComponent[] getAllJComponents () {
        return new JComponent [] { super.getSelectAllBtn(), super.getSelectNoneBtn(), super.getReloadBtn(), super.getDeleteBtn(), super.getTextField(), getEditDateButton(), getOneByOneInsert(), getMultipleInsert() };
    }

    public JButton getEditDateButton() {
        return editDateButton;
    }

    public JRadioButton getOneByOneInsert() {
        return oneByOneInsert;
    }

    public JRadioButton getMultipleInsert() {
        return multipleInsert;
    }   

    public Table getParentTable() {
        return parentTable;
    }

    public List<Integer> getClmIndexToCheck() {
        return clmIndexToCheck;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getStandardWidth() {
        return standardWidth;
    }

    public JLabel getDateLabel() {
        return dateLabel;
    }

    public String getLessonName() {
        return lessonName;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }
    
    public abstract void edit();
    public abstract void multipleInsertion();
    public abstract String getWindowTitle();
    public abstract void setDateTitle ();
}
