package app.classmanagementappclient.Frontend.Common;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AllDataComboBoxFiller {
    
    public static void setComboBoxes 
        (JComboBox schoolBox, JComboBox classBox, JComboBox yearBox, List<String> schoolNames, List<String> classNames, List<Integer> classYearNames) 
    {
        
        setComboBox(schoolBox, schoolNames);
        setComboBox(classBox, classNames);
        setComboBox(yearBox, classYearNames);      
    }
    
    private static <T> void setComboBox (JComboBox<String> comboBox, List<T> items) {
        items.forEach(e -> {
            comboBox.addItem(String.valueOf(e));
        });
    }    
}
