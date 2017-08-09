package gr.forth.ics.isl.timer;

import gr.forth.ics.isl.timer.entries.CustomEntry;
import gr.forth.ics.isl.timer.entries.DefaultEntry;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * 
 * Timer is a lightweight JAVA API, for measuring the time of various tasks.
 * The philosophy of the API is based on value assignments, in order to quickly and reliably 
 * capture the time of particular tasks. The API supports a basic type of timer which can be 
 * used rather quickly (i.e. Timer.start(), ... , Timer.stop()), as well as custom timers.
 * Custom timers are identified using a java.package like string (e.g. foo.timer.timer1). 
 * This naming convention allows supporting accumulated timers (e.g. report the accumulated time for all the timers 
 * under foo.timer). 
 * Apart from starting and stopping timers, they can also be paused if required. 
 * Currently the supported time units are milliseconds, seconds, minutes, hours.
 */
public class Timer {
    private static DefaultEntry defaultEntry=new DefaultEntry();
    protected static Map<String,CustomEntry> customEntries=new HashMap<>();
    
    /**Starts a new default timer. 
     * If the default timer has already been started then the second request for starting it 
     * will be ignored. */
    public static void start(){
        defaultEntry.start();
    }
    
    
    /**Stops the default timer.*/
    public static void stop(){
        defaultEntry.stop();
    }
    
    /**Resets the default timer. 
     * Restarting the default timer means that the previous time intervals are 
     * removed (it's basically a reset option for the default timer) */
    public static void restart(){
        defaultEntry.restart();
    }
    
    /**Creates a new timer with the given name. You can use a pattern similar to 
     * java packages for denoting the name of the class (e.g. foo.loading.data.instances). This allows
     * the timer to compute aggregate functions of the available timers. For example for computing 
     * the loading time ask for the time of the timer foo.loading, that will aggregate the times of all the 
     * corresponding timers.
     * If a timer with that name has already been started then the second request for starting it 
     * will be ignored.
     * 
     * @param timerName the java package-like timer name (e.g. foo.loading.data.instances) */
    public static void start(String timerName){
        start(timerName,"");
    }

    /**Creates a new timer with the given name and description. You can use a pattern similar to 
     * java packages for denoting the name of the class (e.g. foo.loading.data.instances). This allows
     * the timer to compute aggregate functions of the available timers. For example for computing 
     * the loading time ask for the time of the timer foo.loading, that will aggregate the times of all the 
     * corresponding timers.
     * If a timer with that name has already been started then the second request for starting it 
     * will be ignored.
     * 
     * @param timerName the java package-like timer name (e.g. foo.loading.data.instances) 
     * @param description a short description of the timer*/
    public static void start(String timerName, String description){
        timerName=timerName.toLowerCase();
        CustomEntry entry=customEntries.get(timerName);
        if(entry==null){
            entry=new CustomEntry(timerName, description);
            customEntries.put(timerName, entry);
        }
        entry.start();
    }
    
    /**Stops the timer with the given name. You can use a pattern similar to 
     * java packages for denoting the name of the class (e.g. foo.loading.data.instances).
     * A stopped timer is actually on hold, and can be started afterwards. The totally 
     * reported time for a timer that has been stopped and started again will be the accumulated 
     * time that it was active.
     *
     * @param timerName the java package-like timer name (e.g. foo.loading.data.instances) */
    public static void stop(String timerName){
        timerName=timerName.toLowerCase();
        CustomEntry entry=customEntries.get(timerName);
        if(entry!=null){
            entry.stop();
        }
    }
    
    /**Restart the timer with the given name. 
     * Restarting a timer means that the previous time intervals for that timer are 
     * removed (it's basically a reset option for the timer)
     * 
     *
     * @param timerName the java package-like timer name (e.g. foo.loading.data.instances) */
    public static void restart(String timerName){
        timerName=timerName.toLowerCase();
        CustomEntry entry=customEntries.get(timerName);
        if(entry!=null){
            entry.restart();
        }
    }
    
    /**Reports the timer values according to the given time unit. The TimeUnits supported are in 
     * MILLISECONDS, SECONDS, MINUTES, HOURS. Notice that the values that will be returned are always rounded 
     * (e.g. 900 msec will be reported as 1 sec, 70 sec will be reported as 1 min, etc.). 
     *
     * @param timeUnit The time using (msec,sec, min, hr) that will be used.
     * @return the time with respect to the given value */
    public static long report(TimeUnit timeUnit){
        return Operation.accummulated(defaultEntry,timeUnit);
    }
    
    /**Reports the timer values according to the given time unit. The TimeUnits supported are in 
     * MILLISECONDS, SECONDS, MINUTES, HOURS. Notice that the values that will be returned are always rounded 
     * (e.g. 900 msec will be reported as 1 sec, 70 sec will be reported as 1 min, etc. <br>
     * 
     * Use java-like names for timer names (e.g. foo.loading.data.instances). Such names will allow 
     * reporting aggregate timings for the various timers. For example reporting the time 
     * for foo.loading will aggregate the times of all the underlying times, e.g. foo.loading.data.instances
     * , foo.loading.data.schema, foo.loading.test, etc.
     *
     * @param timerName the expression that corresponds to a timer or an aggregation of timers
     * @param timeUnit The time using (msec,sec, min, hr) that will be used.
     * @return the time with respect to the given value */
    public static long report(String timerName, TimeUnit timeUnit){
        timerName=timerName.toLowerCase();
        return Operation.accummulated(timerName, timeUnit);
    }
    
    /**Reported the time of the default timer in a human readable form.
     * More specifically it will return a string representing the time in the following format: <br><ul>
     * <li>Hours: 2  Minutes: 12  Seconds: 45  Milliseconds: 357</li></ul>
     * 
     * @return a string representation of the time for the default timer */
    public static String reportHumanFriendly(){
        return Operation.getHumanFriendly(Operation.accummulated(defaultEntry, TimeUnit.MILLISECONDS));
    }
    
    /**Reported the time of the corresponding timer (or the aggregation of timers) in a human readable form.
     * More specifically it will return a string representing the time in the following format: <br><ul>
     * <li>Hours: 2  Minutes: 12  Seconds: 45  Milliseconds: 357</li></ul>
     * 
     * @param timerName the expression that corresponds to a timer or an aggregation of timers
     * @return a string representation of the time for the given timer (or aggregation of timers) */
    public static String reportHumanFriendly(String timerName){
        timerName=timerName.toLowerCase();
        return Operation.getHumanFriendly(Operation.accummulated(timerName, TimeUnit.MILLISECONDS));
    }
    
    /**Returns the names of all the timers that have been created. Notice that in this case the default 
     * timer will not be returned (as it doesn't have a specific name). 
     * 
     * @return a collection of all the available timers */
    public static Collection<String> getAllTimers(){
        return customEntries.keySet();
    }
    
    /**Returns a human-readable description of a particular timer (if such a description exists).
     * 
     * @param timerName the name of the timer
     * @return a human-readable description of the timer */
    public static String getTimerInfo(String timerName){
        timerName=timerName.toLowerCase();
        if(customEntries.containsKey(timerName)){
            String description=customEntries.get(timerName).getDescription();
            if(description.isEmpty()){
                return "TimerName: ["+timerName+"]\tDescription: [N\\E]";
            }else{
                return "TimerName: ["+timerName+"]\tDescription: ["+description+"]";
            }
        }else{
            return "There is not timer with name \""+timerName+"\"";
        }
    }
}