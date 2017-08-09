package gr.forth.ics.isl.timer.entries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.math.LongRange;

/**
 * DefaultEntry class is being used for timers that do not have a specific identity.
 * More specifically whenever a default Timer is created (i.e. Timer.start() then an instantiation of 
 * this class is being created.
 * 
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data @Log4j
public class DefaultEntry implements Entry{
    private List<LongRange> timeIntervals;
    
    public DefaultEntry(){
        this.timeIntervals=new ArrayList<>();
        log.debug("Created a new Default timer object "+this.toString());
    }
    
    @Override
    public void start() {
        Optional<LongRange> timeInterval=this.timeIntervals.stream()
                                                           .filter(interval -> interval.getMinimumLong()==0)
                                                           .findAny();
        if(!timeInterval.isPresent()){
            log.debug("Create a new time interval for timer object "+this.toString());
            this.timeIntervals.add(new LongRange(System.currentTimeMillis(), 0));
        }else{
            log.debug("There is already an open time interval for the timer object "+this.toString());
        }
    }

    @Override
    public void stop() {
        Optional<LongRange> timeInterval=this.timeIntervals.stream()
                                                           .filter(interval -> interval.getMinimumLong()==0)
                                                           .findAny();
        if(timeInterval.isPresent()){
            log.debug("Stopping the open time interval for timer object "+this.toString());
            long startTimestamp=timeInterval.get().getMaximumLong();
            this.timeIntervals.remove(timeInterval.get());
            this.timeIntervals.add(new LongRange(startTimestamp, System.currentTimeMillis()));
            log.debug("Stopped the open time interval for timer object "+this.toString());
        }else{
            log.debug("There is not an open time interval for timer object "+this.toString());
        }
    }

    @Override
    public void restart() {
        this.timeIntervals=new ArrayList<>();
        log.debug("Resetting all time intervals for timer object "+this.toString());
        log.debug("Create a new time interval for timer object "+this.toString());
        this.timeIntervals.add(new LongRange(System.currentTimeMillis(), 0));
    }

    @Override
    public Collection<LongRange> reportTimeIntervals() {
        Optional<LongRange> timeInterval=this.timeIntervals.stream()
                                                           .filter(interval -> interval.getMinimumLong()==0)
                                                           .findAny();
        if(timeInterval.isPresent()){
            long startTimestamp=timeInterval.get().getMaximumLong();
            this.timeIntervals.remove(timeInterval.get());
            this.timeIntervals.add(new LongRange(startTimestamp, System.currentTimeMillis()));
        }
        return this.timeIntervals;
    }
}