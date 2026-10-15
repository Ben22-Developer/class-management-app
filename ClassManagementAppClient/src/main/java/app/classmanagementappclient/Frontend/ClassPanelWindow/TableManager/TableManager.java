package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.Common.SouthPanelUIElts.SouthPanelUIElts;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author BEN
 */
public class TableManager {
    
    private final DataEntryActions proxy;
    private List<List<String>> data;
    private final int identifierNum;
    private final ClassPanel classPanel;
    private final Table table;
    private final JPanel myPanel;
    private final JLabel titleLabel = new JLabel("", SwingConstants.CENTER); 
    private final SouthPanelUIElts southPanelUIElts = new SouthPanelUIElts();
    
    public TableManager (List<List<String>> data, int identifierNum, ClassPanel classPanel, Table table, JPanel tablePanel, DataEntryActions proxy) {
        
        this.proxy = proxy;
        this.data = data;
        this.identifierNum = identifierNum;
        this.classPanel = classPanel;
        this.myPanel = tablePanel;
        this.table = table;
    }
    
    public void run () {
        setComponentsProp();
        setTitle();
        addComponentsEventListeners();
        addJComponentsToUI();
    }
    
    protected void setComponentsProp () {
        southPanelUIElts.setComponentsProp();
        FrontEndUtility.setJComponentCustomFont(titleLabel,  new Font("Arial", Font.BOLD, 18));
    }
    
    protected void setTitle () {
        proxy.setTitle (this, proxy.getDataSize(data));
    }
    
