package app.classmanagementappclient.Backend.Logic.ProgramFunctions;

import java.util.List;

/**
 *
 * @author BEN
 */
public class ProgramUtility 
{
    public static<T> String[] getStringArrayFromList (List<T> list) 
    {
        String[] stringArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            stringArr[i] = String.valueOf(list.get(i));
        return stringArr;
    }
}
