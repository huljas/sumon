package sumon;

/**
 * @author exthulja
 */
public class SnapImpl implements Snap {

    public final String key;
    private Meter meter;
    private long start;

    public SnapImpl(Meter meter, String key) {
        this.key = key;
        this.meter = meter;
    }

    protected SnapImpl start() {
        this.start = Clock.time();
        meter.start(start);
        return this;
    }

    public void done() {
        long end = Clock.time();
        meter.stop(start, end);
    }
}
