package sumon;

/**
 * Meter that collects execution times for analysis.
 *
 * @author huljas@gmail.com
 */
public class BasicMeter implements Meter {

    private final String key;
    private long count;
    private double total;
    private double ss;
    private long max = Long.MIN_VALUE;
    private long min = Long.MAX_VALUE;
    private long last;
    private long lastAccess;
    private long firstAccess;
    private long maxActive;
    private long active;
    private double totalActive;

    public BasicMeter(String key) {
        this.key = key;
    }

    public synchronized void start(long start) {
        lastAccess = start;
        if (firstAccess == 0) {
            firstAccess = start;
        }
        active++;
        if (active > maxActive) {
            maxActive = active;
        }
    }

    public synchronized void stop(long start, long end) {
        long time = end - start;
        this.totalActive += this.active;
        this.total += time;
        this.last = time;
        this.count++;
        this.ss += time * time;
        if (this.min > time) {
            this.min = time;
        }
        if (this.max < time) {
            this.max = time;
        }
        this.active--;
    }

    public Report report() {
        Report report = new Report();
        synchronized (this) {
            report.key = this.key;
            report.total = this.total;
            report.average = this.total / this.count;
            report.stdev = Math.sqrt((ss - total * total / this.count) / (this.count > 1 ? this.count - 1 : 1));
            report.count = this.count;
            report.max = this.max;
            report.min = this.min;
            report.avgActive = this.totalActive / this.count;
            report.maxActive = this.maxActive;
            report.firstAccess = this.firstAccess;
            report.lastAccess = this.lastAccess;
        }
        return report;
    }

    public String key() {
        return key;
    }
}
