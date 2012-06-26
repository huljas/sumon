package sumon;

import java.util.HashMap;
import java.util.Map;

/**
 * @author exthulja
 */
public class StackMeter extends BasicMeter {

    private String key;
    private StackMeter parent;
    private Map<String, StackMeter> children = new HashMap<String, StackMeter>(11);

    public StackMeter(String key) {
        super(key);
    }

    public StackMeter(String key, StackMeter parent) {
        super(key);
        this.parent = parent;
    }

    @Override
    public void start(long start) {
        currentInStack.set(this);
        super.start(start);
    }

    @Override
    public void stop(long start, long end) {
        super.stop(start, end);
        currentInStack.set(this.parent);
    }

    public synchronized StackMeter child(String key) {
        StackMeter child = children.get(key);
        if (child == null) {
            child = new StackMeter(key, this);
            children.put(key, child);
        }
        return child;
    }

    public synchronized StackReport stackReport() {
        StackReport report = new StackReport(report());
        for (StackMeter child : children.values()) {
            report.addChild(child.stackReport());
        }
        return report;
    }

    private static ThreadLocal<StackMeter> currentInStack = new ThreadLocal<StackMeter>();

    public static void setCurrent(StackMeter meter) {
    }

    public static StackMeter current() {
        return currentInStack.get();

    }
}
