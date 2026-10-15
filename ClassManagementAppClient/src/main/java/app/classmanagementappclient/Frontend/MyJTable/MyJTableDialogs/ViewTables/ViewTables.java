package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables;

import app.classmanagementappclient.Medium.ClassTerm.ClassTermMedium;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class ViewTables extends TableEditDialog {
    
    private int num;
    
    @Override
    public void drawJDialog () {
        
        num = getIndentifier();
        
        if (num == Integer.MIN_VALUE) {
            showErrMsg();
            return;
        }
        List<List<String>> data = getData(num);
        
        display(data);
    }
    
    protected void commonPropertiesSetup (JButton[] selectors, JComponent[] components, TableDisplay tableDisplay, Table table, String title) {
        
        Table.setCommonEventHandlers(selectors, table);
        FrontEndUtility.setJComponentsDefaultFont(components);
        
        setTableDisplayProperties(tableDisplay);
        setTableDisplayNorthPanel(tableDisplay, title);
        setTableDisplayCenterPanel(tableDisplay, table);
        setTableDisplaySouthPanel(tableDisplay, components);
        tableDisplay.useJDialog(table);        
    }
    
    protected int getIndentifier () {
        
        List<String> values = super.getSelectedRowData();
        
        String schoolName = values.get(0);
        String className =  values.get(1);
        int year = Integer.parseInt( values.get(2));
        int term = Integer.parseInt( values.get(3));
        return new ClassTermMedium().getOneClassTermNumV1(schoolName, className, year, term);        
    }    
    
    protected void setTableDisplayProperties (TableDisplay tableDisplay) {
        tableDisplay.setCustomTableDisplayProperties(400, 100, new Dimension(700, 500));
    }
    
    protected void setTableDisplayNorthPanel (TableDisplay tableDisplay, String titleTxt) {
        tableDisplay.getTitleLabel().setText(titleTxt);
    }
    
    protected void setTableDisplayCenterPanel (TableDisplay tableDisplay, Table table) {
        tableDisplay.getScrollPaneContainer().setViewportView(table.getjTable());
    }
    
    protected void setTableDisplaySouthPanel (TableDisplay tableDisplay, JComponent[] components) {
        FrontEndUtility.addJComponentToPanel(tableDisplay.getSouthPanel(), components);
    }    
    
    protected void useTableDisplayDialog (TableDisplay tableDisplay, Table table) {
        tableDisplay.useJDialog(table); 
    }
    
    protected JButton getRemoveSelectedBtn (Table table) {
        
        JButton removeSelectedBtn = new JButton("Remove Selected");
        
        removeSelectedBtn.addActionListener(l -> {
            table.removeAllTableCheckedRows();
        });
        
        return removeSelectedBtn;
    }

    public int getNum() {
        return num;
    }
    
    public abstract void showErrMsg();
    public abstract List<List<String>> getData (int identifier);
    public abstract void display(List<List<String>> data);
}
