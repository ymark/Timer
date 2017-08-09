package gr.forth.ics.isl.timer;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class BasicTest extends TestCase{
    private static final Logger logger=Logger.getLogger(BasicTest.class);
    private static final float TIMER_OVERHEAD=1.1f;
    
    public BasicTest(String name){
        super(name);
    }
    
    @Test
    public void testSimple(){
        int timeToWait=500;
        try{
            Timer.restart();
            Thread.sleep(timeToWait);
            Timer.stop();
            logger.info("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS) >= timeToWait);
            assertTrue(Timer.report(TimeUnit.MILLISECONDS) <= timeToWait*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testWithPause(){
        int timeToWait1=500;
        int timeToWait2=600;
        int timeToWait3=700;
        try{
            Timer.restart();
            Thread.sleep(timeToWait1);
            Timer.stop();
            Thread.sleep(timeToWait1);
            Timer.start();
            Thread.sleep(timeToWait2);
            Timer.stop();
            Thread.sleep(timeToWait2);
            Timer.start();
            Thread.sleep(timeToWait3);
            Timer.stop();
            logger.info("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS)>= (timeToWait1+timeToWait2+timeToWait3));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS)<= (timeToWait1+timeToWait2+timeToWait3)*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testWithoutStop(){
        int timeToWait=500;
        try{
            Timer.restart();
            Thread.sleep(timeToWait);
            logger.info("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS) >= timeToWait);
            assertTrue(Timer.report(TimeUnit.MILLISECONDS) <= timeToWait*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }

    @Test
    public void testWithPauseWithoutStop(){
        int timeToWait1=500;
        int timeToWait2=600;
        int timeToWait3=700;
        try{
            Timer.restart();
            Thread.sleep(timeToWait1);
            Timer.stop();
            Thread.sleep(timeToWait1);
            Timer.start();
            Thread.sleep(timeToWait2);
            Timer.stop();
            Thread.sleep(timeToWait2);
            Timer.start();
            Thread.sleep(timeToWait3);
            logger.info("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS)>= (timeToWait1+timeToWait2+timeToWait3));
            assertTrue(Timer.report(TimeUnit.MILLISECONDS)<= (timeToWait1+timeToWait2+timeToWait3)*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    public void testWithoutStart(){
        int timeToWait=500;
        try{
            logger.info("Timer wasn't started. TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertEquals(0,Timer.report(TimeUnit.MILLISECONDS));
            Thread.sleep(timeToWait);
            Timer.stop();
            logger.info("Timer wasn't started, Only stopped. TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            assertEquals(0,Timer.report(TimeUnit.MILLISECONDS));
        }catch(InterruptedException ex){
            logger.error("Timer wasn't started but reported, TimerReported: "+Timer.report(TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
}