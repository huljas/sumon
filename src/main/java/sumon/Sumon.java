package sumon;

import java.util.Map;

/**
 * @author exthulja
 */
public class Sumon {

    private static MeterPool meters = new MeterPool();

    private static SnapImpl instance;

    /**
     * Starts a new measurement.
     * @param key
     * @return The snap that can be used to monitoring.
     */
    public static Snap snap(String key) {
        SnapImpl instance = new SnapImpl(meters.get(key), key);
        return instance.start();
    }

    public static Snap snapWithStack(String key) {
        SnapImpl instance = new SnapImpl(meters.getWithStack(key), key);
        return instance.start();
    }

    public static Map<String, Report> report() {
        return meters.report();
    }

    public static void reset() {
        meters.reset();
    }

    public static Map<String, StackReport> stackReport() {
        return meters.stackReport();
    }
}
