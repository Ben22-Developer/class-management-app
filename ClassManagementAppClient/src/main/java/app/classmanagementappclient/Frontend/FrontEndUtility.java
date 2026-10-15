package app.classmanagementappclient.Frontend;

import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *
 * @author BEN
 */
public class FrontEndUtility 
{
    private static final Properties uiStateRecordProperties = new Properties();
    private static final Properties warningMsgsProperties = new Properties();
    private static final Properties appModeProperties = new Properties();
    private static final Properties audioProperties = new Properties();
    private static final Properties uiViewProperties = new Properties();
    private static final Properties sentEmailsProperties = new Properties();
    
    private static final String  uiStateRecordKey = "UIStateRecord";
    private static final String warningMsgsKey = "WarningMessages";
    private static final String appModeKey = "AppMode";
    private static final String audioPropKey = "AudioProperties";
    private static final String uiViewKey = "UIView";
    private static final String sentEmailsKey = "SentEmails";
    
    public static final String menuSVGIconPath = "icon/svg/";
    public static final String menuLogoIconPath = Main.getApplicationFilesPaths().getProperty("appLogo");
    
    public static final String flatLafPropClassPath = "app.classmanagementappclient.Frontend.DashboardView.theme";
    
    private static boolean appIsStarting = true;
    
    public static void startApp() {
        
        loadProperties();
        setUIConstants();
        setLookAndFeel (Boolean.parseBoolean(appModeProperties.getProperty("LightMode")));
        setUIManagerDefaults();
        new MainWindow().setVisible(true);
    }    
    
    public static void setAppIsStartingToFalse () {
        appIsStarting = false;
    }
    
    private static void loadProperties () {
        loadUIStateRecordProperties();
        loadWarningMessagesProperties();
        loadAppModeProperties();
        loadAudioProperties ();
        loadUIViewProperties();
        loadSentEmailsProperties();
    }
    
