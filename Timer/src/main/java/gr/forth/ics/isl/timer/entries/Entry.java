package gr.forth.ics.isl.timer.entries;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public interface Entry {

    public long getStartTimestamp();

    public void setStartTimestamp(long startTimestamp);

    public long getAccumulatedTime();

    public void addTime(long time);

    public long getStopTimestamp();

    public void setStopTimestamp(long stopTimestamp);
    
    public boolean isPaused();
    
    public void pause();
    
    public void resume();
   
}