package app.classmanagementappclient.Frontend.MyJTable.CustomTableRenders;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class LabelRender implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {   
        JTextField textField = new JTextField(value.toString());
        textField.setFont(new Font("Segoe UI 18 Plain", Font.PLAIN, 16));  
        return textField;
    }
   
}

