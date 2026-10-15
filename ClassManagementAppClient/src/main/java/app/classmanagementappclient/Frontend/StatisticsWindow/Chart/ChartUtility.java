/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.StatisticsWindow.Chart;

import app.classmanagementappclient.Frontend.Exception.EmptyDatasetException;
import app.classmanagementappclient.Frontend.Exception.InvalidDataException;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.ChartEnum.ChartEnum;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataCollection.ChartDataCollect;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataValidate.Contract.ChartValidatorContract;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataValidate.Implement.ChartDataValidator;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.Render.ChartRender;
import app.classmanagementappclient.Frontend.StatisticsWindow.Chart.Render.RenderNumbers;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ChartUtility {

    private final ChartValidatorContract validatorContract;
    private final StatisticsWindowDialog window;
    
    public ChartUtility(StatisticsWindowDialog window) {
        this.validatorContract = new ChartDataValidator(window);
        this.window = window;
    }
    
    public void run () {
        try {
            validatorContract.validate();
            loadData();
        }
        catch (InvalidDataException ex) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    private void loadData () {

        String schoolName = window.getSchoolBox().getSelectedItem().toString();
        String className = window.getClassBox().getSelectedItem().toString();
        int classYear = Integer.parseInt( window.getAcademicYearBox().getSelectedItem().toString() );
        List<Integer> classTerms = window.getClassTerm();
        List<String> classLessons = window.getSelectedClassLessons();
        List<String> selectedStudentsIDs = window.getSelectedStudents();
        String chartName = window.getChartBox().getSelectedItem().toString();
        
        new SwingWorker<Object, Object> () {
            @Override
            protected Object doInBackground() throws Exception 
            {

                var chartData = new ChartDataCollect (schoolName, className, classYear, classTerms, classLessons, selectedStudentsIDs);

                chartData.setChart(chartName);
                ChartEnum chartEnum = chartData.getChart();

                Object data = chartData.getChartData();

                System.out.println("here 1 " + data);

                try {
                    renderChart(data, chartEnum);    
                    renderNumbers(data, chartEnum);
                }
                catch (EmptyDatasetException ex) {
                    FrontEndUtility.showMsgJOptionPane(ex.getMessage(), "Informational Message", JOptionPane.INFORMATION_MESSAGE);
                }
                return null;
            }
        
        }.execute();
    }
    
    private void renderChart (Object data, ChartEnum chartEnum) throws EmptyDatasetException {
    
        switch (chartEnum) {
            case ChartEnum.PieChart -> ChartRender.showPieChart(window.getStatisticsChartsPanel(), (List<List<Double>>) data);
            case ChartEnum.LineChart -> ChartRender.showLineChart(window.getStatisticsChartsPanel(), (List<List<Double>>) data, window.getClassTerm());
            case ChartEnum.Histogram -> ChartRender.showHistogram(window.getStatisticsChartsPanel(), (List<Double>) data);
            default -> throw new AssertionError("Unknown ChartEnum at ChartUtility.renderChart");
        }
    }

    private void renderNumbers (Object data, ChartEnum chartEnum) {
    
        switch (chartEnum) {
            case ChartEnum.PieChart, ChartEnum.LineChart -> {
                var list = (List<List<Double>>) data;
                RenderNumbers.renderNumbers(window, list.getFirst());
            }
            case ChartEnum.Histogram -> RenderNumbers.renderNumbers(window, (List<Double>) data); // ChartRender.showHistogram(window.getStatisticsChartsPanel(), (List<Double>) data);
            default -> throw new AssertionError("Unknown ChartEnum at ChartUtility.renderChart");
        };
    }    
}
