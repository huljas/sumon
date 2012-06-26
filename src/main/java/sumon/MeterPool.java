package sumon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author exthulja
 */
public class MeterPool {

    public Map<String, BasicMeter> activeMeters = new HashMap<String, BasicMeter>();

    public Map<String, StackMeter> stackRoots = new HashMap<String, StackMeter>();

    public Meter get(String key) {
        synchronized (activeMeters) {
            BasicMeter meter = activeMeters.get(key);
            if (meter == null) {
                meter = new BasicMeter(key);
                activeMeters.put(key, meter);
            }
            return meter;
        }
    }


    public Meter getWithStack(String key) {
        StackMeter current = StackMeter.current();
        if (current == null) {
            synchronized (stackRoots) {
                current = stackRoots.get(key);
                if (current == null) {
                    current = new StackMeter(key);
                    stackRoots.put(key, current);
                }
            }
        } else {
            current = current.child(key);
        }
        return new CompositeMeter(get(key), current);
    }


    public Map<String, Report> report() {
        List<BasicMeter> meters;
        synchronized (activeMeters) {
            meters = new ArrayList<BasicMeter>(activeMeters.values());
        }
        Map<String, Report> result = new HashMap<String, Report>();
        for (BasicMeter meter : meters) {
            Report report = meter.report();
            result.put(report.key, report);
        }
        return result;
    }

    public void reset() {
        synchronized (activeMeters) {
            activeMeters.clear();
        }
        synchronized (stackRoots) {
            stackRoots.clear();
        }
    }

    public Map<String, StackReport> stackReport() {
        List<StackMeter> meters;
        synchronized (stackRoots) {
            meters = new ArrayList<StackMeter>(stackRoots.values());
        }
        Map<String, StackReport> result = new HashMap<String, StackReport>(meters.size());
        for (StackMeter meter : meters) {
            result.put(meter.key(), meter.stackReport());
        }
        return result;
    }
}
