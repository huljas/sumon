package sumon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * @author exthulja
 */
public class PerformanceEffectTest {

    @After
    public void tearDown() {
        Sumon.reset();
        MonitorFactory.reset();
    }

    @Test
    public void testMonitoringWorksOk() {
        MonitoredMergeSort sort = new MonitoredMergeSort();
        sort.sort(Arrays.asList(2, 2, 3, 4));
        sort.sort(Arrays.asList(2, 2, 3, 4));

        assertEquals(2, Sumon.report().get("MergeSort.sort").count);
    }

    @Test
    public void testMergeSortPerformance() {
        int count = 100000;
        MergeSort sort = new MergeSort();
        long time = System.nanoTime();
        List<Integer> list = Collections.unmodifiableList(Arrays.asList(1, 2, 4, 1, 2, 3, 4, 3, 2, 1, 10, 1, 2, 1, 1, 3, 4, 5, 3, 5, 6, 11, 12, 1, 999, 987, 674, 34, 21, 2, 232));
        for (int i = 0; i < count; i++) {
            sort.sort(list);
        }
        time = System.nanoTime() - time;
        long base = time;
        sort = new MonitoredMergeSort();
        time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            sort.sort(list);
        }
        time = System.nanoTime() - time;
        long monitored = time;
        double sortTime = base / (float) count;
        double sortTimeWithMonitoring = monitored / (float) count;
        double delta = sortTime / 100  * 0.1;

        assertTrue("Too much slower " + sortTimeWithMonitoring + " - " + sortTime + " < " + delta, (sortTimeWithMonitoring - sortTime) < delta);
    }

    @Test
    public void testMonitoringSingleThreadPerformance() {
        int count = 100000;
        long requiredNanos = 500;
        long time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Snap snap = Sumon.snap("test1");
            snap.done();
        }
        time = System.nanoTime() - time;
        long timePer = time / count;
        assertEquals(count, Sumon.report().get("test1").count);
        assertTrue("Should be less than " + requiredNanos + "ns but was " + timePer + " ns", timePer < requiredNanos);
        System.out.println("Sumon 1 thread: " + timePer + "ns");
    }

    @Test
    public void testMonitoringMultiThreadedPerformance() throws InterruptedException {
        int count = 1000000;
        int threads = 10;
        long requiredNanos = 500;
        final int repeat = count / threads;
        final Runnable runnable = new Runnable() {
            public void run() {
                for (int j = 0; j < repeat; j++) {
                    Snap snap = Sumon.snap("test1");
                    snap.done();
                }
            }
        };
        Thread[] ta = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            ta[i] = new Thread(runnable);
        }
        long time = System.nanoTime();
        for (int i = 0; i < threads; i++) {
            ta[i].start();
        }
        for (int i = 0; i < threads; i++) {
            ta[i].join(1000);
        }
        time = System.nanoTime() - time;
        long timePer = time / count;
        Map<String, Report> report = Sumon.report();
        assertEquals(count, report.get("test1").count);
        assertTrue("Should be less than " + requiredNanos + "ns but was " + timePer + " ns", timePer < requiredNanos);
        System.out.println("Sumon 10 thread: " + timePer + "ns");
    }

    @Test
    public void compareToJamon() {
        int count = 1000000;
        long requiredNanos = 500;
        long time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Snap snap = Sumon.snap("test1");
            snap.done();
        }
        time = System.nanoTime() - time;
        long timePer1 = time / count;
        time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Monitor monitor = MonitorFactory.start("test1");
            monitor.stop();
        }
        time = System.nanoTime() - time;
        long timePer2 = time / count;
        assertEquals(count, Sumon.report().get("test1").count);
        assertTrue("Should be better than " + timePer2 + "ns but was " + timePer1 + " ns", timePer2 > timePer1);
        System.out.println("Sumon: " + timePer1 + " ns, Jamon: " + timePer2 + " ns");
    }


    @Test
    public void stackMonitoringTest() {
        int count = 100000;
        long requiredNanos = 1000;
        long time = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Snap snap = Sumon.snapWithStack("test1");
            snap.done();
        }
        time = System.nanoTime() - time;
        long timePer = time / count;
        assertEquals(count, Sumon.report().get("test1").count);
        assertTrue("Should be less than " + requiredNanos + "ns but was " + timePer + " ns", timePer < requiredNanos);
        System.out.println("Sumon 1 thread with stack: " + timePer + "ns");
    }
}
