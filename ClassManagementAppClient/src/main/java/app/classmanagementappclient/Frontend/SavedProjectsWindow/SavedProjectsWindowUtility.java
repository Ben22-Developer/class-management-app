package app.classmanagementappclient.Frontend.SavedProjectsWindow;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.SavedProjectsWindow.EventHandlers.DataReLoad.SavedProjectsDataReload;
import app.classmanagementappclient.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author BEN
 */
abstract public class SavedProjectsWindowUtility {
    
    private final TableDisplay tableDisplay = new TableDisplay();
    private final JButton selectAllBtn = new JButton("Select All");
    private final JButton selectNoneBtn =  new JButton("Select None");
    private final JButton reloadDataBtn = new JButton ();
    private final JButton deleteSelectedBtn = new JButton();
    private final String searchStr = "Search..................";
    private final JTextField searchTextField = new JTextField(searchStr);
    private int totalData;
    
    public void projectActivitiesMenuItenActionPerformed () {
        
        if (!isLoggedIn())
            return;
        
        Runnable runnable = () -> {
            run();
        };
        new FrontEndUtility().runOneFixedThread(runnable);
    }
    
    public boolean isLoggedIn () {
        if (!FrontEndUtility.isTeacherLoggedIn()) {
            FrontEndUtility.showMsgJOptionPane("To access this functionality you need to log in or sign in\nGo to file > setings or press 'Ctrl+Alt+S' ", "Informational message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } 
        return true;
    }
    
    public void run() {
        List<List<String>> data = dataLoad();
        setTable(data);
        setGUIComponents();        
    }    
    
    protected void setGUIComponents () {
        
        Properties fileProperties = Main.getApplicationFilesPaths();
        String deleteIconPath = (String) fileProperties.get("DeleteIcon");
        String reloadIconPath = (String) fileProperties.get("ReloadIcon");
        String searchIconPath = (String) fileProperties.get("SearchIcon");
//        String displayIconPath = (String) fileProperties.get("EyeIcon");
        
        JComponent[] components = {  
            selectAllBtn, selectNoneBtn,reloadDataBtn,deleteSelectedBtn,searchTextField, getProgressBar()
        };
        
        ArrayList<int[]> componentsSize = new ArrayList<>();
        
        setBtnsIcon(new JButton[]{ reloadDataBtn, deleteSelectedBtn }, new String[] { reloadIconPath, deleteIconPath, searchIconPath });
        setComponentsSize(componentsSize);
        setJComponentSize(components, componentsSize);
        setWindowTitle();
        setTitleLabelTxt();
        setCenterPanel();
        setSouthPanel(components);
        addGUIEventListeners();
        setCustomTableDisplayProp();
    }
    
    
    private void setBtnsIcon (JButton[]btns, String[] iconsPath) {
        for (int i = 0; i < btns.length; i++)
            btns[i].setIcon(new ImageIcon(iconsPath[i]));
    }
    
    protected void setComponentsSize (ArrayList<int[]> componentsSize) {
        componentsSize.add(new int[] {100,40}); // select all
        componentsSize.add(new int[] {120,40}); // select none
        componentsSize.add(new int[] {70,40}); // reload btn
        //componentsSize.add(new int[] {70,40}); // display selected
        componentsSize.add(new int[] {70,40}); // delete selected
        componentsSize.add(new int[] {150,40}); // search txt field
        componentsSize.add(new int[] {150,40}); // progress bar        
    }
    
    private void setJComponentSize (JComponent[] components, ArrayList<int[]> componentsSize) 
    {    
        for (int i = 0; i < components.length; i++) {
            components[i].setFont(FrontEndUtility.getDefaultAppFont());
            FrontEndUtility.setJComponentDimension(components[i], new Dimension(componentsSize.get(i)[0], componentsSize.get(i)[1]));
        }
    }
    
    private void setWindowTitle () {
        tableDisplay.setTitle("Saved Projects");
    }
    
    private void setCenterPanel () {
        tableDisplay.getScrollPaneContainer().setViewportView(getTable().getjTable());
    }
    
    protected void setSouthPanel (JComponent[] components) {
        
        JPanel panel = tableDisplay.getSouthPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 5));
        FrontEndUtility.addJComponentToPanel(panel, components);
    }
    
    private void setCustomTableDisplayProp () {
        tableDisplay.setCustomTableDisplayProperties(20, 80, new Dimension(1250, 500));
        tableDisplay.setResizable(true);
    }
    
    public void displayDialog () {
        tableDisplay.useJDialog (getTable());
    }
    
    protected void addGUIEventListeners () {
        
        //JComponent[] componentsToDisable = getComponents();
        
        Table.setCommonEventHandlers(new JButton[] { selectAllBtn,selectNoneBtn}, getTable());
        
        reloadDataBtn.addActionListener((ActionEvent e) -> {
            reloadHandler();
        });
        
        deleteSelectedBtn.addActionListener((ActionEvent e) -> {
            deleteHandler ();
        });
        
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained (FocusEvent e) {
                if (searchTextField.getText().equalsIgnoreCase(searchStr)) 
                    searchTextField.setText("");
            }
        });
        
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased (KeyEvent e) {
                if (getTable() instanceof SearchableTable table)
                    table.searchInTable(searchTextField.getText(), getTable());
            }
        });
    }
    
    private void reloadHandler () {
        new SavedProjectsDataReload(this, getProgressTracker()).reload();
    } 
    
    public void processInit (JComponent[] components) {
        FrontEndUtility.enableJComponents(components, false);
    }

    public JButton getSelectAllBtn() {
        return selectAllBtn;
    }

    public JButton getSelectNoneBtn() {
        return selectNoneBtn;
    }

    public JButton getReloadDataBtn() {
        return reloadDataBtn;
    }

    public JButton getDeleteSelectedBtn() {
        return deleteSelectedBtn;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public TableDisplay getTableDisplay() {
        return tableDisplay;
    }

    public JComponent[] getComponents () {
        return new JComponent[] { selectAllBtn, selectNoneBtn, deleteSelectedBtn, reloadDataBtn, searchTextField, getTable().getjTable() };
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getTotalData() {
        return totalData;
    }
    
    public abstract void setTable(List<List<String>> data);
    public abstract Table getTable();
    public abstract List<List<String>> dataLoad();
    public abstract void setTitleLabelTxt();
    public abstract void insertData(List<String> data);
    public abstract void deleteHandler();
    public abstract JComponent getProgressBar();
    public abstract ProgressTracker getProgressTracker();
}
