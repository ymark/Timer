package gr.forth.ics.isl.timer;

import gr.forth.ics.isl.timer.entries.CustomEntry;
import gr.forth.ics.isl.timer.entries.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.math.LongRange;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Log4j
public class Operation {
    private static final int MILLISECONDS_DENOMINATOR=1000;
    private static final int SECODS_DENOMINATOR=60;
    private static final int MINUTES_DENOMINATOR=60;
    
    protected static long accummulated(Entry entry, TimeUnit unit){
        return accummulatedTimeInMillis(entry.reportTimeIntervals(), unit);
    }
    
    protected static long accummulated(String timerName, TimeUnit unit){
        List<LongRange> allRangesList=Timer.customEntries.values().stream()
                                                                .filter(entry -> entry.getTimerName().startsWith(timerName))
                                                                .map(CustomEntry::reportTimeIntervals)
                                                                .flatMap(Collection::stream)
                                                                .distinct()
                                                                .collect(Collectors.toList());
        return accummulatedTimeInMillis(allRangesList, unit);
    }
    
    private static long accummulatedTimeInMillis(Collection<LongRange> allTimeIntervals, TimeUnit unit){
        log.debug("List of time intervals before merging "+allTimeIntervals);
        Set<LongRange> mergedRanges=new HashSet<>();
        while(true){
            for(LongRange examineRange : allTimeIntervals){
                mergedRanges.add(mergeRanges(examineRange, allTimeIntervals));
            }
            if(allTimeIntervals.containsAll(mergedRanges)){
                break;
            }else{
                allTimeIntervals=new ArrayList<>(mergedRanges);
                mergedRanges=new HashSet<>();
            }
        }
        log.debug("List of time intervals after merging "+mergedRanges);
        return getValue(mergedRanges.stream().mapToLong(range -> range.getMaximumLong()-range.getMinimumLong()).sum(), unit);
    }
    
    private static LongRange mergeRanges(LongRange r1, Collection<LongRange> otherRanges){
        OptionalLong resultMin=
        otherRanges.stream().filter(otherRange -> otherRange.overlapsRange(r1))
                            .mapToLong(LongRange::getMinimumLong)
                            .min();
        OptionalLong resultMax=
        otherRanges.stream().filter(otherRange -> otherRange.overlapsRange(r1))
                            .mapToLong(LongRange::getMaximumLong)
                            .max();
        return new LongRange(resultMin.getAsLong(), resultMax.getAsLong());       
    }
    
    protected static String getHumanFriendly(long timeInMillis){
        int hr=(int) getHours(timeInMillis); //We're going to have small values so type casting should be enough
        int min=(int) getMinutes(timeInMillis);
        int sec=(int) getSeconds(timeInMillis);
        int msec=(int) getMilliseconds(timeInMillis);
        return "Hours: "+hr+"\tMinutes: "+min+"\tSeconds: "+sec+"\tMilliseconds: "+msec;
    }
    
    private static long getValue(long initialValue,TimeUnit timeUnit){
        switch(timeUnit){
            case MILLISECONDS:
                return asMillisSeconds(initialValue);
            case SECONDS:
                return asSeconds(initialValue);
            case MINUTES:
                return asMinutes(initialValue);
            case HOURS:
                return asHours(initialValue);
            default:    //Just in case
                return initialValue;
        }
    }
    
    private static long asMillisSeconds(long value){
        return value;
    }
    
    private static long asSeconds(long value){
        long retValue=value/MILLISECONDS_DENOMINATOR;
        if(value%MILLISECONDS_DENOMINATOR > MILLISECONDS_DENOMINATOR/2){
            retValue+=1;
        }
        return retValue;
    }
    
    private static long asMinutes(long value){
        long retValue=value/(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR);
        if(value%(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR) > (MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR)/2){
            retValue+=1;
        }
        return retValue;
    }
    
    private static long asHours(long value){
        long retValue=value/(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR);
        if(retValue%(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR) > (MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR)/2){
            retValue+=1;
        }
        return retValue;
    }
    
    private static long getMilliseconds(long value){
        return value%MILLISECONDS_DENOMINATOR;
    }
    
    private static long getSeconds(long value){
        long countedValue=getHours(value)*(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR);
        countedValue+=getMinutes(value)*(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR);
        value-=countedValue;
        return value/MILLISECONDS_DENOMINATOR;
    }
    
    private static long getMinutes(long value){
        long countedValue=getHours(value)*(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR);
        value-=countedValue;
        return value/(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR);
    }
    
    private static long getHours(long value){
        return value/(MILLISECONDS_DENOMINATOR*SECODS_DENOMINATOR*MINUTES_DENOMINATOR);
    }
    
    public static void main(String[] args){
        System.out.println(getHumanFriendly(78565213l));
    }
}