package app.classmanagementappclient.Medium.Statistic;

import app.classmanagementappclient.Medium.Interfaces.Statistics.StatisticsMediumContract;

/**
 *
 * @author BEN
 */
public class StatisticMedium {
    
    public Object readData (StatisticsMediumContract contract) {
        return contract.readData();
    }
}
