package gr.forth.ics.isl.timer.entries;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DefaultEntry implements Entry{
    private long startTimestamp;
    private long accumulatedTime;
    private long stopTimestamp;
    private boolean paused;
    
    public DefaultEntry(){
        this.startTimestamp=0;
        this.accumulatedTime=0;
        this.stopTimestamp=0;
        this.paused=false;
    }

    @Override
    public long getStartTimestamp() {
        return startTimestamp;
    }

    @Override
    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Override
    public long getAccumulatedTime() {
        return accumulatedTime;
    }

    @Override
    public void addTime(long time) {
        this.accumulatedTime+=time;
    }

    @Override
    public long getStopTimestamp() {
        return stopTimestamp;
    }

    @Override
    public void setStopTimestamp(long stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }
    
    @Override
    public boolean isPaused(){
        return this.paused;
    }
    
    @Override
    public void pause(){
        this.paused=true;
    }
    
    @Override
    public void resume(){
        this.paused=false;
    }
}