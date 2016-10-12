package gr.forth.ics.isl.timer;

import gr.forth.ics.isl.timer.entries.CustomEntry;
import gr.forth.ics.isl.timer.entries.Entry;
import java.util.Map;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Operation {
    private static final int MILLISECONDS_DENOMINATOR=1000;
    private static final int SECODS_DENOMINATOR=60;
    private static final int MINUTES_DENOMINATOR=60;
    
    protected static long accummulated(Entry entry, TimeUnit unit){
        long value=entry.getStopTimestamp()-entry.getStartTimestamp();
        if(value==0){
            if(entry.isPaused()){
                value=entry.getAccumulatedTime();
            }else{
                value=System.currentTimeMillis()-entry.getStartTimestamp()+entry.getAccumulatedTime();
            }
        }else{
            value+=entry.getAccumulatedTime();
        }
        if(entry.getStartTimestamp()==0){   //The Timer wasn't started
            return entry.getStartTimestamp();
        }
        return getValue(value, unit);
    }
    
    protected static long accummulated(String timerName, TimeUnit unit){
        long value=0;
        for(CustomEntry entry : Timer.customEntries.values()){
            if(entry.getTimerName().startsWith(timerName)){
                value+=accummulated(entry, TimeUnit.MILLISECONDS);
            }
        }
        return getValue(value, unit);
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