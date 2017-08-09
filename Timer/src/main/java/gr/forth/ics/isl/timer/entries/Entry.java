package gr.forth.ics.isl.timer.entries;

import java.util.Collection;
import org.apache.commons.lang.math.LongRange;

/** Entry is the generic interface that rules the behavior of timer objects.
 * More specifically timers objects can be triggered (start, stopped, restarted) 
 * and they can also report their time intervals (they can be multiple) when they have been stopped several 
 * stopped and started several times.
 * 
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public interface Entry {

    /** Starts the timer object. If the timer object has not been started before (and therefore it has not 
     any time intervals yet) it creates a new time interval and set the start timestamp). 
     If the timer object is already stopped, then a new time interval is being created and the start timestamp 
     * is being set. 
     If the timer has already been started, but not stopped then the request for starting it is ignored*/
    public void start();
    
    /** Starts the timer object. If the timer object has not been started before then this request is 
     * ignored, otherwise it sets the stop timestamp in the current time interval */
    public void stop();
    
    /** It restarts the time object. It basically removes all the previous time-intervals and creates a new one.
     * This is actually a reset option for timer objects. */
    public void restart();
   
    /** It reports all the available time intervals that exist for the given timer object.
     * If there are open time intervals (i.e. if the timer object is not stopped) it first stops it and the 
     * report the time intervals 
     * 
     * @return a collection of the time intervals that exist for the given timer object */
    public Collection<LongRange> reportTimeIntervals();
}