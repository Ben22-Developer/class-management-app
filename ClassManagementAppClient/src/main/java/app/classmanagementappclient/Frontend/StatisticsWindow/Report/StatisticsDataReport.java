/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.StatisticsWindow.Report;

import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.StatisticsWindow.StatisticsWindowDialog;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class StatisticsDataReport {
 
    
    public static void renderReport (StatisticsWindowDialog window) 
    {
        SwingUtilities.invokeLater(() -> {
            run(window);
        });
    }
    
    private static void run (StatisticsWindowDialog window) {
        
        new ShowReport()
        .showReport(
            """
                Current Selection
            ---------------------------

            - School: %s\n
            - Class: %s\n
            - Academic Year: %s\n

            - Terms selected
            ---------------------
            %s\n
            - Lessons selected
            ---------------------
            %s\n
            - Student selected
            ---------------------
            %s\n
            """.formatted(
                    window.getSchoolBox().getSelectedItem().toString(),
                    window.getClassBox().getSelectedItem().toString(),
                    window.getAcademicYearBox().getSelectedItem().toString(),
                    window.getClassTerm() == null ? "" : intList(window.getClassTerm()),
                    window.getSelectedClassLessons() == null ? "" : stringList(window.getSelectedClassLessons()),
                    window.getSelectedStudents()== null ? "" : stringList(window.getSelectedStudents())
        ));
    }
    
    private static String intList (List<Integer>ints) {
        
        StringBuilder sb = new StringBuilder();
        
        for (int anInt : ints)
            sb.append("        ").append("Term ").append(anInt).append("\n");
        
        return sb.toString();
    }
    
    private static String stringList (List<String>strings) {
        
        StringBuilder sb = new StringBuilder();
        
        for (String string : strings)
            sb.append("        ").append(string).append("\n");
        
        return sb.toString();
    }    
}
