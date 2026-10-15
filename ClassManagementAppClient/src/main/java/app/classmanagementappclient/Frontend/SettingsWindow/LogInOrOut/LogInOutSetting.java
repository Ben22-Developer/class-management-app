package app.classmanagementappclient.Frontend.SettingsWindow.LogInOrOut;

import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindow;
import app.classmanagementappclient.Frontend.SettingsWindow.SettingsWindowUtility;
import app.classmanagementappclient.Frontend.SettingsWindow.WebService.Load.WebServiceWindowLoad;
import app.classmanagementappclient.Frontend.TreeWindow.TreeWindowUtility;
import app.classmanagementappclient.Medium.Teacher.TeacherMedium;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class LogInOutSetting 
{
    public void loginInfoActionPerformed (SettingsWindow sw) 
    {
        JPanel settingsView = sw.getSettingsView();
        CardLayout cl = (CardLayout) settingsView.getLayout();
        cl.show(settingsView, "card3");
        SettingsWindowUtility.storeCurrentPanel("card3");
         
        Runnable runnable = () -> {
            setAccountLogUIComponents(sw);
        };
        sw.runSwingUtility(runnable);
    }
    
    public void logInBtnActionPerformed (SettingsWindow sw, MainWindow mainWindow) 
    {
        if (isFieldBlank (sw)) {
            sw.blankFieldsError();
            return;
        }
        
        Runnable run = () -> {
            sw.setLogInOutSettingPanelLoaded(false);
            Teacher teacher = sw.getTeacher(sw.getNameLogTextField(), sw.getPinLogPasswordField(), sw.getPhoneNbrLogTextField(), sw.getEmailTextField());
            TeacherMedium tm = new TeacherMedium();
            boolean res = tm.logIn(teacher);         
            afterTeacherMediumResults (sw, tm, res);
            mainWindowLoginSuceeded(mainWindow, res);
            WebServiceWindowLoad.apiKeyCheckUp(sw);
        };
        sw.runSwingUtility(run);        
    }
    
    public void logOutBtnActionPerformed (SettingsWindow sw, MainWindow mainWindow) 
    {
        Runnable run = () -> {
                sw.setLogInOutSettingPanelLoaded(false);
                mainWindowComponentDispose(mainWindow);
                TeacherMedium tm = new TeacherMedium();
                boolean res = tm.logOut();
                afterTeacherMediumResults(sw, tm, res);
                WebServiceWindowLoad.apiKeyCheckUp(sw);
            };
        sw.runSwingUtility(run);    
    }
    
    public void registerBtnActionPerformed (SettingsWindow sw, MainWindow mainWindow) 
    {
        if (isFieldBlank(sw)) {
            sw.blankFieldsError();
            return;
        }
        
        Runnable run = () -> {
            sw.setLogInOutSettingPanelLoaded(false);
            Teacher teacher = sw.getTeacher(sw.getNameLogTextField(), sw.getPinLogPasswordField(), sw.getPhoneNbrLogTextField(), sw.getEmailTextField());
            TeacherMedium tm = new TeacherMedium();
            boolean res = tm.registerTeacher(teacher);
            afterTeacherMediumResults (sw, tm, res);
            mainWindowLoginSuceeded(mainWindow, res);
            WebServiceWindowLoad.apiKeyCheckUp(sw);
        };

        sw.runSwingUtility(run);
    }
    
    private boolean isFieldBlank (SettingsWindow sw) {
        return sw.getNameLogTextField().getText().isBlank() || 
                Arrays.toString(sw.getPinLogPasswordField().getPassword()).isBlank() || 
                sw.getPhoneNbrLogTextField().getText().isBlank();
    }
    
    private void afterTeacherMediumResults (SettingsWindow sw, TeacherMedium tm, boolean res) {
        
        SwingUtilities.invokeLater(() -> {
            if (res)
                setAccountLogUIComponents (sw);
            setPaneMsg (res,tm.getMsg());
        });
    }     
    
    public void setAccountLogUIComponents (SettingsWindow sw) 
    {
        if (sw.isLogInOutSettingPanelLoaded())
            return;
        
        setAccountLogJLabelTitle (sw);
        buttonsEnable (sw, FrontEndUtility.isTeacherLoggedIn());
        setEmptyTextFields (sw);
        sw.setLogInOutSettingPanelLoaded(true);
        sw.setUserAccountSetingPanelLoaded(false);
    }      
    
    private void setAccountLogJLabelTitle (SettingsWindow sw) {
        sw.getLogAccountTitle().setText(FrontEndUtility.getLogAccountTitleText());
    }  
    
    private void buttonsEnable (SettingsWindow sw, boolean teacherLoggedIn) 
    {
        boolean enabled = true;
        if (teacherLoggedIn)
            buttonsEnable(new JButton[]{ sw.getLogInBtn(), sw.getRegisterBtn(), sw.getLogOutBtn(), /*sw.getForgotPinButton()*/ }, new boolean[] { !enabled, !enabled, enabled, /* !enabled */ });
        
        else
            buttonsEnable(new JButton[]{ sw.getLogInBtn(), sw.getRegisterBtn(), sw.getLogOutBtn(), /*sw.getForgotPinButton()*/ }, new boolean[] { enabled, enabled, !enabled, /* enabled */ });
    }     
    
    private void buttonsEnable (JButton[]btns, boolean[] enables) {
        for (int i = 0; i < enables.length; i++) {
            btns[i].setEnabled(enables[i]);
        }
    }
    
    private void setEmptyTextFields (SettingsWindow sw) 
    {
        JTextField[] textFields = { sw.getNameLogTextField(), sw.getPinLogPasswordField(), sw.getPhoneNbrLogTextField(), sw.getEmailTextField() };
        sw.getPinLogVisibleButton().setEnabled(false);
        boolean loggedIn = FrontEndUtility.isTeacherLoggedIn();
        
        for (JTextField tf : textFields) { 
            tf.setText("");
            tf.setEnabled (!loggedIn);
        }    
    }     

    private void setPaneMsg (boolean bool, String txt) {
        String title = bool ? "Informational Message" : "Error Message";
        int msgType = bool ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        FrontEndUtility.showMsgJOptionPane(txt, title, msgType);
    }  
    
    private void mainWindowLoginSuceeded (MainWindow mainWindow, boolean isSuccess) {
        if (!isSuccess)
            return;
        mainWindow.userLogin();
        FrontEndUtility.refresh(mainWindow);
    }
    
    private void mainWindowComponentDispose (MainWindow mainWindow) {
        disposeTreeWindow(mainWindow);
        disposeTabbedPane(mainWindow);
        mainWindow.userLogout();
        FrontEndUtility.refresh(mainWindow);
    }
    
    private void disposeTreeWindow (MainWindow mainWindow) 
    {
        if (mainWindow.getTreePanel() == null)
            return;
        mainWindow.removeComponentFromSplitPanel(mainWindow.getjTree());
        TreeWindowUtility.destroyTree(mainWindow);
        mainWindow.setjTree(null);
    }
    
    private void disposeTabbedPane (MainWindow mainWindow) {
        
        JTabbedPane pane = mainWindow.getJTabbedPane();
        Component[] components = pane.getComponents();
        for (Component component : components) {
            if (!(component instanceof JPanel))
                continue;
            pane.remove(component);
        }
        JSplitPane splitPane = mainWindow.getSplitPane();
        
        splitPane.setLeftComponent(null);
        splitPane.setRightComponent(null);        
        mainWindow.removeComponentFromSplitPanel(pane);   
        FrontEndUtility.refresh(splitPane);
    }
}