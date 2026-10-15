package app.classmanagementappclient.Frontend.JProgress;

import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author BEN
 */
public class ProgressTracker 
{
    private int loadingPercentage;
    private final JProgressBar progressBar;
    
    public ProgressTracker(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    
    public void run () 
    {
        loadingPercentage = 0;
        
        Runnable runnable = () -> 
        {
            while (loadingPercentage < 100) {
                FrontEndUtility.sleep(100);
                progressBar.setValue(loadingPercentage);
            }
            System.out.println("JTableProgress.run service execution ended");
        };
        
        runExecutorService(runnable);
    }
    
    private void runExecutorService (Runnable runnable) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(runnable);
        es.shutdown();
    }
    
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    
    public void setLoadingPercentage (int currValue, int total) {
        loadingPercentage = (int) (((double) currValue/total)*100);
    }    

    public synchronized void endProgress() {
        setTableLoadingPercentage(100);
    }
    
    public void setTableLoadingPercentage (int loadingPercentage) {
        this.loadingPercentage = loadingPercentage;
    } 
    
    public int getLoadingPercentage () {
        return loadingPercentage;
    }
}
