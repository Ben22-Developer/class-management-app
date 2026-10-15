package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.SubClasses;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.ClassOptions.ClassOptionsWorker;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.TableDisplayStudentsInsert.TableDisplayHelperClass;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class SavedStudentTableDisplayInsert extends TableDisplayHelperClass{

    private final JButton classOptionsBtn =  new JButton("Class Options");
    
    public SavedStudentTableDisplayInsert(Table table, InputWindow inputWindow) {
        super(table, inputWindow);
    }

    @Override
    protected void setGUIComponentsProp() {
        super.setGUIComponentsProp(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        FrontEndUtility.setJComponentDimension(classOptionsBtn, new Dimension(135, 40));
        FrontEndUtility.setJComponentDefaultFont(classOptionsBtn);
    }

    
    
    @Override
    protected void setEventListeners() {
        
        super.setEventListeners(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        classOptionsBtn.addActionListener((l) -> {
            new ClassOptionsWorker(this).execute();
        });     
    }

    @Override
    public void setTableDisplaySouthPanel() {
        FrontEndUtility.addJComponentToPanel(super.getTableDisplay().getSouthPanel(), getComponents());
    }
    
    @Override
    public void submitData() {
        if (!super.isOnViewCheckedRowsEmpty())
            return;
        super.submitData(getTable().getOnViewCheckedRowsData(), getTable(), new int[] {0,1});        
    }

    @Override
    public JComponent[] getComponents() {
        return new JComponent[] { super.getSelectAllBtn(), super.getSelectNoneBtn() ,super.getRemoveSelectedBtn(), classOptionsBtn, super.getSubmitBtn() };
    }
    
    
    
}
