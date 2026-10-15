package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsAddNewLessonTable;

import app.classmanagementappclient.Backend.Logic.CredentialValidation.Validation;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;

/**
 *
 * @author BEN
 */
public class NewLessonTableBtnCellEditorHandler 
{
    public boolean addNewLessonTable (String userInput, int row, int clmIndexToCheck, JTable jTable)  
    {
        Validation validation = new Validation();
        
        if (validation.isLessonNameValid(userInput)) {
            jTable.setValueAt(userInput, row, clmIndexToCheck);
            return true;
        }
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(validation.getErrorMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
