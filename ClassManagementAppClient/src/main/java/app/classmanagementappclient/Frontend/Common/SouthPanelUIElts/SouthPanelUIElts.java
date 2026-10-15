/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.Common.SouthPanelUIElts;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Main;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author BEN
 */
public class SouthPanelUIElts {
    
    private final JButton selectAllBtn = new JButton("Select All");
    private final JButton selectNoneBtn = new JButton("Select None");
    private final JButton reloadBtn = new JButton(new ImageIcon(String.valueOf(Main.getApplicationFilesPaths().getProperty("ReloadIcon"))));
    private final JButton deleteBtn = new JButton(new ImageIcon(String.valueOf(Main.getApplicationFilesPaths().getProperty("DeleteIcon"))));
    private final JButton addNew = new JButton("+");    
    private final String searchTxt = "Search...........";
    private final JTextField textField = new JTextField (searchTxt);
    private final JProgressBar progressBar = new JProgressBar();
    private final ProgressTracker tracker = new ProgressTracker(progressBar);
    
        
    public void setComponentsProp () {
        FrontEndUtility.setJComponentsDefaultFont( new JComponent[] { selectAllBtn, selectNoneBtn, textField} ); 
        FrontEndUtility.setJComponentCustomFont(addNew, new Font("Arial", Font.BOLD, 40));
        
        FrontEndUtility.setJComponentDimension(selectAllBtn, new Dimension(100,40));
        FrontEndUtility.setJComponentDimension(selectNoneBtn, new Dimension(120,40));
        FrontEndUtility.setJComponentDimension(reloadBtn, new Dimension(50,40));
        FrontEndUtility.setJComponentDimension(deleteBtn, new Dimension(50,40));
        FrontEndUtility.setJComponentDimension(addNew, new Dimension(60,40));
        
        FrontEndUtility.setJComponentDimension(textField, new Dimension(120,40));
        FrontEndUtility.setJComponentDimension(progressBar, new Dimension(100,40));
    }

    public JButton getSelectAllBtn() {
        return selectAllBtn;
    }

    public JButton getSelectNoneBtn() {
        return selectNoneBtn;
    }

    public JButton getReloadBtn() {
        return reloadBtn;
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JButton getAddNew() {
        return addNew;
    }

    public String getSearchTxt() {
        return searchTxt;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public ProgressTracker getTracker() {
        return tracker;
    }
    
    public JButton getNewReloadBtn () {
        return new JButton(new ImageIcon(String.valueOf(Main.getApplicationFilesPaths().getProperty("ReloadIcon"))));
    }
}
