package app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel
{
    private Object prevObject;
    
    public MyDefaultTableModel (Object[][] data, Object[] clmNames) {
        super (data, clmNames);
    }

    @Override
    public boolean isCellEditable (int row , int clm) {
        return clm == this.getColumnCount() - 1 || clm == 0;
    }

    @Override
    public Class<?> getColumnClass (int clmIndex) {
        
        String columnName = getColumnName(clmIndex);
        
        if (columnName.equalsIgnoreCase("num") || columnName.equalsIgnoreCase("no") || columnName.equalsIgnoreCase("Total Marks") || columnName.equalsIgnoreCase("years") || columnName.equalsIgnoreCase("terms") || columnName.equalsIgnoreCase("term")){
            return Integer.class;
        }
        
        if (columnName.equalsIgnoreCase("Student Marks") || columnName.equalsIgnoreCase("Total (%)"))
            return Double.class;
        
        String value = String.valueOf(getValueAt(0, clmIndex));
        if (clmIndex == 0 && !value.equals("true") && !value.equals("false"))
            return String.class;
        
        return clmIndex == 0 ? Boolean.class : String.class;
    }
    
    @Override
    public void setValueAt(Object value, int row, int clm) 
    {
        if (row >= getRowCount())
            return;
        prevObject = getValueAt(row, clm);
        super.setValueAt(value, row, clm);
    }

    public Object getPrevObject() {
        return prevObject;
    }  
    
    public void removeColumn (int i) {
        
        
        
    }
}

