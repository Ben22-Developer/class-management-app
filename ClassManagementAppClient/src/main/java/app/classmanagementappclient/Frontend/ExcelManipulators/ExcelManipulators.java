package app.classmanagementappclient.Frontend.ExcelManipulators;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.ExcelFileInsert.ExcelFileStudentInsert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class ExcelManipulators 
{
    private final List<List<String>> data = new ArrayList<>();
    private final File file;
    private Sheet sheet;
    private final Row row;
    private final Cell cell;
    private final int clmsToManipulate;
    private String msg;
    
    public ExcelManipulators(File file, Sheet sheet, Row row, Cell cell, int clmsToManipulate) {
        this.file = file;
        this.row = row;
        this.cell = cell;
        this.clmsToManipulate = clmsToManipulate;
        this.sheet = sheet;
    }

    public File getFile() {
        return file;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public Row getRow() {
        return row;
    }

    public Cell getCell() {
        return cell;
    }

    public int getClmsToManipulate() {
        return clmsToManipulate;
    }

    public List<List<String>> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
    
    public String getEmptyListMsg () {
        return "Application reader failed\n1.Review the excel format, on how data is to be formatted, Via settings.\n2.Make sure the data to be inputted doesn't have null cells.\n3.Save your file before submitting.";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
   
    public abstract void runProcess();
    
    public static File getExcelFile () {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\BEN\\OneDrive\\Desktop\\ClassManagementApplicationProject\\Application Tests");
        File file = null;
        fileChooser.showOpenDialog(null);
        fileChooser.setVisible(true);
        file = fileChooser.getSelectedFile();
        return file;        
    }
    
    public static boolean isExcelFile (File file) 
    {
        try {
            if (Files.probeContentType(file.toPath()).equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                return true;
        } 
        catch (IOException ex) {
            System.getLogger(ExcelFileStudentInsert.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Invalid file type\nOnly excel files are allowes\n'.xlsx' extension files", "Error Message", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
