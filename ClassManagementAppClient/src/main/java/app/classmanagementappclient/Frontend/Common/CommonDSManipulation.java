package app.classmanagementappclient.Frontend.Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author BEN
 */
public class CommonDSManipulation {
  
    public static<T> List<T> transformIteratorToList (Iterator<T> iterator) 
    {
        var list = new ArrayList<T>();
        
        while (iterator.hasNext())
            list.add(iterator.next());
        
        return list;
    }
    
    public static double[] tranformDoubleListToArray (List<Double>doubleList) {
        
        double[] doubleArray = new double[doubleList.size()];
        int i = 0;
        
        for (double d : doubleList) {
            doubleArray[i] = d;
            i += 1;
        }    
        
        return doubleArray;
    }
}
