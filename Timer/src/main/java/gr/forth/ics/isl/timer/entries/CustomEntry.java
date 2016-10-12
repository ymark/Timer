package gr.forth.ics.isl.timer.entries;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class CustomEntry implements Entry {

    private String timerName;
    private String description;
    private long startTimestamp;
    private long accumulatedTime;
    private long stopTimestamp;
    private boolean paused;

    public CustomEntry(String timerName, String description) {
        this.timerName = timerName;
        this.description = description;
        this.startTimestamp = 0;
        this.stopTimestamp = 0;
        this.accumulatedTime = 0;
        this.paused = false;
    }

    @Override
    public long getStartTimestamp() {
        return this.startTimestamp;
    }

    @Override
    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Override
    public long getAccumulatedTime() {
        return this.accumulatedTime;
    }

    @Override
    public void addTime(long time) {
        this.accumulatedTime += time;
    }

    @Override
    public long getStopTimestamp() {
        return this.stopTimestamp;
    }

    @Override
    public void setStopTimestamp(long stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    @Override
    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void resume() {
        this.paused = false;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
