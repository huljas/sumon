package sumon;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author exthulja
 */
public class MonitoringWithStackTest {

    @After
    public void tearDown() {
        Sumon.reset();
    }

    @Test
    public void basicMonitoring() throws InterruptedException {
        flowWithMonitoring();
        assertEquals(1, Sumon.report().get("root").count);
        assertEquals(8, Sumon.report().get("loop").count);
        assertEquals(8.25, Sumon.report().get("loop").average, 1);
    }

    @Test
    public void stackMonitoring() throws InterruptedException {
        flowWithMonitoring();
        assertEquals(1, Sumon.stackReport().get("root").count, 1);
        assertEquals(5, Sumon.stackReport().get("root").child("sub1").child("loop").count);
        assertEquals(10, Sumon.stackReport().get("root").child("sub1").child("loop").average, 1);
        assertEquals(3, Sumon.stackReport().get("root").child("sub2").child("loop").count);
        assertEquals(5, Sumon.stackReport().get("root").child("sub2").child("loop").average, 1);
    }

    @Test
    public void stackMonitoringWithThreads() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        flowWithMonitoring();
                    } catch (InterruptedException e) {
                        // Ignored
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join(1000);
        }

        assertEquals(1, Sumon.stackReport().get("root").count, 10);
        assertEquals(50, Sumon.stackReport().get("root").child("sub1").child("loop").count);
        assertEquals(10, Sumon.stackReport().get("root").child("sub1").child("loop").average, 1);
        assertEquals(30, Sumon.stackReport().get("root").child("sub2").child("loop").count);
        assertEquals(5, Sumon.stackReport().get("root").child("sub2").child("loop").average, 1);

    }

    private void flowWithMonitoring() throws InterruptedException {
        Snap snap = Sumon.snapWithStack("root");
        Snap snap1 = Sumon.snapWithStack("sub1");
        for (int i = 0; i < 5; i++) {
            Snap loop = Sumon.snapWithStack("loop");
            Thread.sleep(10);
            loop.done();
        }
        snap1.done();
        Snap snap2 = Sumon.snapWithStack("sub2");
        for (int i = 0; i < 3; i++) {
            Snap loop = Sumon.snapWithStack("loop");
            Thread.sleep(5);
            loop.done();
        }
        snap2.done();
        snap.done();
    }
}
