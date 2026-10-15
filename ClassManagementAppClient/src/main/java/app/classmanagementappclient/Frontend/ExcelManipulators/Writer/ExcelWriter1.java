package app.classmanagementappclient.Frontend.ExcelManipulators.Writer;

import app.classmanagementappclient.Frontend.ExcelManipulators.ExcelManipulators;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ExcelWriter1 extends ExcelManipulators {

    private List<List<String>> content;
    private String sheetName;
    private int startingRow;
    private boolean openExcelFile;
    private final Workbook wb = new XSSFWorkbook();
    private FileOutputStream fileOutputStream;
    
    public ExcelWriter1 (File file, Sheet sheet, Row row, Cell cell, int clmsToManipulate, List<List<String>> content, String sheetName, int startingRow) {
        super(file, sheet, row, cell, clmsToManipulate);
        this.content = content;
        this.sheetName = sheetName; //"Students in class"
        this.startingRow = startingRow;
    }

    @Override
    public void runProcess() {
        
       
        try {
            
            Sheet sheet = super.getSheet();
            
            while (super.getSheet() == null) {
                sheet = wb.createSheet(sheetName);
                super.setSheet(sheet);
            }
            sheet.setDefaultColumnWidth(30);
            
            for (List<String> rowContent : content) {
                
                Row row = sheet.createRow(startingRow);
                int cellNum = 0;
                
                for (String cellContent : rowContent) {
                    
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(cellContent);
                    cellNum += 1;
                }
                startingRow += 1;
            }
            
            if (openExcelFile) {
                fileOutputStream = new FileOutputStream(super.getFile());
                wb.write(fileOutputStream);
                Desktop.getDesktop().open(super.getFile());
            }
        } 
        catch (Exception ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        finally 
        {
            try {
                if (fileOutputStream != null) {
                    wb.close();
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                System.out.println(ex);
                throw new RuntimeException();
            }
        }   
    }

    public int getStartingRow() {
        return startingRow;
    }

    public void setContent(List<List<String>> content) {
        this.content = content;
    }

    public void setStartingRow(int startingRow) {
        this.startingRow = startingRow;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public boolean isOpenExcelFile() {
        return openExcelFile;
    }

    public void setOpenExcelFile(boolean openExcelFile) {
        this.openExcelFile = openExcelFile;
    }
    
    public static ExcelWriter1 writeToExcelFile(String excelName, Sheet sheet, List<List<String>> content, String sheetName, int startingRow, boolean createDirectory, String...customDirectory) {
        
        ExcelWriter1 writer = new ExcelWriter1(fileValidate(excelName, createDirectory,customDirectory), sheet, null, null, 0, content, sheetName, startingRow);
        writer.runProcess();
        return writer;
    }   
    
    private static File fileValidate (String excelName, boolean createDirectory, String...customDirectory) {
        
        String userName = System.getProperty("user.name");
        String path = customDirectory.length == 0 ? "C:\\Users\\"+userName+"\\Class Management Files\\" : "C:\\Users\\"+userName+"\\Class Management Files\\" + customDirectory[0];
        File directory = create(path, createDirectory);
        return getFile(directory.getAbsolutePath() + "\\" + excelName);
    }
    
    private static File create (String path, boolean createDirectory) {
        
        File directory = new File( path );
        
        int i = 1;
        while (directory.exists() && createDirectory) {
            directory = new File(path + "("+ i + ")");
            i += 1;
        }        
        
        createDirectory(directory);
        
        return directory;
    }
    
    private static File createDirectory (File directory) {
        while (!directory.exists())
            directory.mkdir();
        return directory;
    }
    
    
    
    private static File getFile (String path) {
        
        int num = 1;
        File file = new File(path+".xlsx");
        
        while (file.exists()) {
            file = new File(path + "("+ num + ").xlsx");
            num+=1;
        }
        return file;
    }
}