package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.Handlers.DataFilter;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ExcelDataFilter {

    public void filterNumericsData(List<List<String>> data) {
        int i = 0; 
        int limit = 0;
        
        while (i < data.size()) 
        {
            boolean canLoop = handleTableRows(data, data.get(i), i);
            
            if (limit == 50)
                break;
            
            if (!canLoop) {
                limit ++;
                continue;
            }
            i ++;
            limit = 0;
        }        
    }
    
    private boolean handleTableRows (List<List<String>> data, List<String> rowData, int index) 
    {
        String num = rowData.getFirst(); // I expect the 1st cell to contain a numerical value (1.0), if not string 'No'
        
        if (num.equalsIgnoreCase("no")) {
            data.remove(index);
            return false;
        }
        
        String[] numSplit = num.split("[.]");
        
        rowData.set(0, numSplit[0]);
        return true;
    }    
}
