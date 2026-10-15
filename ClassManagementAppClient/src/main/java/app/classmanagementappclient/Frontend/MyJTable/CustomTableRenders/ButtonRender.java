package app.classmanagementappclient.Frontend.MyJTable.CustomTableRenders;

import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRender implements TableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {   
        JButton btn = new JButton( String.valueOf(value) );
//        JButton btn = (JButton) value;
        String btnText = btn.getText();
        JButton jButton = new JButton(btnText);
        jButton.setFont(FrontEndUtility.getDefaultAppFont());
        return jButton;
    }
}

