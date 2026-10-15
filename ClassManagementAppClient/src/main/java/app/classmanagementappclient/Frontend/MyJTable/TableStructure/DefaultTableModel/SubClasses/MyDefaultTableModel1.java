package app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.SubClasses;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.DefaultTableModel.MyDefaultTableModel;

/**
 *
 * @author BEN
 */
public class MyDefaultTableModel1 extends MyDefaultTableModel {
    
    public MyDefaultTableModel1(Object[][] data, Object[] clmNames) {
        super(data, clmNames);
    }

    @Override
    public boolean isCellEditable(int row, int clm) {
        return clm != 1;
    }
}
