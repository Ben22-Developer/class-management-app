package app.classmanagementappclient.Frontend.SettingsWindow.UserAccount;

import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindowUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load.WebServiceWindowLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class UserAccountSetting 
{
    public void userAccountActionPerformed (SettingsWindow sw) 
    {
        JPanel settingsView = sw.getSettingsView();
        CardLayout cl = (CardLayout) settingsView.getLayout();
        cl.show(settingsView, "card4");
        SettingsWindowUtility.storeCurrentPanel("card4");
        
        Runnable runnable = () -> {
            loadPanel(sw);
        };
        sw.runSwingUtility(runnable);
    }
    
    public void loadPanel (SettingsWindow sw) 
    {
        if (sw.isUserAccountSetingPanelLoaded())
            return;
        
        initialUIState(sw);
        displayLoggedInUserData(sw); 
        sw.setUserAccountSetingPanelLoaded(true);
    }
    
    public void userAccEditBtnActionPerformed (SettingsWindow sw) {
        setTextFieldsComponentsEditable(sw, true);
        buttonsEnable(new JButton[]{sw.getUserAccSubmitBtn()},true);
    }
    
    public void userAccDeleteBtnActionPerformed (SettingsWindow sw) 
    {
        Runnable runnable = () -> {
            TeacherMedium tm = new TeacherMedium();
            
            if (!CommonWebServiceFeatures.teacherApiKey().isBlank()) {
                new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Please delete your API key before proceeding with this operation", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            boolean isOkay = tm.deleteAccount();
            if (isOkay) {
                setUIComponentContext(sw, List.of("","",""));
                initialUIState(sw);
                WebServiceWindowLoad.apiKeyCheckUp(sw);
            }    
            setPaneMsg(isOkay, tm.getMsg()); 
            
        };
        sw.runSwingUtility(runnable);
    }
    
    public void userAccSubmitBtnActionPerformed (SettingsWindow sw) 
    {
        Runnable runnable = () -> 
        {
            TeacherMedium tm = new TeacherMedium();
            
            JTextField[] textFields = getTextFieldsComponents(sw); //sw.getUserAccNameTextField(),  sw.getUserAccPinField(), sw.getUserAccPhoneNbrTextField(), sw.getEmailTextField()
            Teacher newTeacherData = sw.getTeacher(textFields[0],  (JPasswordField)textFields[1], textFields[2], textFields[3]);
            
            boolean isOkay = tm.updateData (newTeacherData);
            
            displayUserData(sw);
            setPaneMsg(isOkay, tm.getMsg());
        };
        setTextFieldsComponentsEditable(sw, false);
        buttonsEnable(new JButton[]{sw.getUserAccSubmitBtn()}, false);
        sw.runSwingUtility(runnable);
    }
    
    private void initialUIState (SettingsWindow sw) {
        buttonsEnable(getBtnComponents(sw), false);
        setTextFieldsComponentsEditable(sw, false);
        setTextFieldsComponentsEmpty(sw);
    }
    
    private void displayLoggedInUserData (SettingsWindow sw) 
    {
        boolean isLoggedIn = FrontEndUtility.isTeacherLoggedIn();
        if (isLoggedIn) 
            userLoggedIn(sw);       
    }
    
    private void userLoggedIn (SettingsWindow sw) {
        buttonsEnable(new JButton[]{sw.getUserAccEditBtn(), sw.getUserAccDeleteBtn()}, true);
        displayUserData(sw);
    }
    
    private void displayUserData (SettingsWindow sw) 
    {
        TeacherMedium tm = new TeacherMedium();
        List<String> userData = tm.getUserData(); // teacherName,teacherPhoneNumber, teacherEmail, teacherPin
        setUIComponentContext(sw,userData);        
    }
    
    private void setUIComponentContext (SettingsWindow sw,  List<String> data) 
    {   
        sw.getLogAccountTitle().setText( data.getFirst() );
        sw.getUserAccNameTextField().setText (data.getFirst());
        sw.getUserAccPhoneNbrTextField().setText (data.get(1));
        sw.getUserAccEmailTextField().setText(data.get(2));
        sw.getUserAccPinField().setText (data.getLast());
    }    
    
    private void setPaneMsg (boolean bool, String txt) {
        String title = bool ? "Informational Message" : "Error Message";
        int msgType = bool ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        FrontEndUtility.showMsgJOptionPane(txt, title, msgType);
    } 
    
    private void buttonsEnable (JButton[] btns, boolean enable) {
        for (JButton btn : btns)
            btn.setEnabled(enable);
    }
    
    private void setTextFieldsComponentsEditable (SettingsWindow sw, boolean enable) 
    {
       JTextField[] textFields = getTextFieldsComponents(sw);
       for (JTextField textField : textFields) {
           textField.setEditable(enable);
       }
    }
    
    private void setTextFieldsComponentsEmpty (SettingsWindow sw) 
    {
       JTextField[] textFields = getTextFieldsComponents(sw);
       for (JTextField textField : textFields) {
           textField.setText("");
       }        
    }
    
    private JComponent[] getAllComponents (SettingsWindow sw) {
        return new JComponent[]{ sw.getUserAccNameTextField(), sw.getUserAccPhoneNbrTextField(), sw.getUserAccPinField(), sw.getUserAccEditBtn(), sw.getUserAccSubmitBtn() };
    }

    private JTextField[] getTextFieldsComponents (SettingsWindow sw) {
        return new JTextField[]{ sw.getUserAccNameTextField(),  sw.getUserAccPinField(), sw.getUserAccPhoneNbrTextField(), sw.getUserAccEmailTextField()}; // before adjusting another texField check on line 68
    }  
    
    private JButton[] getBtnComponents (SettingsWindow sw) {
        return new JButton[]{ sw.getUserAccEditBtn(), sw.getUserAccSubmitBtn(), sw.getUserAccDeleteBtn(), sw.getUserAccDeleteBtn() };
    }      
}
