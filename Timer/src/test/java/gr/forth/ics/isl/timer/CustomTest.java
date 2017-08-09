package gr.forth.ics.isl.timer;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class CustomTest extends TestCase{
    private static final Logger logger=Logger.getLogger(CustomTest.class);
    private static final float TIMER_OVERHEAD=1.1f;
    
    public CustomTest(String name){
        super(name);
    }
    
    @Test
    public void testSimple(){
        int timeToWait=500;
        String  timerName="foo.test.simple";
        try{
            Timer.start(timerName);
            Thread.sleep(timeToWait);
            Timer.stop(timerName);
            logger.info("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS) >= timeToWait);
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS) <= timeToWait*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testWithPause(){
        int timeToWait1=500;
        int timeToWait2=600;
        int timeToWait3=700;
        String timerName="foo.test.withPause";
        try{
            Timer.start(timerName);
            Thread.sleep(timeToWait1);
            Timer.stop(timerName);
            Thread.sleep(timeToWait1);
            Timer.start(timerName);
            Thread.sleep(timeToWait2);
            Timer.stop(timerName);
            Thread.sleep(timeToWait2);
            Timer.start(timerName);
            Thread.sleep(timeToWait3);
            Timer.stop(timerName);
            logger.info("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS)>= (timeToWait1+timeToWait2+timeToWait3));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS)<= (timeToWait1+timeToWait2+timeToWait3)*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testWithoutStop(){
        int timeToWait=500;
        String timerName="foo.test.withoutStop";
        try{
            Timer.start(timerName);
            Thread.sleep(timeToWait);
            logger.info("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS) >= timeToWait);
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS) <= timeToWait*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+timeToWait+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }

    @Test
    public void testWithPauseWithoutStop(){
        int timeToWait1=500;
        int timeToWait2=600;
        int timeToWait3=700;
        String timerName="foo.test.withPauseWithoutStop";
        try{
            Timer.start(timerName);
            Thread.sleep(timeToWait1);
            Timer.stop(timerName);
            Thread.sleep(timeToWait1);
            Timer.start(timerName);
            Thread.sleep(timeToWait2);
            Timer.stop(timerName);
            Thread.sleep(timeToWait2);
            Timer.start(timerName);
            Thread.sleep(timeToWait3);
            logger.info("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS)>= (timeToWait1+timeToWait2+timeToWait3));
            assertTrue(Timer.report(timerName,TimeUnit.MILLISECONDS)<= (timeToWait1+timeToWait2+timeToWait3)*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime: "+(timeToWait1+timeToWait2+timeToWait3)+" TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    public void testWithoutStart(){
        int timeToWait=500;
        String timerName="foo.test.withoutStart";
        try{
            logger.info("Timer wasn't started. TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertEquals(0,Timer.report(timerName,TimeUnit.MILLISECONDS));
            Thread.sleep(timeToWait);
            Timer.stop(timerName);
            logger.info("Timer wasn't started, Only stopped. TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            assertEquals(0,Timer.report(timerName,TimeUnit.MILLISECONDS));
        }catch(InterruptedException ex){
            logger.error("Timer wasn't started but reported, TimerReported: "+Timer.report(timerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testNonExistingTimer(){
        int timeToWait=500;
        String existingTimerName="foo.test.nonExistingTimer1";
        String nonExistingTimerName="foo.test.nonExistingTimer2";
        try{
            Timer.start(existingTimerName);
            Thread.sleep(timeToWait);
            Timer.stop(existingTimerName);
            logger.info("Asking for a Timer that doesn't exist. TimerReported: "+Timer.report(nonExistingTimerName,TimeUnit.MILLISECONDS));
            assertEquals(0,Timer.report(nonExistingTimerName,TimeUnit.MILLISECONDS));
        }catch(InterruptedException ex){
            logger.error("Asking for a Timer that doesn't exist. TimerReported: "+Timer.report(nonExistingTimerName,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
    
    @Test
    public void testAccumulated(){
        int timeToWait1=500;
        int timeToWait2=400;
        String timerName1="foo.test.accumulated.timer1";
        String timerName2="foo.test.accumulated.timer2";
        String timerNameAcc="foo.test.accumulated";
        try{
            Timer.start(timerName1);
            Thread.sleep(timeToWait1);
            Timer.stop(timerName1);
            Timer.start(timerName2);
            Thread.sleep(timeToWait2);
            Timer.stop(timerName2);
            logger.info("ActualSleepTime-timer1: "+timeToWait1+" Timer1 Reported: "+Timer.report(timerName1,TimeUnit.MILLISECONDS));
            logger.info("ActualSleepTime-timer2: "+timeToWait2+" Timer2 Reported: "+Timer.report(timerName2,TimeUnit.MILLISECONDS));
            logger.info("ActualSleepTime-ACC: "+(timeToWait1+timeToWait2)+" Timer2 Reported: "+Timer.report(timerNameAcc,TimeUnit.MILLISECONDS));
            assertTrue(Timer.report(timerName1, TimeUnit.MILLISECONDS) >= timeToWait1);
            assertTrue(Timer.report(timerName1, TimeUnit.MILLISECONDS) <= timeToWait1*TIMER_OVERHEAD);
            assertTrue(Timer.report(timerName2, TimeUnit.MILLISECONDS) >= timeToWait2);
            assertTrue(Timer.report(timerName2, TimeUnit.MILLISECONDS) <= timeToWait2*TIMER_OVERHEAD);
            assertTrue(Timer.report(timerNameAcc, TimeUnit.MILLISECONDS) >= (timeToWait1+timeToWait2));
            assertTrue(Timer.report(timerNameAcc, TimeUnit.MILLISECONDS) <= (timeToWait1+timeToWait2)*TIMER_OVERHEAD);
        }catch(InterruptedException ex){
            logger.error("ActualSleepTime-timer1: "+timeToWait1+" Timer1 Reported: "+Timer.report(timerName1,TimeUnit.MILLISECONDS));
            logger.error("ActualSleepTime-timer2: "+timeToWait2+" Timer2 Reported: "+Timer.report(timerName2,TimeUnit.MILLISECONDS));
            logger.error("ActualSleepTime-ACC: "+(timeToWait1+timeToWait2)+" Timer2 Reported: "+Timer.report(timerNameAcc,TimeUnit.MILLISECONDS));
            fail(ex.toString());
        }
    }
}