    private static void loadUIStateRecordProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(uiStateRecordKey);
        PropertiesFileManipulator.propertyLoad(path, uiStateRecordProperties);        
    }
    
    private static void loadWarningMessagesProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(warningMsgsKey);
        PropertiesFileManipulator.propertyLoad(path, warningMsgsProperties);
    }
    
    private static void loadAppModeProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(appModeKey);
        PropertiesFileManipulator.propertyLoad(path, appModeProperties);
    }

    private static void loadAudioProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(audioPropKey);
        PropertiesFileManipulator.propertyLoad(path, audioProperties);
    }

    private static void loadUIViewProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(uiViewKey);
        PropertiesFileManipulator.propertyLoad(path, uiViewProperties);
    }

    private static void loadSentEmailsProperties () {
        String path = Main.getApplicationFilesPaths().getProperty(sentEmailsKey);
        PropertiesFileManipulator.propertyLoad(path, sentEmailsProperties);
    }
    
    public static void updateAppModeProperties (Boolean isLightMode) {
        appModeProperties.put("LightMode", isLightMode.toString());
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(appModeKey), appModeProperties);
    }    
    
    private static void setUIConstants () {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource(flatLafPropClassPath);
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));        
    }
    
    public static void setLookAndFeel (boolean isLightMode) 
    {   
        try {
            UIManager.setLookAndFeel(isLightMode == true ? new FlatLightLaf() : new FlatDarkLaf());
            
            if (FlatLaf.isLafDark())
                FlatDarkLaf.setup();
            else
                FlatLightLaf.setup();
            
        }
        catch (Exception ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
    
    public static void updateUI () {
        SwingUtilities.invokeLater(() -> {
            FlatLaf.updateUI();
        });
    }

    public static void updateUINoSafety () {
        FlatLaf.updateUI();
    }
    
    private static void setUIManagerDefaults () 
    {
       // Collections.list( UIManager.getDefaults().keys() ).forEach(e -> { System.out.println(e); });
        UIManager.put("OptionPane.messageFont", getDefaultAppFont());
        UIManager.put("TextField.font", getDefaultAppFont());
    }
    
    public static String getLogAccountTitleText () {
        boolean isLoggedIn = isTeacherLoggedIn();
        if (isLoggedIn)
            return "Hey " + LoggedInTeacher.getLoggedInTeacher().getName();
        else
            return "Hey Welcome User!";        
    }
    
    public static boolean isTeacherLoggedIn() {
        Properties teacherP = LoggedInTeacher.getProperties();
        return Boolean.parseBoolean(teacherP.getProperty("isLoggedIn"));
    }
    
    public static void sleep (int...msTime) 
    {
        int sleepTime = (msTime.length == 0) ? 5_000 : msTime[0];
        try {
        Thread.sleep(sleepTime);
        } 
        catch (InterruptedException ex) {}
    }
    
    public static Color getJLabelStatusColor (boolean status) {
        return (status == true) ? new Color(0, 100, 0) : Color.RED;
    }
    
    public static Font getDefaultAppFont () {
        return new Font("Segoe UI 18 Plain", Font.PLAIN, 16);
    }

    public static Font getBoldAppFont () {
        return new Font("Segoe UI 18 Plain", Font.BOLD, 16);
    }
    
    public static Font getTitleAppFont () {
        return new Font("Segoe UI 18 Plain", Font.BOLD, 18);
    }
    
    public static void addJComponentToPanel (JPanel jPanel, JComponent[] components) {
        for (JComponent component : components)
            jPanel.add(component);
    }    
    
    
    public static void addToRadioBtnsToButtonGrp (ButtonGroup buttonGroup, JRadioButton[] radioButtons) {
        
        for (JRadioButton radioButton : radioButtons)
            buttonGroup.add(radioButton);
    }    
    
    public static void setJComponentForeGroundColor (JComponent component, Color color) {
        component.setForeground(color);
    }  
    
    public static void setJComponentsDimension (JComponent[] components, Dimension dimension) {
        for (JComponent component : components) {
            setJComponentDimension(component, dimension);
        }        
    }
    
    public static void setJComponentDimension (JComponent component, Dimension dimension) {
        component.setPreferredSize (dimension);       
    }      
    
    public static void setJComponentsDefaultFont (JComponent[] components) {
        for (JComponent component : components) {
            component.setFont(FrontEndUtility.getDefaultAppFont());   
        }
    }
    
    public static void setJComponentDefaultFont (JComponent component) {
        component.setFont(FrontEndUtility.getDefaultAppFont()); 
    }    
    
    public static void setJTextField (JTextField textField) {
        textField.setColumns(20);
        textField.setFont(FrontEndUtility.getDefaultAppFont());
    }     

    public static void setJComponentsCustomFont (JComponent[] components, Font font) {
        for (JComponent component : components) {
            component.setFont(font);   
        }
    }
    
    public static void setJComponentCustomFont (JComponent component, Font font) {
        component.setFont(font);
    }    
    
    public static String getUserInput (String txt, String title) {
        String res = "";
        try {
            res = JOptionPane.showInputDialog(null, txt, title, JOptionPane.QUESTION_MESSAGE);
        }
        catch (Exception ex) {
            res = "";
        }
        return res;
    }
    
    public static void showMsgJOptionPane (String txt, String title, int msgType) {
        
        if (appIsStarting)
            return;
        
        JOptionPane pane = new JOptionPane(txt, msgType);
        JDialog dialog = pane.createDialog(title);
        displayJDialog(dialog);
//        JOptionPane.showMessageDialog(null, txt, title, msgType);
    }
    
    public static int showConfirmDialog (String txt, String title, int msgType) { 
        return JOptionPane.showConfirmDialog(null, txt, title, msgType);
    }
    
    private static void displayJDialog (JDialog dialog) {
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);        
    }
    
    public static boolean canDisplayWarningMsg (String key) {
        return Boolean.parseBoolean(String.valueOf(warningMsgsProperties.get(key)));
    }
    
    public synchronized static void updateDisplayWarningMsgProperty (String key, boolean bool) {
        warningMsgsProperties.replace(key, String.valueOf(bool));
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(warningMsgsKey), warningMsgsProperties);
    }
    
    public static int getWindowPanelToDisplay (String key) {
        return Integer.parseInt (uiStateRecordProperties.getProperty(key));
    }
    
    public static void storeWindowPanelToDisplay (String key, int index) {
        uiStateRecordProperties.replace(key, String.valueOf(index));
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(uiStateRecordKey), uiStateRecordProperties);
    }
    
    public static void storeUIViewProperties () {
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(uiViewKey), uiViewProperties);
    }
    
    public static String getUIViewValue (String key) {
        return String.valueOf(uiViewProperties.get(key));
    }
    
    public static boolean canPlayAudioFile (String key) {
        return Boolean.parseBoolean(audioProperties.get(key).toString());
    }
    
    public static void storeCanPlayAudioFileProp (String key, boolean canPlay) {
        audioProperties.replace(key, String.valueOf(canPlay));
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(audioPropKey), audioProperties);
    }
    
    public static Object getSentEmailsPropertiesValue (String key) {
        return sentEmailsProperties.get(key);
    }

    public static void setSentEmailsPropertiesValue (String key, String value) {
        sentEmailsProperties.replace(key, value);
    }    
    
    public static void storeSentEmailsProperties () {
        PropertiesFileManipulator.propertyStore(Main.getApplicationFilesPaths().getProperty(sentEmailsKey), sentEmailsProperties);
    }
    
    public static Component getBorderLayoutComponent (JPanel containerPanel, String constraint) {
        var layout = (BorderLayout) containerPanel.getLayout();
        return layout.getLayoutComponent(constraint);
    }
    
    public static void enableJComponents (JComponent[] components, boolean enable)
    {
        SwingUtilities.invokeLater(() -> 
        {
            for (JComponent component : components) 
                component.setEnabled(enable);            
        });
    }    

    public static void jTextEditable (JTextComponent[] components, boolean enable) {   
        for (var component : components) 
            component.setEditable(enable);
    }
    
    public static void setTextAreaColor (JTextArea textArea) {
        
        if (FlatLaf.isLafDark()) 
            
            textArea.putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:$Menu.background;"
                        + "foreground:$Menu.foreground;");        
        
        else
            
            textArea.putClientProperty(FlatClientProperties.STYLE, ""
                        + "background:$White;"
                        + "foreground:$Black;");   
    }    
    
    public void runOneFixedThread (Runnable runnable) 
    {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            executorService.submit(runnable);
        }
    }  
    
    public void runShowMsgJOptionPaneSwingWorker (String msg, String title, int msgType)
    {
//        SwingWorker<Object, Object> swingWorker = new SwingWorker<Object, Object> () {
//            @Override
//            protected Object doInBackground() throws Exception {
//                showMsgJOptionPane(msg, title, msgType);
//                return null;
//            }
//        };
//        swingWorker.execute();
        
        SwingUtilities.invokeLater(() -> {
            showMsgJOptionPane(msg, title, msgType);
        });
    }
    
    public static void componentValidate (JComponent component) {
        component.revalidate();
        component.repaint();
    }    
    
    public static void setPopupMenuColor (JPopupMenu popupMenu) {
        
        SwingUtilities.invokeLater(() -> 
        { 
            popupMenu.setBackground( FlatLaf.isLafDark() ? new Color(49,62,74) : Color.WHITE);
            
            for (Component component : popupMenu.getComponents())
                component.setForeground(FlatLaf.isLafDark() ? Color.WHITE : new Color(49,62,74));
        });        
    }
    
    public static void displayExcelFormatStudentInsertImageExample () {
        try {
            Desktop.getDesktop().open(new File(Main.getApplicationFilesPaths().getProperty("ExcelStudentFileInputImageExample")));
        } catch (IOException ex) {
            System.getLogger(FrontEndUtility.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }         
    }
    
    public static java.sql.Date getDate (JDateChooser dateChooser) {
        
        java.util.Date date = dateChooser.getDate();
        
        if (date == null)
            return null;
        
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.ofInstant(instant, zoneId);
        return java.sql.Date.valueOf( localDate );        
    }
    
    public static String getFormattedFrontEndTimeValue (int timeValue) {
        return timeValue < 10 ? "0" + String.valueOf(timeValue) : String.valueOf(timeValue);
    }
    
    public static String getAppFormatDate (Object dateObject) {
        
        
        LocalDate attendanceDate = LocalDate.parse(String.valueOf(dateObject)); // to parse string example: 2007-12-03
        
        String attendanceDay = FrontEndUtility.getFormattedFrontEndTimeValue(attendanceDate.getDayOfMonth());
        String attendanceMonth = FrontEndUtility.getFormattedFrontEndTimeValue(attendanceDate.getMonthValue());
        int attendanceYear = attendanceDate.getYear();        
        
        return attendanceDay + "/"+ attendanceMonth + "/" + String.valueOf(attendanceYear);
    } 
    
    public static String getRecordedAtTimeInAppFormat (Object dateTimeObject) {
        
        LocalDateTime recordedAt = (LocalDateTime) dateTimeObject;
        
        String recordedSeconds = FrontEndUtility.getFormattedFrontEndTimeValue(recordedAt.getSecond());
        String recordedMinutes = FrontEndUtility.getFormattedFrontEndTimeValue(recordedAt.getMinute());
        String recordedHour = FrontEndUtility.getFormattedFrontEndTimeValue(recordedAt.getHour());
        
        String recordedMonthDay = FrontEndUtility.getFormattedFrontEndTimeValue(recordedAt.getDayOfMonth());
        String recordedMonth = FrontEndUtility.getFormattedFrontEndTimeValue(recordedAt.getMonthValue());
        int recordedYear = recordedAt.getYear();      
        
        return recordedMonthDay + "/"+ recordedMonth + "/" + recordedYear + ", " + recordedHour+ ":" + recordedMinutes + ":" + recordedSeconds;
    }      
    
    /**
     *This method get string date: 01/02/2026 returns it's SQL Date format: 2026-02-01
     * @param dateString
     * @return
     */
    public static java.sql.Date getAppFormatDateToSQLFormatDate (String dateString) {
        LocalDate localDate = getLocalDate(dateString);
        return java.sql.Date.valueOf(localDate);
    }
    
    /**
     *This method can string timestamp get 21/02/2026, 06:19:47 returns it's SQL Timestamp format: 2026-02-21 06:19:47
     * @param timestamp
     * @return
     */
    public static Timestamp getAppFormatTimestampToSQLFormatTimestamp (String timestamp) {
        
        String[] dateTime = timestamp.split(", ");
        
        LocalDate localDate = getLocalDate(dateTime[0]);
        LocalTime localTime = getLocalTime(dateTime[1]);
        return Timestamp.valueOf( getLocalDateTime(localDate, localTime) );
    }
    
    private static LocalDate getLocalDate (String dateString) {
        String[] dateStrings = dateString.split("/");
        int dayOfMonth = Integer.parseInt(dateStrings[0]);
        int month = Integer.parseInt(dateStrings[1]);
        int year = Integer.parseInt(dateStrings[2]); 
        return LocalDate.of(year, month, dayOfMonth);
    }
    
    private static LocalTime getLocalTime (String timeString) {
        String[] timeStrings = timeString.split(":");
        int hour = Integer.parseInt(timeStrings[0]);
        int minute = Integer.parseInt(timeStrings[1]);
        int second = Integer.parseInt(timeStrings[2]); 
        return LocalTime.of(hour, minute, second);
    } 
    
    public static void refresh (JComponent component) {
        component.revalidate();
        component.repaint();
    }
    
    public static void refresh (JFrame frame) {
        frame.revalidate();
        frame.repaint();
    }
    
    public static void connection (JLabel label, boolean isConnected) {
        label.setForeground(isConnected ? Color.GREEN : Color.red);
        label.setText(isConnected ? "Connected" : "No Internert Connection"); 
    }
    
    private static LocalDateTime getLocalDateTime (LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static Properties getUiStateRecordProperties() {
        return uiStateRecordProperties;
    }

    public static Properties getWarningMsgsProperties() {
        return warningMsgsProperties;
    }

    public static Properties getAppModeProperties() {
        return appModeProperties;
    }

    public static String getUiStateRecordKey() {
        return uiStateRecordKey;
    }

    public static String getWarningMsgsKey() {
        return warningMsgsKey;
    }

    public static String getAppModeKey() {
        return appModeKey;
    }

    public static Properties getUiViewProperties() {
        return uiViewProperties;
    }
}
