package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.ClassOptions;

import app.classmanagementappclient.Medium.Class.ClassInsertMedium.Proxies.InsertClassProxy;
import app.classmanagementappclient.Medium.Class.ClassMedium;
import app.classmanagementappclient.Medium.ClassYear.ClassYearMedium;
import app.classmanagementappclient.Medium.ClassYear.InsertClassYear.Proxies.InsertClassYearProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.ClassExistanceChecker;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.Proxies.MsgProxy2;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.InsertTreeDataFromInputWindow;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeMediums.Insert.InsertTreeDataMedium;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassOptions {
    
    private final JLabel editClassLabel = new JLabel("Edit Class");
    private final JLabel saveClassLabel = new JLabel("Save class if not saved");
    private final JButton saveBtn = new JButton("Save");
    private final MyJDialog dialog = new MyJDialog();
    private final InputWindow inputWindow;
    
    public ClassOptions (InputWindow inputWindow) {
        this.inputWindow = inputWindow;
    }
    
    public void run () {
        
        setFont();
        addNorthElements();
        addCenterElements();
        addSouthElements();
        addListeners ();
        setDialogProp();
    }
    
    private void setDialogProp () {
        dialog.setTitle("Class Options");
        dialog.setMinimumSize(new Dimension(600,200));
        dialog.setLocation(400, 200);
        dialog.setVisible(true);        
    }
    
    private void addNorthElements () {
        
        JPanel panel = new JPanel(new FlowLayout(SwingConstants.LEADING, 10, 10));
        panel.add(editClassLabel);
        dialog.add(panel,BorderLayout.NORTH);
    }
    
    private void addCenterElements () {
        
        JPanel panel = new JPanel(new GridLayout(1, 4, 20 ,20));   
        
        panel.setMinimumSize(new Dimension(600, 10));
        
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[] { inputWindow.getRecordedSchoolNewStudentComboBox(), inputWindow.getRecordedClassNewStudentComboBox(), inputWindow.getRecordedYearNewStudentComboBox(), inputWindow.getRecordedTermsNewStudentComboBox() });
        
        dialog.add(panel,BorderLayout.CENTER);        
    }
    
    private void addSouthElements () {
        
        JPanel southPanel = new JPanel(new BorderLayout());
        
        JPanel panel = new JPanel (new FlowLayout(SwingConstants.TOP, 10, 15));
        panel.add(saveClassLabel);
        
        JPanel panel1 = new JPanel();
        panel1.add(saveBtn);
        
        southPanel.add(panel, BorderLayout.NORTH);
        southPanel.add(panel1, BorderLayout.CENTER);
        
        dialog.add(southPanel,BorderLayout.SOUTH);        
    }
    
    private void addListeners() {
        saveBtn.addActionListener((l) -> {
            new SwingWorker<Object, Object> () {
                @Override
                protected Object doInBackground() throws Exception {
                    eventHandler();
                    return null;
                }  
            }.execute();
        });
    }    
    
    private void eventHandler () {
        
        ClassExistanceChecker checker = ClassExistanceChecker.getClassExistanceChecker(inputWindow);
        checker.checkClassExistance(new MsgProxy2());
        
        if (checker.getClassTermNum() == Integer.MIN_VALUE)
            insertNewClassTerm();
    }
    
    private void insertNewClassTerm () {       
        
//        checkClassYearExistance();

        String schoolName = String.valueOf( inputWindow.getRecordedSchoolNewStudentComboBox().getSelectedItem() );
        String className = String.valueOf( inputWindow.getRecordedClassNewStudentComboBox().getSelectedItem() );
        String year = String.valueOf( inputWindow.getRecordedYearNewStudentComboBox().getSelectedItem() );
        String term = String.valueOf( inputWindow.getRecordedTermsNewStudentComboBox().getSelectedItem() );        
//        List<String> classTerm = List.of (schoolName, className, year);

        var itdfiw =  new InsertTreeDataFromInputWindow (inputWindow.getjTree());
        itdfiw.dataInsert(schoolName, className, year, term, new HashSet<>());
        boolean bool = itdfiw.getIsInserted() == 1;         
        
//        boolean bool = new InsertTreeDataMedium(inputWindow.getjTree(), classTerm).insertClassTerm(term, new InsertClassTermProxy());
        
        if (bool)
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Class Inserted Successfully", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void checkClassYearExistance () {
        
        String schoolName = String.valueOf( inputWindow.getRecordedSchoolNewStudentComboBox().getSelectedItem() );
        String className = String.valueOf( inputWindow.getRecordedClassNewStudentComboBox().getSelectedItem() );
        String year = String.valueOf( inputWindow.getRecordedYearNewStudentComboBox().getSelectedItem() );
        
        while (new ClassYearMedium().getOneClassYearNumV1(schoolName, className, Integer.parseInt(year)) == Integer.MIN_VALUE) {
            checkClassExistance();
            new InsertTreeDataMedium(List.of(schoolName, className)).insertClassYear(year, new InsertClassYearProxy());
        }
        
    }

    private void checkClassExistance () {

        String schoolName = String.valueOf( inputWindow.getRecordedSchoolNewStudentComboBox().getSelectedItem() );
        String className = String.valueOf( inputWindow.getRecordedClassNewStudentComboBox().getSelectedItem() );
        
        while (new ClassMedium().getOneClassNumV1(schoolName, className) == Integer.MIN_VALUE)
            new InsertTreeDataMedium(List.of(schoolName)).insertClass(className, new InsertClassProxy());
    }    
    
    private void setFont () {
        FrontEndUtility.setJComponentsCustomFont(new JComponent[] { editClassLabel, saveClassLabel }, new Font("Arial", Font.BOLD, 16));
        FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { saveBtn });
    }
}
