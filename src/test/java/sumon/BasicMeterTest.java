package sumon;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author exthulja
 */
public class BasicMeterTest {

    BasicMeter meter;

    @Before
    public void setUp() {
        meter = new BasicMeter("test");
    }

    @Test
    public void testSingle() {
        meter.start(17);
        meter.stop(17, 27);
        assertEquals(10, meter.report().total, 0.1);
        assertEquals(1, meter.report().count);
        assertEquals(10, meter.report().average, 0.1);
        assertEquals(10, meter.report().min);
        assertEquals(10, meter.report().max);
        assertEquals("test", meter.report().key);
        assertEquals(0, meter.report().stdev, 0.1);
        assertEquals(1, meter.report().avgActive, 0.1);
        assertEquals(1, meter.report().maxActive);
        assertEquals(17, meter.report().firstAccess);
        assertEquals(17, meter.report().lastAccess);
    }

    @Test
    public void testAFew() {
        meter.start(17);
        meter.start(18);
        meter.stop(18, 23);
        meter.stop(17, 27);
        meter.start(27);
        meter.stop(27, 32);
        assertEquals(20, meter.report().total, 0.1);
        assertEquals(3, meter.report().count);
        assertEquals(6.6, meter.report().average, 0.1);
        assertEquals(5, meter.report().min);
        assertEquals(10, meter.report().max);
        assertEquals("test", meter.report().key);
        assertEquals(2.9, meter.report().stdev, 0.1);
        assertEquals(1.333, meter.report().avgActive, 0.01);
        assertEquals(2, meter.report().maxActive);
        assertEquals(17, meter.report().firstAccess);
        assertEquals(27, meter.report().lastAccess);
    }
}