    protected void addComponentsEventListeners () {
        
        JButton selectAllBtn = getSelectAllBtn();
        JButton selectNoneBtn = getSelectNoneBtn();
        JButton reloadBtn = getReloadBtn();
        JButton deleteBtn = getDeleteBtn();        
        JButton addNew = getAddNew();
        JTextField textField = getTextField(); 
        String searchTxt = getSearchTxt();
        
        Table.setCommonEventHandlers(new JButton[]{ selectAllBtn, selectNoneBtn }, table );

        reloadBtn.addActionListener(l -> {
            Runnable runnable = () -> {
                reloadHandler();
            };
            new FrontEndUtility().runOneFixedThread(runnable);
        });
        
        deleteBtn.addActionListener(l -> {
            deleteHandler();
        });
        
        addNew.addActionListener(l -> {
            addNewData();
        });
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(searchTxt))
                    textField.setText("");   
            }
        });
        
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                
                if (table.getjTable().getModel().getRowCount() == 0)
                    return;
                tableSearchHandler(textField.getText());
            }
        });
    }
    
    private void reloadHandler () {
        
        ProgressTracker tracker = getTracker();
        
        int[] progress = {0, data.size() * 2};
        tracker.run();
        TableManager tableManager = this;
        
        SwingWorker sw =  new SwingWorker<Object, List<List<String>>> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                data = proxy.getData(identifierNum);
                proxy.setTitle(tableManager, proxy.getDataSize(data));
                
                if (data.isEmpty()) {
                    handleNoDataRemain();
                    return null;
                }                
                
                while (table.getjTable().getRowCount() != 0) {}
                
                publish(data);
                
//                while (data.size() != table.getjTable().getRowCount()) {System.out.println(data.size() +"\t" + table.getjTable().getRowCount());}
                
                return null;
            }

            @Override
            protected void process(List<List<List<String>>> chunks) {
                for (List<List<String>> chunk : chunks) {
                    for (List<String> row : chunk) {
                        proxy.insertTableRow(table, row);
                        progressCounter(progress);
                    }
                }
            }

            @Override
            protected void done() {
                setComponentsBoolean(true);
                tracker.endProgress();
            }
        };
        
        new SwingWorker<Object, Integer> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                JTextField textField = getTextField(); 
                
                if (!textField.getText().isBlank())
                    tableSearchHandler("");
                
                setComponentsBoolean(false);
                publish(table.getjTable().getRowCount());
                return null;
            }            
            
            @Override
            protected void process(List<Integer> chunks) {
                for (int i = chunks.get(0) - 1; i >= 0; i --) {
                    table.removeTableRow(table.getjTable().convertRowIndexToModel(i));
                    progressCounter(progress);
                }
            }

            @Override
            protected void done() {
                sw.execute();
            }
            
        }.execute();
    }
    
    private void progressCounter (int[] progress)  {
        
        ProgressTracker tracker = getTracker();
        tracker.setLoadingPercentage(progress[0], progress[1]);
        progress[0] += 1;        
    }
    
    private void handleNoDataRemain () {
        proxy.handleNoDataReload(this, classPanel, identifierNum);
    }
    
    private void deleteHandler () {
        
        if (table.getCheckedRows().isEmpty())
            return;
        
        int num = FrontEndUtility.showConfirmDialog("Are you sure you want to delete data in selected check boxes?", "Confirm Message", JOptionPane.YES_NO_OPTION);
        if (num != 0)
            return;
        
        proxy.deleteHandler(table, this, getTracker());
    }
    
    protected void addNewData () {
        
        var thisObj = this;
        
        new SwingWorker<Object, Object[]> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                Object[] data = proxy.addNewData(table, identifierNum);
                
                if (data != null)
                    publish (data);
                return null;
            }

            @Override
            protected void process(List<Object[]> chunks) {
                
                for (Object[] objects : chunks) {
                    
                    Vector <Object> vector = new Vector<>();
                    Arrays.stream(objects).forEach(vector::add);
                    table.insertTableARow(table.getjTable().getRowCount(), vector);
                }
            }           

            @Override
            protected void done() {
                proxy.setTitle(thisObj, table.getjTable().getRowCount());
            }
            
        }.execute();
    }
    
    private void tableSearchHandler (String txt) {
        
        if (!(table instanceof SearchableTable))
            return;
        
        SearchableTable table = (SearchableTable) this.table;
        table.searchInTable(txt, this.table);
    }
    
    protected void addJComponentsToUI () {
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        myPanel.removeAll();
        FrontEndUtility.addJComponentToPanel(southPanel, getAllJComponents());
        FrontEndUtility.addJComponentToPanel(southPanel, new JComponent[] { getProgressBar() });
        
        myPanel.add(titleLabel, BorderLayout.NORTH);
        myPanel.add(new JScrollPane(table.getjTable()), BorderLayout.CENTER);
        myPanel.add(southPanel, BorderLayout.SOUTH);
        
        panelReValidation();
    }
    
    public void setComponentsBoolean (boolean bool) {
        for (JComponent component : getAllJComponents())
            component.setEnabled(bool);
    }
    
    protected JComponent[] getAllJComponents () {
        
        return new JComponent [] { getSelectAllBtn(), getSelectNoneBtn(), getReloadBtn(),
            getDeleteBtn(), getAddNew(), getTextField() };
    }
    
    public void panelReValidation () {
        myPanel.revalidate();        
        myPanel.repaint();
    }
    
    public final void setTitle (String text) {
        titleLabel.setText(text);
    }

    public DataEntryActions getProxy() {
        return proxy;
    }

    public List<List<String>> getData() {
        return data;
    }

    public int getIdentifierNum() {
        return identifierNum;
    }
    
    public Table getTable() {
        return table;
    }

    public JPanel getMyPanel() {
        return myPanel;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JButton getSelectAllBtn() {
        return southPanelUIElts.getSelectAllBtn();
    }

    public JButton getSelectNoneBtn() {
        return southPanelUIElts.getSelectNoneBtn();
    }

    public JButton getReloadBtn() {
        return southPanelUIElts.getReloadBtn();
    }

    public JButton getDeleteBtn() {
        return southPanelUIElts.getDeleteBtn();
    }

    public JButton getAddNew() {
        return southPanelUIElts.getAddNew();
    }

    public String getSearchTxt() {
        return southPanelUIElts.getSearchTxt();
    }

    public JTextField getTextField() {
        return southPanelUIElts.getTextField();
    }

    public JProgressBar getProgressBar() {
        return southPanelUIElts.getProgressBar();
    }

    public ProgressTracker getTracker() {
        return southPanelUIElts.getTracker();
    }
}
