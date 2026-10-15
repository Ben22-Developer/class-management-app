package app.classmanagementappclient.Frontend.ExcelManipulators.Reader;

import app.classmanagementappclient.Frontend.ExcelManipulators.ExcelManipulators;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ExcelReader extends ExcelManipulators {
    
    private final StringBuffer report = new StringBuffer();

    public ExcelReader(File file, Sheet sheet, Row row, Cell cell, int clmsToRead) throws IOException, InvalidFormatException {
        super(file, sheet, row, cell, clmsToRead);
    }

    @Override
    public void runProcess() 
    {
        try (Workbook wb = WorkbookFactory.create(super.getFile())) 
        {
            var sheet = super.getSheet();
            var row = super.getRow();
            var cell = super.getCell();
            var clmsToRead = super.getClmsToManipulate();
            var errLimit = 0;
            var rowNum = 0;
            
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(rowNum);

            while (true) {
                
                List<String> rowData = new ArrayList<>();
                
                for (int i = 0; i < clmsToRead; i++) {
                    
                    if (row == null) {
                        errLimit ++;
                        break;
                    }
                    else
                        errLimit = 0;
                    
                    cell = row.getCell(i);
                    
                    if (cell == null)
                        continue;
                    
                    CellType cellType = cell.getCellType();
                    
                    switch (cellType) {
                        case CellType.BOOLEAN -> setExcelRowData(cell.getBooleanCellValue(), rowData);
                        case CellType.NUMERIC -> setExcelRowData(cell.getNumericCellValue(), rowData);
                        case CellType.STRING -> setExcelRowData(cell.getStringCellValue(), rowData);
                    }
                }

                if (!rowData.isEmpty())
                    addRowDataToList(rowData);

                rowNum ++;
                if (errLimit > 100)
                    break;

                row = sheet.getRow(rowNum);
            }
        } 
        catch (IOException ex) {
            handleException();
        }        
    }
    
    private void handleException () {
        if (super.getData().isEmpty())
            super.setMsg("Application reader failed\nPlease save your excel file before resubmitting!");
    }
    
    private void setExcelRowData (Object object, List<String> rowData) {
        rowData.add(String.valueOf(object));
    }
    
    private void addRowDataToList (List<String> rowData) {
        if (rowData.size() != super.getClmsToManipulate()) {
            report.append("Incomplete data for: ").append(rowData).append("\n\n");
            return;
        }
        super.getData().add(rowData);
    }

    public StringBuffer getReport() {
        return report;
    }
    
    public static ExcelReader readExcelFile(File file, int clmsToRead) 
    {
        ExcelReader reader = null;
        try {
            reader = new ExcelReader(file, null, null, null, clmsToRead);
            reader.runProcess();
        } 
        catch (IOException | InvalidFormatException ex) {
            System.getLogger(ExcelManipulators.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return reader;
    }    
}
