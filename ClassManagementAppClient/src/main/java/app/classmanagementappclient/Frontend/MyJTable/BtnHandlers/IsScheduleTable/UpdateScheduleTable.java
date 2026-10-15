/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MyJTable.BtnHandlers.IsScheduleTable;

import app.classmanagementappclient.Backend.Objects.CustomGson.LocalDateAdapter;
import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ScheduleTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BEN
 */
public class UpdateScheduleTable {
    
    private final List<String> currRecordsData;
    private final List<String> newRecordsData;
    private final Table table;
    private final int row;
    
    public UpdateScheduleTable(List<String> currRecordsData, List<String> newRecordsData, Table table, int row) {
        this.currRecordsData = currRecordsData;
        this.newRecordsData = newRecordsData;
        this.table = table;
        this.row = row;
    }
    
    public boolean run () {
        
        var thiss = this;
        
        var sw = new SwingWorker<Object, List<String> > () 
        {   
//            private int scheduleTableClmIndex = 2;
            @Override
            protected Object doInBackground() throws Exception {
                
                serverUpdate();
//                List<String> s = thiss.newRecordsData;
                publish(List.of(thiss.newRecordsData.getFirst(), thiss.newRecordsData.get(1), thiss.newRecordsData.getLast()));
                return true;
            }

            @Override
            protected void process(List<List<String>> chunks) {
                for (List<String> chunk : chunks)
                    thiss.table.setValueAt(row, chunk);
            }
            
            private void serverUpdate () {
                
                Date currDate = FrontEndUtility.getAppFormatDateToSQLFormatDate(currRecordsData.getLast());
                Date newDate = FrontEndUtility.getAppFormatDateToSQLFormatDate(newRecordsData.getLast());
                
                
                Task currData = new Task(currRecordsData.getFirst(), currRecordsData.get(1), LocalDate.parse(currDate.toString()));
                Task newData = new Task(newRecordsData.getFirst(), newRecordsData.get(1), LocalDate.parse(newDate.toString()));
                String boundary = UUID.randomUUID().toString();
                
//                HttpRequest.BodyPublishers[] publishers =
                
                var request = HttpRequest
                        .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.putSchedule))
                        .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                        .header("Content-type", "multipart/form-data; boundary="+boundary)
                        .PUT(HttpRequest.BodyPublishers.ofByteArray(getPublishers(currData, newData, boundary)))
                        .build();
                
                var client = CommonWebServiceFeatures
                        .getHttpResponse(request, getConnectionLabel());
                
                if (client.statusCode() > 299) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(client.body().toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException();
                }
            }
            
            
            private byte[] getPublishers (Task currData, Task newData, String boundary) {

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                
                List<byte[]> byteList = new ArrayList<>();
                
                String currDataJson = gson.toJson(currData);
                String newDataJson = gson.toJson(newData);
                
                CommonWebServiceFeatures.addMultipartBytes(byteList, boundary, "currData", currDataJson, "application/json");
                CommonWebServiceFeatures.addMultipartBytes(byteList, boundary, "newData", newDataJson, "application/json", true);
                
                return CommonWebServiceFeatures.formartMultipartBytesArr(byteList);
            }
            
            private JLabel getConnectionLabel () {
                var scheduleTable = (ScheduleTable) table;
                return scheduleTable.getConnectionLabel();
            }
        };
        
        try 
        {
            sw.execute();
            return (boolean) sw.get();
        }
        catch (Exception e) {}
        
        return false;
    }
}
