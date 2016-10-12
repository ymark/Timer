package gr.forth.ics.isl.timer;

import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * 
 * This class is responsible for creating a bar chart with the timers. The user can either create a 
 * bar chart using specific timers, or automatically create it (using all the available timers). 
 * In the first case the user can also specify accumulated timers, while in the second case they are not supported.
 * Below we provide two examples: one for creating a plot using specific timers and one using 
 * the automatic creation facility.
 * <ul><li>Automatic creation
 *     <ul><li> PlotFactory(TimeUnit.SECONDS).withAutomaticSettings().createPlot(file); </li></ul>
 *     </li>
 *     <li>Custom creation
 *     <ul><li> PlotFactory(TimeUnit.SECONDS).withEntry("timer1").withEntry("timer2").createPlot(file); </li></ul>
 *     </li>
 * </ul>
 */
public class PlotReporter {
    private DefaultCategoryDataset dataset;
    private TimeUnit timeUnit;
    
    private PlotReporter(TimeUnit timeUnit){
        this.dataset=new DefaultCategoryDataset();
        this.timeUnit=timeUnit;
    }
    
    /** Creates a new PlotReporter object for generating the chart diagram. The factory method 
     * takes as input the desired time unit (i.e. msec, sec, etc.)
     * 
     * @param timeUnit the desired time unit (i.e. msec, sec, etc.)
     * @return the PlotReporter object */
    public static PlotReporter PlotFactory(TimeUnit timeUnit){
        return new PlotReporter(timeUnit);
    }
    
    /** Adds a new timer in the chart diagram. 
     * 
     * @param timerName the name of the timer or the name of an accumulated timer.
     * @return the (updated) PlotReporter object */
    public PlotReporter withEntry(String timerName){
        this.dataset.addValue(Timer.report(timerName,this.timeUnit), timerName, "");
        return this;
    }
    
    /** Add the default timer (which does not have a name) in the chart diagram.
     * 
     * @return the (updated) PlotReporter object */
    public PlotReporter withDefaultEntry(){
        this.dataset.addValue(Timer.report(this.timeUnit), ReporterResources.DEFAULT_TIMER_LABEL, "");
        return this;
    }
    
    /** Adds all the available timers (the ones created by the user and the default timer if it exists)
     * in the chart diagram. Only the timers that have been explicitly created will be added. No accumulated
     * timers will be added. 
     * 
     * @return the (updated) PlotReporter object */
    public PlotReporter withAutomaticSettings(){
        if(Timer.report(this.timeUnit)>0){
            this.dataset.addValue(Timer.report(this.timeUnit),ReporterResources.DEFAULT_TIMER_LABEL,"");
        }
        for(String timer : Timer.getAllTimers()){
            this.dataset.addValue(Timer.report(timer,this.timeUnit),timer,"");
        }
        return this;
    }
    
    /** Creates an image file containing the chart diagram with the desired dimensions.
     * The supported format for the file is JPEG. 
     * 
     * @param fileJpeg the file that will be exported. If it already exists, it will be replaced
     * @param width the width of the produced image
     * @param height the height of the produced image
     * @throws IOException for any error that might occur with the given file */
    public void createPlotAsFile(File fileJpeg, int width, int height) throws IOException{
        JFreeChart barChart=ChartFactory.createBarChart3D(ReporterResources.CHART_TITLE, ReporterResources.X_AXIS_LABEL, this.getYAxisLabel(timeUnit), dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartUtilities.saveChartAsJPEG(fileJpeg, barChart, width, height);
    }
    
    /** Creates an image file containing the chart diagram. The supported format for the file 
     * is JPEG.
     * 
     * @param fileJpeg the file that will be exported. If it already exists, it will be replaced
     * @throws IOException for any error that might occur with the given file */
    public void createPlotAsFile(File fileJpeg) throws IOException{
        JFreeChart barChart=ChartFactory.createBarChart3D(ReporterResources.CHART_TITLE, ReporterResources.X_AXIS_LABEL, this.getYAxisLabel(timeUnit), dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartUtilities.saveChartAsJPEG(fileJpeg, barChart, ReporterResources.CHART_IMAGE_RESOLUTION_X, ReporterResources.CHART_IMAGE_RESOLUTION_Y);
    }
    
    /* returns the proper Y axis label for the chart diagram */
    private String getYAxisLabel(TimeUnit timeUnit){
        switch(timeUnit){
            case MILLISECONDS:
                return ReporterResources.Y_AXIS_LABEL+"( "+ReporterResources.Y_AXIS_LABEL_MSEC+" )";
            case SECONDS:
                return ReporterResources.Y_AXIS_LABEL+"( "+ReporterResources.Y_AXIS_LABEL_SEC+" )";
            case MINUTES:
                return ReporterResources.Y_AXIS_LABEL+"( "+ReporterResources.Y_AXIS_LABEL_MIN+" )";
            case HOURS:
                return ReporterResources.Y_AXIS_LABEL+"( "+ReporterResources.Y_AXIS_LABEL_HOUR+" )";
            default:
                return ReporterResources.Y_AXIS_LABEL;
        }
    }
    
    class ReporterResources{
        static final String CHART_TITLE="Timer Results";
        static final String X_AXIS_LABEL="Tasks";
        static final String Y_AXIS_LABEL="Time";
        static final String Y_AXIS_LABEL_MSEC="msec";
        static final String Y_AXIS_LABEL_SEC="sec";
        static final String Y_AXIS_LABEL_MIN="min";
        static final String Y_AXIS_LABEL_HOUR="hr";
        static final String DEFAULT_TIMER_LABEL="Generic Timer";
        static final int CHART_IMAGE_RESOLUTION_X=800;
        static final int CHART_IMAGE_RESOLUTION_Y=600;
        
    }
}