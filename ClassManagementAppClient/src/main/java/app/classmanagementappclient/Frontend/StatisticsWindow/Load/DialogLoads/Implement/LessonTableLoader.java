package app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Implement;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Contract.TableLoaderContract;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class LessonTableLoader implements TableLoaderContract{
   
    
    @Override
    public Table getTable(StatisticsWindowDialog window) {
        return Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("", "Lessons"), List.of(1), window.getAllClassLessons(), 200);
    }
    
    
    @Override
    public void setSubmitHandler (Table table, StatisticsWindowDialog window, TableDisplay display) {
        
        if (table.getCheckedRowsData().isEmpty()) {
            FrontEndUtility.showMsgJOptionPane("You must select at least 1 lesson!", "Warning Message", JOptionPane.WARNING_MESSAGE);
            return;
        }       
        
        window.setSelectedClassLessons( table.getOnViewCheckedRowsData().parallelStream().map(List::getFirst).toList() );
        display.getDialog().dispose();
    }
 }
