/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Frontend.StatisticsWindow.Load.DialogLoads.Contract.TableLoaderContract;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class TableLoaderUtility {

    public TableLoaderUtility(StatisticsWindowDialog window, String windowTitle, TableLoaderContract tableLoader) {
        this.window = window;
        this.windowTitle = windowTitle;
        this.tableLoader = tableLoader;
    }

    private final StatisticsWindowDialog window;
    private final String windowTitle;
    private final TableLoaderContract tableLoader;
    private final TableDisplay display = new TableDisplay();
    
    public void loadTable () 
    {
        if (window.getAllClassLessons() == null) {
            FrontEndUtility.showMsgJOptionPane("You have no valid classes selected yet!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableSetup();
    }
    
    private void tableSetup () {
        
        var table = tableLoader.getTable (window);
        displayTable(table, Table.getCommonBtnsViewWithEvent(table), getSubmitButton(table));
    }
    
    private void displayTable (Table table, JComponent[] components, JButton submitButton) {
        
        FrontEndUtility.addJComponentToPanel(display.getSouthPanel(), new JComponent[] { components[0], components[1], submitButton });
        
        display.getTitleLabel().setText(getLabelTitle());
        display.getScrollPaneContainer().setViewportView(table.getjTable());
        display.setCustomTableDisplayProperties(400, 100, new Dimension(600, 500));
        display.setTitle(windowTitle);
        display.useJDialog(table);        
    }
    
    
    private String getLabelTitle () {
        
        return window.getSchoolBox().getSelectedItem().toString() + "/" + 
               window.getClassBox().getSelectedItem().toString() + "/" + 
               window.getAcademicYearBox().getSelectedItem().toString() +"/"+
                "Terms " + window.getClassTerm().toString();
    }    
    
    private JButton getSubmitButton (Table table) {
        
        JButton button = new JButton("Submit");
        FrontEndUtility.setJComponentDefaultFont(button);
        
        button.addActionListener(l -> {   
            tableLoader.setSubmitHandler(table, window, display);
        });
        return button;
    }
}
