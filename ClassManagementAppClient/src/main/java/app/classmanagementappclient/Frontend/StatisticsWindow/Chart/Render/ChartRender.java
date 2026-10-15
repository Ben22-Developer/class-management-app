package app.classmanagementappclient.Frontend.StatisticsWindow.Chart.Render;

import app.classmanagementappclient.Frontend.Common.CommonDSManipulation;
import app.classmanagementappclient.Frontend.Exception.EmptyDatasetException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author BEN
 */
public class ChartRender {

    
    public static void showPieChart(JPanel statisticsGraphsPanel, List<List<Double>> pieChartData)
    {
        
          if (pieChartData.isEmpty())
              throw new EmptyDatasetException("Sorry I can't display charts, the dataset is empty");
          
          //create dataset
          DefaultPieDataset barDataset = new DefaultPieDataset();
          List<Double> zones = pieChartData.getFirst(); // from worst to excellent 
          
          barDataset.setValue( "Worst" , zones.getFirst());
          barDataset.setValue( "Good" , zones.get(1));
          barDataset.setValue( "Excellent" , zones.getLast());  
          

          //create chart example if 1 student has 12.5 % then red zone will be 100%, if he has 12.5% but also 68% then orange is 50% while red is also 50%
           JFreeChart piechart = ChartFactory.createPieChart("Student marks zone",barDataset, true,true,false);//explain

            PiePlot piePlot =(PiePlot) piechart.getPlot();

//           changing pie chart blocks colors
            piePlot.setSectionPaint("Worst", new Color(255,0,0));
            piePlot.setSectionPaint("Good", new Color(255,140,0));
            piePlot.setSectionPaint("Excellent", new Color(0,215,0));
            

            //create chartPanel to display chart(graph)
            ChartPanel barChartPanel = new ChartPanel(piechart);
            showChart(statisticsGraphsPanel, barChartPanel);
            
            System.out.println("[['Worst', 'Good', 'Excellent']]");
    }
    
    public static void showLineChart (JPanel statisticsGraphsPanel, List<List<Double>> perTermMarks, List<Integer> terms){
        
        
        if (perTermMarks.isEmpty())
            throw new EmptyDatasetException("Sorry I can't display charts, the dataset is empty");
        
        String yAxisKey = "Marks";
        
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 0; i < terms.size(); i++) 
            dataset.setValue(perTermMarks.getFirst().get(i), yAxisKey, "Term "+ terms.get(i));
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Marks per term","term","marks (100%)", 
                dataset, PlotOrientation.VERTICAL, true,true,false);
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(204,0,51);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        showChart(statisticsGraphsPanel, lineChartPanel);
    }    

    //
    public static void showHistogram (JPanel statisticsGraphsPanel, List<Double> doublesList){
    
        if (doublesList.isEmpty())
            throw new EmptyDatasetException("Sorry I can't display charts, the dataset is empty");
        
         double[] values = CommonDSManipulation.tranformDoubleListToArray(doublesList);
         
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Points", values, 100, 0.0, 100.0);

        JFreeChart chart = ChartFactory.createHistogram("Marks Frequency Histogram Visualizer","Marks", "Frequency", dataset,PlotOrientation.VERTICAL, true,true,false);
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
        showChart(statisticsGraphsPanel, barpChartPanel2);
    }
    
    private static void showChart (JPanel statisticsGraphsPanel, ChartPanel barChartPanel) {
        statisticsGraphsPanel.removeAll();
        statisticsGraphsPanel.add(barChartPanel, BorderLayout.CENTER);
        statisticsGraphsPanel.revalidate();
        statisticsGraphsPanel.repaint();
    }
    
}
