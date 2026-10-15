package app.classmanagementappclient.Frontend.MyJDialog;

import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class ADialog 
{
    private Point locationPoint = new Point(400,165);
    private final List<String> userInputs = new ArrayList<>();
    private List<String> headers;
    private List<String> values;
    private JDialog dialog;
    private List<JTextField> textFields;
    private String titleLable = "Edit";
    private String windowTitle = "Student Addition";
    private final JButton submitBtn = new JButton("Submit");
    
    public void drawJDialog () 
    {
        initDialog();
        initTextFields();
        
        btnListenerRenew();
        addTitleLabel();
        addCenterPanel();
        setSouthPanelComponents();
        addEventListener();
        setJDialogProp();        
    }    
    
    protected void initDialog () {
        dialog = new JDialog();
    }
    
    protected void initTextFields () {
        textFields = new ArrayList<>();
    }
    
    protected void btnListenerRenew () {
        ActionListener[] listeners = submitBtn.getActionListeners();
        for (ActionListener listener : listeners)
            submitBtn.removeActionListener(listener);
    }
    
    protected void addTitleLabel () 
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(titleLable);
        setTitleJLabel(label, new Dimension(100,40));
        Dimension dimension = new Dimension(300, 40);
        setJPanel(panel, new JComponent[] { label }, dimension);
        dialog.add(panel, BorderLayout.NORTH);
    } 
    
    protected void addCenterPanel ()
    {
        List<JPanel> smallPanels = new ArrayList<>();
        
        for (int i = 0; i < headers.size(); i++) 
        {
            String labelTxt = headers.get(i);
            String value = values.get(i);
            JLabel label = new JLabel(labelTxt);
            JTextField textField = new JTextField (value);
            setJComponent(label,  new Dimension(120,40));
            FrontEndUtility.setJTextField(textField);
            Dimension dimension = new Dimension(500,50);
            JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
            setJPanel(jPanel, new JComponent[] { label, textField}, dimension);
            textFields.add(textField);
            smallPanels.add(jPanel);
        }
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new GridLayout(smallPanels.size(), 1, 0, 5));
        smallPanels.forEach((JPanel panel) -> {
            bigPanel.add(panel);
        });
        dialog.add(bigPanel, BorderLayout.CENTER);
    }
    
    protected void setSouthPanelComponents () 
    {
        JPanel panel = new JPanel();
        Dimension dimension = new Dimension(300, 50);
        setJButton(submitBtn);
        setJPanel(panel, new JComponent[] {submitBtn}, dimension);
        dialog.add(panel, BorderLayout.SOUTH);
    }
    
    protected void addEventListener () 
    {
        submitBtn.addActionListener((ActionEvent l ) -> {
            handleSubmitEvent();
        });
        
        for (JTextField textField : textFields) 
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
    
    protected void handleSubmitEvent () 
    {
        for (JTextField textField : textFields) {
            String txt = textField.getText();
            if (txt.isBlank()) {
                    showErrorMsg();
                    userInputs.clear();
                return;
            }    
            userInputs.add(txt);
        }
            
        finalTouch();
    }
    
    protected void finalTouch () {
        if (!handler()) { 
            userInputs.clear();
            return;
        }
        dialog.dispose();
        dialog = null;        
    }
    
    protected void setJComponent (JComponent component, Dimension dimension) 
    {
        component.setFont(FrontEndUtility.getDefaultAppFont());
        component.setPreferredSize(dimension);
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
    }
    
    protected void setTitleJLabel (JLabel label, Dimension dimension) {
        label.setFont(FrontEndUtility.getTitleAppFont());
        label.setSize(dimension);
    }    
    
    protected void setJButton (JButton btn) 
    {
        btn.setFont(FrontEndUtility.getDefaultAppFont());
        btn.setSize(new Dimension(100,20));
    }    
    
    protected void setJPanel (JPanel panel, JComponent[] components, Dimension dimension) 
    {
        panel.setPreferredSize (dimension);        
        for (JComponent component : components) {
            panel.add(component);
        }
    }
    
    protected void showErrorMsg () {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("None of the fields must be blank!", "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    protected void setJDialogProp () 
    {
        
        SwingWorker<Object, Object> swingWorker = new SwingWorker<Object, Object> () {
            
            @Override
            protected Object doInBackground() throws Exception 
            {
                dialog.setModal(true);
                dialog.setLocation (locationPoint);
                dialog.pack();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setTitle(windowTitle);
                dialog.setResizable(false);            
                return null;
            }
            
            @Override
            public void done () {
                dialog.setVisible(true);
            }
        };
        swingWorker.execute();
    }


    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getSelectedRowData() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getUserInputs() {
        return userInputs;
    }    

    public JDialog getDialog() {
        return dialog;
    }

    public List<JTextField> getTextFields() {
        return textFields;
    }

    public String getTitleLable() {
        return titleLable;
    }

    public void setTitleLable(String titleLable) {
        this.titleLable = titleLable;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public Point getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(Point locationPoint) {
        this.locationPoint = locationPoint;
    }
    
    public abstract boolean handler();
}
