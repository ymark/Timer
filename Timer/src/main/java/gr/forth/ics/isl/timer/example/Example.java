package gr.forth.ics.isl.timer.example;

import gr.forth.ics.isl.timer.PlotReporter;
import gr.forth.ics.isl.timer.TimeUnit;
import gr.forth.ics.isl.timer.Timer;
import java.io.File;
import java.io.IOException;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Example {
    
    private static void createSomeTimers() throws InterruptedException{
        Timer.start();
        Timer.start("timers.accTimer1.timer1");
        Thread.sleep(451);
        Timer.stop("timers.accTimer1.timer1");
        Timer.start("timers.accTimer1.timer2");
        Thread.sleep(321);
        Timer.stop("timers.accTimer1.timer2");
        Timer.start("timers.timer3");
        Thread.sleep(651);
        Timer.stop("timers.timer3");
        Timer.start("timers.timer4");
        Thread.sleep(298);
        Timer.stop("timers.timer4");
        Timer.stop();
    }
    
    private static void reportDefaultTimer(){
        System.out.println("---------------------Default Timer Only--------------------------------");
        System.out.println(Timer.report(TimeUnit.MILLISECONDS)+" (in "+TimeUnit.MILLISECONDS+")");
    }
    
    private static void reportCustomTimers(){
        System.out.println("-----------------------Custom Timers----------------------------------");
        for(String timer : Timer.getAllTimers()){
            System.out.println(timer+": "+Timer.report(timer, TimeUnit.MILLISECONDS)+" (in "+TimeUnit.MILLISECONDS+")");
        }
    }
    
    private static void reportAccumulatedTimers(){
        System.out.println("-----------------------Acuumulated Timers------------------------------");
        System.out.println("timers.accTimer1"+": "+Timer.report("timers.accTimer1", TimeUnit.MILLISECONDS)+" (in "+TimeUnit.MILLISECONDS+")");
        System.out.println("timers"+": "+Timer.report("timers", TimeUnit.MILLISECONDS)+" (in "+TimeUnit.MILLISECONDS+")");
    }
    
    private static void reportHumanFriendly(){
        System.out.println("------------------Default Timer (Human Friendly)----------------------");
        System.out.println(Timer.reportHumanFriendly());
    }
    
    private static void createPlotAutomatic() throws IOException{
        System.out.println("----------------------Plot Creation (automatic)------------------------");
        File file=new File("plotAutomatic.jpg");
        PlotReporter.PlotFactory(TimeUnit.MILLISECONDS)
                    .withAutomaticSettings()
                    .createPlotAsFile(file);
        System.out.println("Created file with plot at "+file.getAbsolutePath());
    }
    
    private static void createPlotCustom() throws IOException{
        System.out.println("----------------------Plot Creation (custom)---------------------------");
        File file=new File("plotCustom.jpg");
        PlotReporter.PlotFactory(TimeUnit.MILLISECONDS)
                    .withDefaultEntry()
                    .withEntry("timers.accTimer1.timer1")
                    .withEntry("timers.accTimer1.timer2")
                    .withEntry("timers.timer3")
                    .withEntry("timers.timer4")
                    .withEntry("timers.accTimer1")
                    .withEntry("timers")
                    .createPlotAsFile(file);
        System.out.println("Created file with plot at "+file.getAbsolutePath());
    }
    
    public static void main(String[] args) throws InterruptedException, IOException{
        /*First let's create some timers*/
        createSomeTimers();
        /*Report the time of the default timer*/
        reportDefaultTimer();
        /*Report the custom timers*/
        reportCustomTimers();
        /*Report Accumulated timers*/
        reportAccumulatedTimers();
        /*Report the time in a human friendly format*/
        reportHumanFriendly();
        /*Create a plot using automatic settings*/
        createPlotAutomatic();
        /*Create a plot using custom settings*/
        createPlotCustom();
//        PlotReporter.PlotFactory(TimeUnit.MILLISECONDS).withAutomaticSettings().createPlotAsFile(new File("plotExample.jpg"));
    }
}