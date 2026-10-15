package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.Proxies;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.ClassExistanceChecker;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.ShowMsgInterface;
import javax.swing.*;

/**
 *
 * @author BEN
 */
public class MsgProxy1 implements ShowMsgInterface {

    @Override
    public void showMsg (ClassExistanceChecker checker) {
        
        int num = checker.getClassTermNum();
        String schoolName = checker.getSchoolName();
        String className = checker.getClassName();
        int year = checker.getYear();
        int term = checker.getTerm();
        
        if (num == Integer.MIN_VALUE)
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("School : " + schoolName + "\nClass: " + className + "\nAcademic year: " + year + "\nTerm: " + term + "\nDoesn't exist","Warning Message", JOptionPane.WARNING_MESSAGE);
    }
}
