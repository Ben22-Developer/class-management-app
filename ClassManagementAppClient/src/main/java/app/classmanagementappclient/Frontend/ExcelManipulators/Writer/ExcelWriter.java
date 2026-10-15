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
public class ExcelWriter extends ExcelManipulators {

    private final List<List<String>> content;
    private final String sheetName;
    
    public ExcelWriter(File file, Sheet sheet, Row row, Cell cell, int clmsToManipulate, List<List<String>> content, String sheetName) {
        super(file, sheet, row, cell, clmsToManipulate);
        this.content = content;
        this.sheetName = sheetName; //"Students in class"
    }

    @Override
    public void runProcess() {
        
        Workbook wb = null;
        FileOutputStream fileOutputStream = null;
        
        try
        {
            wb = new XSSFWorkbook();
            fileOutputStream = new FileOutputStream(super.getFile());
            
            Sheet sheet = super.getSheet();
            sheet = wb.createSheet(sheetName);
            
            int rowNum = 0;
            sheet.setDefaultColumnWidth(20);
            
            for (List<String> rowContent : content) {
                
                Row row = sheet.createRow(rowNum);
                int cellNum = 0;
                
                for (String cellContent : rowContent) {
                    
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(cellContent);
                    cellNum += 1;
                }
                rowNum += 1;
            }
            
            wb.write(fileOutputStream);
            Desktop.getDesktop().open(super.getFile());
            
        } 
        catch (Exception ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        finally 
        {
            try {
                wb.close();
                fileOutputStream.close();
            } catch (Exception ex) {
                System.out.println(ex);
                throw new RuntimeException();
            }
        }
        
    }
    
    public static ExcelWriter writeToExcelFile(String excelName, List<List<String>> content, String sheetName) {
        
        ExcelWriter writer = new ExcelWriter(fileValidate(excelName), null, null, null, 0, content, sheetName);
        writer.runProcess();
        return writer;
    }   
    
    private static File fileValidate (String excelName) {
        
        String userName = System.getProperty("user.name");
        String path = "C:\\Users\\"+userName+"\\Class Management Files\\";
        File directory = create(path);
        return getFile(directory.getAbsolutePath() + "\\" + excelName);
    }
    
    private static File create (String path) {
        
        File directory = new File( path );
        
        while (!directory.exists()) {
            directory.mkdir();
        }
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