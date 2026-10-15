package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.RowIdentifierRemoval;

import java.util.List;

/**
 *
 * @author BEN
 */
public class RowIdentifierRemoval {

    public void populateIdentifierNums (List<List<String>> rowsData, List<Integer> identifiers, int identifierIndex) {
        rowsData.forEach(e -> {
            int identifier = Integer.parseInt(e.remove(identifierIndex));
            identifiers.add(identifier);
        });
    }    
}
