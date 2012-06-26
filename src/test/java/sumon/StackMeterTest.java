package sumon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author exthulja
 */
public class StackMeterTest {

    @Test
    public void simpleStack() {
        StackMeter root = new StackMeter("root");
        root.start(10);
        for (int i = 0; i < 10; i++) {
            StackMeter loop = root.child("loop");
            loop.start(11 + i);
            loop.stop(11 + i, 11 + i + 2);
        }
        root.stop(10, 11+9+2);
        assertEquals(1, root.report().count);
        assertEquals(12, root.report().average, 0.01);
        assertEquals(10, root.stackReport().child("loop").count);
        assertEquals(2, root.stackReport().child("loop").average, 0.01);
    }

}
