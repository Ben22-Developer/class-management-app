package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.OneByOneInsert;

import app.classmanagementappclient.Medium.Student.StudentMedium;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.MyJDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class InsertOneStudentDialog {

    private final MyJDialog dialog = new MyJDialog();
    private final JTextField textField = new JTextField ();
    private final JTextField textField1 = new JTextField ();   
    private final JButton button = new JButton ("Submit");  
    private final Table table;
    private volatile boolean inserted;

    public InsertOneStudentDialog(Table table) {
        this.table = table;
    }
    
    public void run () {
        addCenterPanel();
        addSouthPanelComponents();
        addEventListener();
        setDialog ();
        addDialogListener();
    }
    
    private void addCenterPanel ()
    {
        JLabel label = new JLabel ("Student ID: ");
        JLabel label1 = new JLabel("Student Name: ");
        
        List<JPanel> smallPanels = new ArrayList<>();
        JLabel[] labels = {label, label1};
        JTextField[] fields = { textField, textField1 };
        
        for (int i = 0; i < fields.length; i++) 
        {
            FrontEndUtility.setJComponentsDefaultFont(new JComponent[] { labels[i], fields[i]});
            FrontEndUtility.setJComponentDimension(labels[i], new Dimension(120,40));
            FrontEndUtility.setJTextField(fields[i]);
            
            JPanel jPanel = new JPanel(new FlowLayout(SwingConstants.CENTER, 50, 10));
            
            FrontEndUtility.setJComponentDimension(jPanel, new Dimension(500,50));
            FrontEndUtility.addJComponentToPanel(jPanel, new JComponent[] { labels[i], fields[i] });
            
            smallPanels.add(jPanel);
        }
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(smallPanels.size(), 1, 0, 5));
        smallPanels.forEach((JPanel panel) -> {
            bigPanel.add(panel);
        });
        dialog.add(bigPanel, BorderLayout.CENTER);
    }
    
    private void addSouthPanelComponents () 
    {
        JPanel panel = new JPanel();
        Dimension dimension = new Dimension(300, 50);
        FrontEndUtility.setJComponentsDefaultFont (new JComponent[] {button});
        FrontEndUtility.setJComponentDimension(panel, dimension);
        FrontEndUtility.addJComponentToPanel(panel,  new JComponent[] {button});
        dialog.add(panel, BorderLayout.SOUTH);
    }
    
    private void addEventListener () 
    {
        button.addActionListener((ActionEvent l ) -> {
            handleSubmitEvent();
        });
        
        for (JTextField textField : new JTextField[] { textField, textField1 }) 
        {
            textField.addKeyListener(new KeyAdapter () {
                @Override
                public void keyReleased (KeyEvent k) {
                    if (k.getKeyCode() == KeyEvent.VK_ENTER)
                        handleSubmitEvent();   
                }
            });
        }
    }   

    private void handleSubmitEvent() {
        
        Student student = new Student(textField.getText(), textField1.getText());
        StudentMedium medium = new StudentMedium (student);
        boolean b = medium.validation();
        if (b) 
            dataInsert();
        else
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(medium.getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    protected void dataInsert () {
        SwingUtilities.invokeLater(() -> 
        {
            DefaultTableModel model = table.getDefaultTableModel();
            model.addRow(new Object[] { true, textField.getText(), textField1.getText(), "Edit" });
            dialog.dispose();
            inserted = true;
        });
    }

    protected void setDialog() {
        
        Runnable doRun = () -> 
        {
            dialog.setTitle("Student Details");
            dialog.pack();  
            dialog.setLocation(400, 200);
            dialog.setVisible(true);
        };
        SwingUtilities.invokeLater(doRun);
    }
    
    protected void addDialogListener() {
        dialog.addWindowListener(new WindowAdapter () {
            public void windowClosed(WindowEvent e) {
                setInserted(true);
            }
        });        
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public Table getTable() {
        return table;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public MyJDialog getDialog() {
        return dialog;
    }
}
