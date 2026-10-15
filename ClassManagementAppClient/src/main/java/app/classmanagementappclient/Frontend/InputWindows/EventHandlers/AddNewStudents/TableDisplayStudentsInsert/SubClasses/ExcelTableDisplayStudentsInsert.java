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
public class ExcelTableDisplayStudentsInsert extends TableDisplayHelperClass{

    private final JButton classOptionsBtn =  new JButton("Class Options");
    private final JButton reportBtn = new JButton("Report");    
    private final String report;
    
    public ExcelTableDisplayStudentsInsert(Table table, InputWindow inputWindow, String report) {
        super(table, inputWindow);
        this.report = report;
    }

    @Override
    protected void setGUIComponentsProp() {
        super.setGUIComponentsProp(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        FrontEndUtility.setJComponentDimension(reportBtn , new Dimension(80, 40));
        FrontEndUtility.setJComponentDimension(classOptionsBtn, new Dimension(135, 40));
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { reportBtn, classOptionsBtn });
    }

    @Override
    protected void setEventListeners() {
        super.setEventListeners(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        reportBtn.addActionListener((l) ->{
            showReport("Re-Input using the formatted way for these input(s):\n\n" + report);
        });
        
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
        return new JComponent[] { super.getSelectAllBtn(), super.getSelectNoneBtn() ,super.getRemoveSelectedBtn(), reportBtn, classOptionsBtn, super.getSubmitBtn() };
    }
    
}
